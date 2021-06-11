package com.thasheel.service.impl;

import com.thasheel.service.NeedfulServiceDocumentsService;
import com.thasheel.service.ServiceAppliedDocumentsService;
import com.thasheel.service.ServiceAppliedService;
import com.thasheel.service.ThasheelServiceService;
import com.thasheel.domain.NeedfulServiceDocuments;
import com.thasheel.domain.ServiceApplied;
import com.thasheel.domain.ServiceAppliedDocuments;
import com.thasheel.domain.ThasheelService;
import com.thasheel.domain.enumeration.NEWSAPPLIEDSTATUS;
import com.thasheel.domain.enumeration.SERVICEAPPLIEDSTATUS;
import com.thasheel.repository.BranchRepository;
import com.thasheel.repository.ServiceAppliedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceApplied}.
 */
@Service
@Transactional
public class ServiceAppliedServiceImpl implements ServiceAppliedService {

    private final Logger log = LoggerFactory.getLogger(ServiceAppliedServiceImpl.class);

    private final ServiceAppliedRepository serviceAppliedRepository;
    private final ThasheelServiceService thasheelServiceService;
    private final BranchRepository branchRepository;
//    @Autowired
//    private  ServiceAppliedDocumentsService serviceAppliedDocumentsService;

    public ServiceAppliedServiceImpl(BranchRepository branchRepository, ThasheelServiceService thasheelServiceService,ServiceAppliedRepository serviceAppliedRepository) {
        this.serviceAppliedRepository = serviceAppliedRepository;
        this.thasheelServiceService=thasheelServiceService;
        this.branchRepository=branchRepository;
    }

    @Override
    public ServiceApplied save(ServiceApplied serviceApplied) {
        log.debug("Request to save ServiceApplied : {}", serviceApplied);
        
        serviceApplied.setCreatedTime(Instant.now());
        serviceApplied.setCurrentStatus(SERVICEAPPLIEDSTATUS.DOCUMENT_NOT_UPLOADED);
        ThasheelService thasheelService = thasheelServiceService.findOne(serviceApplied.getServiceId()).get();
        serviceApplied =serviceAppliedRepository.save(serviceApplied);
        
        return serviceApplied;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceApplied> findAll() {
        log.debug("Request to get all ServiceApplieds");
        return serviceAppliedRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceApplied> findOne(Long id) {
        log.debug("Request to get ServiceApplied : {}", id);
        return serviceAppliedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceApplied : {}", id);
        serviceAppliedRepository.deleteById(id);
    }
    
    
    @Override
	public Optional<ServiceApplied> checkUserAlreadyApplied(Long serviceId, Long customerId) {
		//
		Optional<ServiceApplied> newsApplied = serviceAppliedRepository.findByServiceIdAndCustomerId(serviceId, customerId);
		return newsApplied;
	}

}
