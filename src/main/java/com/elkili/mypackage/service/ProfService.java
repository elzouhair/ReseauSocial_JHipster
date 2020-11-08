package com.elkili.mypackage.service;

import com.elkili.mypackage.service.dto.ProfDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.elkili.mypackage.domain.Prof}.
 */
public interface ProfService {

    /**
     * Save a prof.
     *
     * @param profDTO the entity to save.
     * @return the persisted entity.
     */
    ProfDTO save(ProfDTO profDTO);

    /**
     * Get all the profs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prof.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfDTO> findOne(Long id);

    /**
     * Delete the "id" prof.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
