package com.thasheel.service.impl;

import com.thasheel.service.NewsAppliedService;
import com.thasheel.domain.NewsApplied;
import com.thasheel.repository.NewsAppliedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NewsApplied}.
 */
@Service
@Transactional
public class NewsAppliedServiceImpl implements NewsAppliedService {

    private final Logger log = LoggerFactory.getLogger(NewsAppliedServiceImpl.class);

    private final NewsAppliedRepository newsAppliedRepository;

    public NewsAppliedServiceImpl(NewsAppliedRepository newsAppliedRepository) {
        this.newsAppliedRepository = newsAppliedRepository;
    }

    @Override
    public NewsApplied save(NewsApplied newsApplied) {
        log.debug("Request to save NewsApplied : {}", newsApplied);
        return newsAppliedRepository.save(newsApplied);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsApplied> findAll() {
        log.debug("Request to get all NewsApplieds");
        return newsAppliedRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NewsApplied> findOne(Long id) {
        log.debug("Request to get NewsApplied : {}", id);
        return newsAppliedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsApplied : {}", id);
        newsAppliedRepository.deleteById(id);
    }
}
