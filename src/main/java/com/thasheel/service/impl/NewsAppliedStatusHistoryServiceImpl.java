package com.thasheel.service.impl;

import com.thasheel.service.NewsAppliedStatusHistoryService;
import com.thasheel.domain.NewsAppliedStatusHistory;
import com.thasheel.repository.NewsAppliedStatusHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NewsAppliedStatusHistory}.
 */
@Service
@Transactional
public class NewsAppliedStatusHistoryServiceImpl implements NewsAppliedStatusHistoryService {

    private final Logger log = LoggerFactory.getLogger(NewsAppliedStatusHistoryServiceImpl.class);

    private final NewsAppliedStatusHistoryRepository newsAppliedStatusHistoryRepository;

    public NewsAppliedStatusHistoryServiceImpl(NewsAppliedStatusHistoryRepository newsAppliedStatusHistoryRepository) {
        this.newsAppliedStatusHistoryRepository = newsAppliedStatusHistoryRepository;
    }

    @Override
    public NewsAppliedStatusHistory save(NewsAppliedStatusHistory newsAppliedStatusHistory) {
        log.debug("Request to save NewsAppliedStatusHistory : {}", newsAppliedStatusHistory);
        return newsAppliedStatusHistoryRepository.save(newsAppliedStatusHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsAppliedStatusHistory> findAll() {
        log.debug("Request to get all NewsAppliedStatusHistories");
        return newsAppliedStatusHistoryRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NewsAppliedStatusHistory> findOne(Long id) {
        log.debug("Request to get NewsAppliedStatusHistory : {}", id);
        return newsAppliedStatusHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsAppliedStatusHistory : {}", id);
        newsAppliedStatusHistoryRepository.deleteById(id);
    }
}
