package com.thasheel.service.impl;

import com.thasheel.service.NewsAppliedService;
import com.thasheel.domain.News;
import com.thasheel.domain.NewsApplied;
import com.thasheel.domain.enumeration.NEWSAPPLIEDSTATUS;
import com.thasheel.repository.BranchRepository;
import com.thasheel.repository.NewsAppliedRepository;
import com.thasheel.repository.NewsRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
	private final NewsRepository newsRepository;
	private final BranchRepository branchRepository;

	public NewsAppliedServiceImpl(BranchRepository branchRepository, NewsAppliedRepository newsAppliedRepository,
			NewsRepository newsRepository) {
		this.branchRepository = branchRepository;
		this.newsRepository = newsRepository;
		this.newsAppliedRepository = newsAppliedRepository;
	}

	@Override
	public NewsApplied save(NewsApplied newsApplied) {
		log.debug("Request to save NewsApplied : {}", newsApplied);
		newsApplied.setCreatedTime(Instant.now());
		newsApplied.setCurrentStatus(NEWSAPPLIEDSTATUS.APPLIED);
		News news = newsRepository.findById(newsApplied.getNewsId()).get();
		Long branchManagerId = branchRepository.findById(news.getBranch().getId()).get().getManager().getId();
		newsApplied.setBranchManagerId(branchManagerId);
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

	@Override
	public Optional<NewsApplied> checkUserAlreadyApplied(Long newsId, Long customerId) {
		//
		Optional<NewsApplied> newsApplied = newsAppliedRepository.findByNewsIdAndCustomerId(newsId, customerId);
		return newsApplied;
	}

	@Override
	@Transactional(readOnly = true)
	public List<NewsApplied> findAllByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return newsAppliedRepository.findAllByCustomerId(customerId);
	}
}
