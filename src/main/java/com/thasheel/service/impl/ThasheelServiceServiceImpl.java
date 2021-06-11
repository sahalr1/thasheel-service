package com.thasheel.service.impl;

import com.thasheel.service.ThasheelServiceService;
import com.thasheel.domain.ThasheelService;
import com.thasheel.repository.ThasheelServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ThasheelService}.
 */
@Service
@Transactional
public class ThasheelServiceServiceImpl implements ThasheelServiceService {

    private final Logger log = LoggerFactory.getLogger(ThasheelServiceServiceImpl.class);

    private final ThasheelServiceRepository thasheelServiceRepository;

    public ThasheelServiceServiceImpl(ThasheelServiceRepository thasheelServiceRepository) {
        this.thasheelServiceRepository = thasheelServiceRepository;
    }

    @Override
    public ThasheelService save(ThasheelService thasheelService) {
        log.debug("Request to save ThasheelService : {}", thasheelService);
        return thasheelServiceRepository.save(thasheelService);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThasheelService> findAll() {
        log.debug("Request to get all ThasheelServices");
        return thasheelServiceRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ThasheelService> findOne(Long id) {
        log.debug("Request to get ThasheelService : {}", id);
        return thasheelServiceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThasheelService : {}", id);
        thasheelServiceRepository.deleteById(id);
    }
}
