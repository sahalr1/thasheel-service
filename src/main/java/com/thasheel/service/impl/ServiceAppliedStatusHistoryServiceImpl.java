package com.thasheel.service.impl;

import com.thasheel.service.ServiceAppliedStatusHistoryService;
import com.thasheel.domain.ServiceAppliedStatusHistory;
import com.thasheel.repository.ServiceAppliedStatusHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceAppliedStatusHistory}.
 */
@Service
@Transactional
public class ServiceAppliedStatusHistoryServiceImpl implements ServiceAppliedStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(ServiceAppliedStatusHistoryServiceImpl.class);

    private final ServiceAppliedStatusHistoryRepository serviceAppliedStatusHistoryRepository;

    public ServiceAppliedStatusHistoryServiceImpl(ServiceAppliedStatusHistoryRepository serviceAppliedStatusHistoryRepository) {
        this.serviceAppliedStatusHistoryRepository = serviceAppliedStatusHistoryRepository;
    }

    @Override
    public ServiceAppliedStatusHistory save(ServiceAppliedStatusHistory serviceAppliedStatusHistory) {
        log.debug("Request to save ServiceAppliedStatusHistory : {}", serviceAppliedStatusHistory);
        return serviceAppliedStatusHistoryRepository.save(serviceAppliedStatusHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceAppliedStatusHistory> findAll() {
        log.debug("Request to get all ServiceAppliedStatusHistories");
        return serviceAppliedStatusHistoryRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceAppliedStatusHistory> findOne(Long id) {
        log.debug("Request to get ServiceAppliedStatusHistory : {}", id);
        return serviceAppliedStatusHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceAppliedStatusHistory : {}", id);
        serviceAppliedStatusHistoryRepository.deleteById(id);
    }
}
