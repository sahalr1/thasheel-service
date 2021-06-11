package com.thasheel.service.impl;

import com.thasheel.service.NeedfulServiceDocumentsService;
import com.thasheel.domain.NeedfulServiceDocuments;
import com.thasheel.repository.NeedfulServiceDocumentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NeedfulServiceDocuments}.
 */
@Service
@Transactional
public class NeedfulServiceDocumentsServiceImpl implements NeedfulServiceDocumentsService {

    private final Logger log = LoggerFactory.getLogger(NeedfulServiceDocumentsServiceImpl.class);

    private final NeedfulServiceDocumentsRepository needfulServiceDocumentsRepository;

    public NeedfulServiceDocumentsServiceImpl(NeedfulServiceDocumentsRepository needfulServiceDocumentsRepository) {
        this.needfulServiceDocumentsRepository = needfulServiceDocumentsRepository;
    }

    @Override
    public NeedfulServiceDocuments save(NeedfulServiceDocuments needfulServiceDocuments) {
        log.debug("Request to save NeedfulServiceDocuments : {}", needfulServiceDocuments);
        return needfulServiceDocumentsRepository.save(needfulServiceDocuments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NeedfulServiceDocuments> findAll() {
        log.debug("Request to get all NeedfulServiceDocuments");
        return needfulServiceDocumentsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NeedfulServiceDocuments> findOne(Long id) {
        log.debug("Request to get NeedfulServiceDocuments : {}", id);
        return needfulServiceDocumentsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NeedfulServiceDocuments : {}", id);
        needfulServiceDocumentsRepository.deleteById(id);
    }

	@Override
	public List<NeedfulServiceDocuments> findAllByServiceId(Long serviceId) {
		 log.debug("Request to get all NeedfulServiceDocuments");
	        return needfulServiceDocumentsRepository.findAllByThasheelServiceId(serviceId);
	}
}
