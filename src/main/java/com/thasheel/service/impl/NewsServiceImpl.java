package com.thasheel.service.impl;

import com.thasheel.service.NewsService;
import com.thasheel.domain.News;
import com.thasheel.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link News}.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News save(News news) {
        log.debug("Request to save News : {}", news);
        return newsRepository.save(news);
    }

    @Override
    @Transactional(readOnly = true)
    public List<News> findAll() {
        log.debug("Request to get all News");
        return newsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<News> findOne(Long id) {
        log.debug("Request to get News : {}", id);
        return newsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.deleteById(id);
    }

	@Override
	public List<News> findNewsbyCountryId(Long countryId) {
		 log.debug("Request to get all News by country id");
	        return newsRepository.findAllByCountry(countryId);
	}
}
