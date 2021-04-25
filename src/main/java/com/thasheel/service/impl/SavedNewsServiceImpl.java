package com.thasheel.service.impl;

import com.thasheel.service.SavedNewsService;
import com.thasheel.domain.SavedNews;
import com.thasheel.repository.SavedNewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SavedNews}.
 */
@Service
@Transactional
public class SavedNewsServiceImpl implements SavedNewsService {

    private final Logger log = LoggerFactory.getLogger(SavedNewsServiceImpl.class);

    private final SavedNewsRepository savedNewsRepository;

    public SavedNewsServiceImpl(SavedNewsRepository savedNewsRepository) {
        this.savedNewsRepository = savedNewsRepository;
    }

    @Override
    public SavedNews save(SavedNews savedNews) {
        log.debug("Request to save SavedNews : {}", savedNews);
        return savedNewsRepository.save(savedNews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavedNews> findAll() {
        log.debug("Request to get all SavedNews");
        return savedNewsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SavedNews> findOne(Long id) {
        log.debug("Request to get SavedNews : {}", id);
        return savedNewsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SavedNews : {}", id);
        savedNewsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
	public List<SavedNews> findAllByCustomerId(Long CustomerId) {
    	log.debug("Request to get all SavedNews");
        return savedNewsRepository.findAllByCustomerId(CustomerId);
	}
}
