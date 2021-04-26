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
import com.thasheel.service.NewsAppliedService;
import com.thasheel.service.NewsService;
import com.thasheel.service.SavedNewsService;
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
	    
	    private final NewsAppliedService newsAppliedService;
	    
	    private final BranchManagerServiceImpl branchManagerServiceImpl;
	    
	    private final CustomerServiceImpl customerServiceImpl;
	    
	    private final SavedNewsService savedNewsService;

	    public CustomerNewsController(SavedNewsService savedNewsService, CustomerServiceImpl customerServiceImpl , BranchManagerServiceImpl branchManagerServiceImpl,NewsService newsService,NewsAppliedService newsAppliedService) {
	        this.savedNewsService = savedNewsService;
			this.customerServiceImpl = customerServiceImpl;
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
    @PostMapping("/news/apply")
    public ResponseEntity<NewsApplied> applyNews(@RequestBody NewsApplied newsApplied) throws URISyntaxException {
        log.debug("REST request to save NewsApplied : {}", newsApplied);
        if (newsApplied.getId() != null) {
            throw new BadRequestAlertException("A new newsApplied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsApplied result = newsAppliedService.save(newsApplied);
        return ResponseEntity.created(new URI("/api/news-applieds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/news/apply/check/{newsId}")
    public NewsApplied checkUserAlreadyApplied(@PathVariable Long newsId,HttpServletRequest request) {
        log.debug("REST request to know whether user applied News or not");
       // BranchManager manager= branchManagerServiceImpl.findOne(newsService.findOne(newsId).get().getBranch().getManager().getId()).get();
        Customer customer= customerServiceImpl.findByIdpCode(request.getRemoteUser()).get();
        
        Optional<NewsApplied> newsApplied=newsAppliedService.checkUserAlreadyApplied(newsId,customer.getId());
        if(!newsApplied.isPresent())
        {
        	throw new UserNotAppliedException();
        }
        
        
        return newsApplied.get();
    }
    
    
    /**
     * {@code GET  /news-applieds} : get all the newsApplieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsApplieds in body.
     */
    @GetMapping("/news/apply/me")
    public List<NewsApplied> getAllNewsApplieds(HttpServletRequest request) {
        log.debug("REST request to get all NewsApplieds");
        
        Customer customer= customerServiceImpl.findByIdpCode(request.getRemoteUser()).get();
            
        return newsAppliedService.findAllByCustomerId(customer.getId());
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
        Customer customer= customerServiceImpl.findByIdpCode(request.getRemoteUser()).get();
        
        return savedNewsService.findAllByCustomerId(customer.getId());
    }
    @DeleteMapping("/news/save/{id}")
    public ResponseEntity<Void> deleteSavedNews(@PathVariable Long id) {
        log.debug("REST request to delete SavedNews : {}", id);
        savedNewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    
    

}
