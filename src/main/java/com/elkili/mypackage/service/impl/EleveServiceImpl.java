package com.elkili.mypackage.service.impl;

import com.elkili.mypackage.service.EleveService;
import com.elkili.mypackage.domain.Eleve;
import com.elkili.mypackage.repository.EleveRepository;
import com.elkili.mypackage.service.dto.EleveDTO;
import com.elkili.mypackage.service.mapper.EleveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Eleve}.
 */
@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveServiceImpl.class);

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    public EleveServiceImpl(EleveRepository eleveRepository, EleveMapper eleveMapper) {
        this.eleveRepository = eleveRepository;
        this.eleveMapper = eleveMapper;
    }

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EleveDTO save(EleveDTO eleveDTO) {
        log.debug("Request to save Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EleveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eleves");
        return eleveRepository.findAll(pageable)
            .map(eleveMapper::toDto);
    }


    /**
     * Get one eleve by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EleveDTO> findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        return eleveRepository.findById(id)
            .map(eleveMapper::toDto);
    }

    /**
     * Delete the eleve by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.deleteById(id);
    }
}
