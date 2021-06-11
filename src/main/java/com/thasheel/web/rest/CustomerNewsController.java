package com.thasheel.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thasheel.domain.BranchManager;
import com.thasheel.domain.Customer;
import com.thasheel.domain.News;
import com.thasheel.domain.NewsApplied;
import com.thasheel.domain.SavedNews;
import com.thasheel.service.CustomerService;
import com.thasheel.service.NewsAppliedService;
import com.thasheel.service.NewsService;
import com.thasheel.service.SavedNewsService;
import com.thasheel.service.UserService;
import com.thasheel.service.UsernameAlreadyUsedException;
import com.thasheel.service.impl.BranchManagerServiceImpl;
import com.thasheel.service.impl.CustomerServiceImpl;

import com.thasheel.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/customer")
public class CustomerNewsController {

	
	  private final Logger log = LoggerFactory.getLogger(CustomerNewsController.class);

	    private static final String ENTITY_NAME = "news";

	    @Value("${jhipster.clientApp.name}")
	    private String applicationName;

	    private final NewsService newsService;
	    private final UserService userService;
	    private final NewsAppliedService newsAppliedService;
	    
	    private final BranchManagerServiceImpl branchManagerServiceImpl;
	    
	    private final CustomerService customerService;
	    
	    
	    
	    private final SavedNewsService savedNewsService;

	    public CustomerNewsController(CustomerService customerService,UserService userService, SavedNewsService savedNewsService, BranchManagerServiceImpl branchManagerServiceImpl,NewsService newsService,NewsAppliedService newsAppliedService) {
	        this.userService=userService;
	    	this.savedNewsService = savedNewsService;
			this.customerService=customerService;
			this.branchManagerServiceImpl = branchManagerServiceImpl;
			this.newsService = newsService;
	        this.newsAppliedService=newsAppliedService;
	    }
	
	/**
     * {@code GET  /news} : get all the news.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of news in body.
     */
    @GetMapping("/news")
    public List<News> getAllNews() {
        log.debug("REST request to get all News");
        return newsService.findAll();
    }

    /**
     * {@code GET  /news} : get all the news.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of news in body.
     */
    @GetMapping("/news/country")
    public List<News> getAllNewsbyCountry() {
        log.debug("REST request to get all News");
        
        Customer customer= userService.getUserWithAuthorities()
        .map(data-> customerService.findByIdpCode(data.getLogin())).get().get();
        
        
        return newsService.findNewsbyCountryId(customer.getCountryId());
    }

    
    
    @PostMapping("/news/save")
    public ResponseEntity<SavedNews> createSavedNews(@RequestBody SavedNews savedNews) throws URISyntaxException {
        log.debug("REST request to save SavedNews : {}", savedNews);
        if (savedNews.getId() != null) {
            throw new BadRequestAlertException("A new savedNews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SavedNews result = savedNewsService.save(savedNews);
        return ResponseEntity.created(new URI("/api/saved-news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code GET  /saved-news} : get all the savedNews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of savedNews in body.
     */
    @GetMapping("/news/save")
    public List<SavedNews> getAllSavedNews(HttpServletRequest request) {
        log.debug("REST request to get all SavedNews");
        Customer customer= customerService.findByIdpCode(request.getRemoteUser()).get();
        
        return savedNewsService.findAllByCustomerId(customer.getId());
    }
    @DeleteMapping("/news/save/{id}")
    public ResponseEntity<Void> deleteSavedNews(@PathVariable Long id) {
        log.debug("REST request to delete SavedNews : {}", id);
        savedNewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    
    

}
