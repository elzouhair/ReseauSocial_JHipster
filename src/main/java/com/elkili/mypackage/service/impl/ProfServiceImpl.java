package com.elkili.mypackage.service.impl;

import com.elkili.mypackage.service.ProfService;
import com.elkili.mypackage.domain.Prof;
import com.elkili.mypackage.repository.ProfRepository;
import com.elkili.mypackage.service.dto.ProfDTO;
import com.elkili.mypackage.service.mapper.ProfMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prof}.
 */
@Service
@Transactional
public class ProfServiceImpl implements ProfService {

    private final Logger log = LoggerFactory.getLogger(ProfServiceImpl.class);

    private final ProfRepository profRepository;

    private final ProfMapper profMapper;

    public ProfServiceImpl(ProfRepository profRepository, ProfMapper profMapper) {
        this.profRepository = profRepository;
        this.profMapper = profMapper;
    }

    /**
     * Save a prof.
     *
     * @param profDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfDTO save(ProfDTO profDTO) {
        log.debug("Request to save Prof : {}", profDTO);
        Prof prof = profMapper.toEntity(profDTO);
        prof = profRepository.save(prof);
        return profMapper.toDto(prof);
    }

    /**
     * Get all the profs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profs");
        return profRepository.findAll(pageable)
            .map(profMapper::toDto);
    }


    /**
     * Get one prof by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfDTO> findOne(Long id) {
        log.debug("Request to get Prof : {}", id);
        return profRepository.findById(id)
            .map(profMapper::toDto);
    }

    /**
     * Delete the prof by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prof : {}", id);
        profRepository.deleteById(id);
    }
}
