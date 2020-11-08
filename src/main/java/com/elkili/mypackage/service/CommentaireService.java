package com.elkili.mypackage.service;

import com.elkili.mypackage.service.dto.CommentaireDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.elkili.mypackage.domain.Commentaire}.
 */
public interface CommentaireService {

    /**
     * Save a commentaire.
     *
     * @param commentaireDTO the entity to save.
     * @return the persisted entity.
     */
    CommentaireDTO save(CommentaireDTO commentaireDTO);

    /**
     * Get all the commentaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentaireDTO> findAll(Pageable pageable);


    /**
     * Get the "id" commentaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentaireDTO> findOne(Long id);

    /**
     * Delete the "id" commentaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
