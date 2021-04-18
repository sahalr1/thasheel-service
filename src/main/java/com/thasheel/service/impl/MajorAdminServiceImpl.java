package com.thasheel.service.impl;

import com.thasheel.service.MajorAdminService;
import com.thasheel.domain.MajorAdmin;
import com.thasheel.repository.MajorAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MajorAdmin}.
 */
@Service
@Transactional
public class MajorAdminServiceImpl implements MajorAdminService {

    private final Logger log = LoggerFactory.getLogger(MajorAdminServiceImpl.class);

    private final MajorAdminRepository majorAdminRepository;

    public MajorAdminServiceImpl(MajorAdminRepository majorAdminRepository) {
        this.majorAdminRepository = majorAdminRepository;
    }

    @Override
    public MajorAdmin save(MajorAdmin majorAdmin) {
        log.debug("Request to save MajorAdmin : {}", majorAdmin);
        return majorAdminRepository.save(majorAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MajorAdmin> findAll() {
        log.debug("Request to get all MajorAdmins");
        return majorAdminRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MajorAdmin> findOne(Long id) {
        log.debug("Request to get MajorAdmin : {}", id);
        return majorAdminRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MajorAdmin : {}", id);
        majorAdminRepository.deleteById(id);
    }
}
