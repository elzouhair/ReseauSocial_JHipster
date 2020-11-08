package com.elkili.mypackage.service;

import com.elkili.mypackage.service.dto.PosteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.elkili.mypackage.domain.Poste}.
 */
public interface PosteService {

    /**
     * Save a poste.
     *
     * @param posteDTO the entity to save.
     * @return the persisted entity.
     */
    PosteDTO save(PosteDTO posteDTO);

    /**
     * Get all the postes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PosteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" poste.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PosteDTO> findOne(Long id);

    /**
     * Delete the "id" poste.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
