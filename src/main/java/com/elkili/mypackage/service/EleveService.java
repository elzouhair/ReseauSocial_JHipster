package com.elkili.mypackage.service;

import com.elkili.mypackage.service.dto.EleveDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.elkili.mypackage.domain.Eleve}.
 */
public interface EleveService {

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save.
     * @return the persisted entity.
     */
    EleveDTO save(EleveDTO eleveDTO);

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EleveDTO> findAll(Pageable pageable);


    /**
     * Get the "id" eleve.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EleveDTO> findOne(Long id);

    /**
     * Delete the "id" eleve.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
