package com.thasheel.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thasheel.domain.Customer;
import com.thasheel.domain.NeedfulServiceDocuments;
import com.thasheel.domain.ServiceApplied;
import com.thasheel.domain.ServiceAppliedDocuments;
import com.thasheel.domain.ThasheelService;
import com.thasheel.service.CustomerService;
import com.thasheel.service.NeedfulServiceDocumentsService;
import com.thasheel.service.ServiceAppliedDocumentsService;
import com.thasheel.service.ServiceAppliedService;
import com.thasheel.service.ThasheelServiceService;
import com.thasheel.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/customer")
public class CustomerServiceController {

	private final Logger log = LoggerFactory.getLogger(CustomerServiceController.class);

    private static final String ENTITY_NAME = "thasheelService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

	
	 private final ThasheelServiceService thasheelServiceService;
	 private final ServiceAppliedService serviceAppliedService;

	private final CustomerService customerService;
	private final ServiceAppliedDocumentsService serviceAppliedDocumentsService;
	
	private final NeedfulServiceDocumentsService needfulServiceDocumentsService;
	
	    public CustomerServiceController(NeedfulServiceDocumentsService needfulServiceDocumentsService , ServiceAppliedDocumentsService serviceAppliedDocumentsService, CustomerService customerService,ServiceAppliedService serviceAppliedService,ThasheelServiceService thasheelServiceService) {
	    	this.needfulServiceDocumentsService = needfulServiceDocumentsService;
	    	this.serviceAppliedDocumentsService=serviceAppliedDocumentsService;
	    	this.thasheelServiceService = thasheelServiceService;
	        this.serviceAppliedService= serviceAppliedService;
	        this.customerService = customerService;
	    }

	/**
     * {@code GET  /thasheel-services} : get all the thasheelServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thasheelServices in body.
     */
    @GetMapping("/services")
    public List<ThasheelService> getAllThasheelServices() {
        log.debug("REST request to get all ThasheelServices");
        return thasheelServiceService.findAll();
    }
    
    
    /**
     * {@code POST  /service-applieds} : Create a new serviceApplied.
     *
     * @param serviceApplied the serviceApplied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceApplied, or with status {@code 400 (Bad Request)} if the serviceApplied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service/apply")
    public ResponseEntity<ServiceApplied> applyService(@RequestBody ServiceApplied serviceApplied) throws URISyntaxException {
        log.debug("REST request to save ServiceApplied : {}", serviceApplied);
        if (serviceApplied.getId() != null) {
            throw new BadRequestAlertException("A new serviceApplied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceApplied result = serviceAppliedService.save(serviceApplied);
        
        
        for(ServiceAppliedDocuments doc:serviceApplied.getUploadedDocuments())
        {
        	serviceAppliedDocumentsService.upload(doc);
        }
        result=serviceAppliedService.findOne(serviceApplied.getId()).get();
        return ResponseEntity.created(new URI("/api/service-applieds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
   
    @PostMapping("/service/document")
    public ResponseEntity<ServiceAppliedDocuments> uploadDocument(@RequestBody ServiceAppliedDocuments serviceAppliedDocuments) throws URISyntaxException {
        log.debug("REST request to save ServiceAppliedDocuments : {}", serviceAppliedDocuments);
        if (serviceAppliedDocuments.getId() != null) {
            throw new BadRequestAlertException("A new serviceAppliedDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAppliedDocuments result = serviceAppliedDocumentsService.upload(serviceAppliedDocuments);
        return ResponseEntity.created(new URI("/api/service-applied-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    @PostMapping("/service/documents")
    public List<ServiceAppliedDocuments> uploadDocuments(@RequestBody List<ServiceAppliedDocuments> serviceAppliedDocuments) throws URISyntaxException {
       
    	 List<ServiceAppliedDocuments> result = new ArrayList<ServiceAppliedDocuments>();
    	for(ServiceAppliedDocuments serviceAppliedDocument:serviceAppliedDocuments) {
        result.add(serviceAppliedDocumentsService.upload(serviceAppliedDocument));
        }
        
        return result;
    }
    
    @GetMapping("/service/apply/documents/{appliedServiceId}")
    public List<ServiceAppliedDocuments> getAllServiceAppliedDocuments(Long appliedServiceId) {
        log.debug("REST request to get all ServiceAppliedDocuments");
        return serviceAppliedDocumentsService.findAllByAppliedServiceId(appliedServiceId);
    }
    
    
    @GetMapping("/service/needful-documents/{serviceId}")
    public List<NeedfulServiceDocuments> getAllNeedfulServiceDocumentsbyServiceId(Long serviceId) {
        log.debug("REST request to get all NeedfulServiceDocuments");
        return needfulServiceDocumentsService.findAllByServiceId(serviceId);
    }
    
    
    @GetMapping("/service/apply/check/{serviceId}")
    public ServiceApplied checkUserAlreadyApplied(@PathVariable Long serviceId,HttpServletRequest request) {
    	log.debug("REST request to know whether user applied News or not");
    
      Customer customer= customerService.findByIdpCode(request.getRemoteUser()).get();
      
      Optional<ServiceApplied> newsApplied=serviceAppliedService.checkUserAlreadyApplied(serviceId,customer.getId());
      if(!newsApplied.isPresent())
      {
      	throw new UserNotAppliedException();
      }
      
      return newsApplied.get();
  }
    
    
}
