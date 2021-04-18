package com.thasheel.service.impl;

import com.thasheel.service.BranchManagerService;
import com.thasheel.domain.BranchManager;
import com.thasheel.repository.BranchManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link BranchManager}.
 */
@Service
@Transactional
public class BranchManagerServiceImpl implements BranchManagerService {

    private final Logger log = LoggerFactory.getLogger(BranchManagerServiceImpl.class);

    private final BranchManagerRepository branchManagerRepository;

    public BranchManagerServiceImpl(BranchManagerRepository branchManagerRepository) {
        this.branchManagerRepository = branchManagerRepository;
    }

    @Override
    public BranchManager save(BranchManager branchManager) {
        log.debug("Request to save BranchManager : {}", branchManager);
        return branchManagerRepository.save(branchManager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchManager> findAll() {
        log.debug("Request to get all BranchManagers");
        return branchManagerRepository.findAll();
    }



    /**
     *  Get all the branchManagers where Branch is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<BranchManager> findAllWhereBranchIsNull() {
        log.debug("Request to get all branchManagers where Branch is null");
        return StreamSupport
            .stream(branchManagerRepository.findAll().spliterator(), false)
            .filter(branchManager -> branchManager.getBranch() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BranchManager> findOne(Long id) {
        log.debug("Request to get BranchManager : {}", id);
        return branchManagerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BranchManager : {}", id);
        branchManagerRepository.deleteById(id);
    }
}
