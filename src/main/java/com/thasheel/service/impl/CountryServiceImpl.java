package com.thasheel.service.impl;

import com.thasheel.service.CountryService;
import com.thasheel.service.CustomerService;
import com.thasheel.service.UserService;
import com.thasheel.domain.Country;
import com.thasheel.domain.Customer;
import com.thasheel.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Country}.
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository countryRepository;
    private final CustomerService customerService;
    private final UserService userService;
    public CountryServiceImpl(UserService userService, CustomerService customerService,CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.userService= userService;
        this.customerService =  customerService;
        
    }

    @Override
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        return countryRepository.save(country);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> findAll() {
        log.debug("Request to get all Countries");
        return countryRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Country> findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        return countryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.deleteById(id);
    }

	@Override
	public Optional<Customer> setCountryForCustomer(Long countryId) {
		
		return userService.getUserWithAuthorities()
	            .map(data-> {
	           Customer customer  =	customerService.findByIdpCode(data.getLogin()).get();
	           customer.setCountryId(countryId);
	           return customerService.save(customer);
	            });
	}
}
