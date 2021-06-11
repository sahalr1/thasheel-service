package com.thasheel.service.impl;

import com.thasheel.service.NeedfulServiceDocumentsService;
import com.thasheel.service.NewsAppliedService;
import com.thasheel.service.ServiceAppliedDocumentsService;
import com.thasheel.service.ServiceAppliedService;
import com.thasheel.domain.NeedfulServiceDocuments;
import com.thasheel.domain.ServiceApplied;
import com.thasheel.domain.ServiceAppliedDocuments;
import com.thasheel.domain.enumeration.SERVICEAPPLIEDSTATUS;
import com.thasheel.repository.ServiceAppliedDocumentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceAppliedDocuments}.
 */
@Service
@Transactional
public class ServiceAppliedDocumentsServiceImpl implements ServiceAppliedDocumentsService {

    private final Logger log = LoggerFactory.getLogger(ServiceAppliedDocumentsServiceImpl.class);

    private final ServiceAppliedDocumentsRepository serviceAppliedDocumentsRepository;
    private final ServiceAppliedService serviceAppliedService;
    private final NewsAppliedService newsAppliedService;
    private final NeedfulServiceDocumentsService needfulServiceDocumentsService;
    public ServiceAppliedDocumentsServiceImpl(NeedfulServiceDocumentsService needfulServiceDocumentsService, NewsAppliedService newsAppliedService, ServiceAppliedService serviceAppliedService, ServiceAppliedDocumentsRepository serviceAppliedDocumentsRepository) {
        this.serviceAppliedDocumentsRepository = serviceAppliedDocumentsRepository;
        this.serviceAppliedService =serviceAppliedService;
        this.newsAppliedService = newsAppliedService;
        this.needfulServiceDocumentsService = needfulServiceDocumentsService;
    }

    @Override
    public ServiceAppliedDocuments save(ServiceAppliedDocuments serviceAppliedDocuments) {
        log.debug("Request to save ServiceAppliedDocuments : {}", serviceAppliedDocuments);
        return serviceAppliedDocumentsRepository.save(serviceAppliedDocuments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceAppliedDocuments> findAll() {
        log.debug("Request to get all ServiceAppliedDocuments");
        return serviceAppliedDocumentsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceAppliedDocuments> findOne(Long id) {
        log.debug("Request to get ServiceAppliedDocuments : {}", id);
        return serviceAppliedDocumentsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceAppliedDocuments : {}", id);
        serviceAppliedDocumentsRepository.deleteById(id);
    }

	@Override
	public List<ServiceAppliedDocuments> findAllByAppliedServiceId(Long serviceId) {
		
		log.debug("Request to get all ServiceAppliedDocuments");
        return serviceAppliedDocumentsRepository.findAllByServiceApplied(serviceId);
	}

	@Override
	public ServiceAppliedDocuments upload(ServiceAppliedDocuments serviceAppliedDocuments) {
		ServiceAppliedDocuments appliedDoc=save(serviceAppliedDocuments);
		List<ServiceAppliedDocuments> appliedDocs =findAllByAppliedServiceId(serviceAppliedDocuments.getServiceApplied().getId());
		
		ServiceApplied serviceApplied = serviceAppliedService.findOne(serviceAppliedDocuments.getServiceApplied().getId()).get();
		List<NeedfulServiceDocuments> needfulServiceDocuments =needfulServiceDocumentsService.findAllByServiceId(serviceApplied.getServiceId());
		int content=0;
		
		for(NeedfulServiceDocuments doc : needfulServiceDocuments)
		{
			for(ServiceAppliedDocuments applied: appliedDocs)
			{
				if(doc.getId()==applied.getNeedfulServiceDocmentId())
				{
					content++;
				}
			}
		}
		if(needfulServiceDocuments.size()==content) {
			serviceApplied.setCurrentStatus(SERVICEAPPLIEDSTATUS.APPLIED);
		}
		else
		{
			serviceApplied.setCurrentStatus(SERVICEAPPLIEDSTATUS.DOCUMENT_NOT_UPLOADED);
		}
		serviceAppliedService.save(serviceApplied);
		
		return appliedDoc;
	}
}
