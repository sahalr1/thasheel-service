package com.thasheel.web.rest;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thasheel.domain.Customer;
import com.thasheel.domain.User;
import com.thasheel.repository.UserRepository;
import com.thasheel.security.jwt.JWTFilter;
import com.thasheel.security.jwt.TokenProvider;
import com.thasheel.service.CustomerService;
import com.thasheel.service.MailService;
import com.thasheel.service.UserService;
import com.thasheel.service.UsernameAlreadyUsedException;
import com.thasheel.service.dto.UserDTO;
import com.thasheel.web.rest.UserJWTController.JWTToken;
import com.thasheel.web.rest.errors.BadRequestAlertException;
import com.thasheel.web.rest.errors.EmailAlreadyUsedException;
import com.thasheel.web.rest.errors.InvalidPasswordException;
import com.thasheel.web.rest.errors.LoginAlreadyUsedException;
import com.thasheel.web.rest.vm.LoginVM;
import com.thasheel.web.rest.vm.ManagedUserVM;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	

    private static final String ENTITY_NAME = "customer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

	
	 private static class AccountResourceException extends RuntimeException {
	        private AccountResourceException(String message) {
	            super(message);
	        }
	    }

	    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

	    private final UserRepository userRepository;

	    private final UserService userService;

	    private final CustomerService customerService;
	    private final TokenProvider tokenProvider;

	    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	    private final MailService mailService;

	    public CustomerController(CustomerService customerService,TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, UserService userService, MailService mailService) {
	    	
	    	this.tokenProvider = tokenProvider;
	        this.authenticationManagerBuilder = authenticationManagerBuilder;
	        this.userRepository = userRepository;
	        this.userService = userService;
	        this.mailService = mailService;
	        this.customerService = customerService;
	    }

	    /**
	     * {@code POST  /register} : register the user.
	     *
	     * @param managedUserVM the managed user View Model.
	     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
	     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
	     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
	     */
	    @PostMapping("/register")
	    @ResponseStatus(HttpStatus.CREATED)
	    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
	        if (!checkPasswordLength(managedUserVM.getPassword())) {
	            throw new InvalidPasswordException();
	        }
	        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword(),managedUserVM.getPhone());
	        Customer customer=null;
	        customerService.findByIdpCode(user.getLogin()).ifPresent(
	        		savedCustomer->
	        		{
	        			throw new UsernameAlreadyUsedException();
	        		}
	        		);
	        customer=new Customer();
	        customer.setIsEnabled(true);
	        customer.setFirstName(managedUserVM.getFirstName());
	        customer.setLastName(managedUserVM.getLastName());
	        customer.setIdpCode(user.getLogin());
	        customerService.save(customer);
	        mailService.sendActivationEmail(user);
	    }
	    private static boolean checkPasswordLength(String password) {
	        return !StringUtils.isEmpty(password) &&
	            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
	            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
	    }
	    
	    
	    @PostMapping("/authenticate")
	    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

	        UsernamePasswordAuthenticationToken authenticationToken =
	            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

	        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
	        String jwt = tokenProvider.createToken(authentication, rememberMe);
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
	        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	    }
	    
	    /**
	     * {@code PUT  /customers} : Updates an existing customer.
	     *
	     * @param customer the customer to update.
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customer,
	     * or with status {@code 400 (Bad Request)} if the customer is not valid,
	     * or with status {@code 500 (Internal Server Error)} if the customer couldn't be updated.
	     * @throws URISyntaxException if the Location URI syntax is incorrect.
	     */
	    @PutMapping("/updateProfile")
	    public ResponseEntity<Customer> updateCustomerProfile(@Valid @RequestBody Customer customer) throws URISyntaxException {
	        log.debug("REST request to update Customer : {}", customer);
	        if (customer.getId() == null) {
	            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	        Customer result = customerService.save(customer);
	        return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customer.getId().toString()))
	            .body(result);
	    }
	    
	    
	    @GetMapping("/account")
	    public UserDTO getAccount() {
	        return userService.getUserWithAuthorities()
	            .map(UserDTO::new)
	            .orElseThrow(() -> new AccountResourceException("User could not be found"));
	    }

	    
	    @GetMapping("/me")
	    public Customer getCustomerByAccount() {
	        return userService.getUserWithAuthorities()
	            .map(data-> customerService.findByIdpCode(data.getLogin()).get())
	            .orElseThrow(() -> new AccountResourceException("User could not be found"));
	    }

	    
	    
	    
	    
	    
	    /**
	     * Object to return as body in JWT Authentication.
	     */
	    static class JWTToken {

	        private String idToken;

	        JWTToken(String idToken) {
	            this.idToken = idToken;
	        }

	        @JsonProperty("id_token")
	        String getIdToken() {
	            return idToken;
	        }

	        void setIdToken(String idToken) {
	            this.idToken = idToken;
	        }
	    }
	    
	    
}
