package com.elkili.mypackage.web.rest;

import com.elkili.mypackage.service.CommentaireService;
import com.elkili.mypackage.web.rest.errors.BadRequestAlertException;
import com.elkili.mypackage.service.dto.CommentaireDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.elkili.mypackage.domain.Commentaire}.
 */
@RestController
@RequestMapping("/api")
public class CommentaireResource {

    private final Logger log = LoggerFactory.getLogger(CommentaireResource.class);

    private static final String ENTITY_NAME = "commentaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentaireService commentaireService;

    public CommentaireResource(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     * {@code POST  /commentaires} : Create a new commentaire.
     *
     * @param commentaireDTO the commentaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentaireDTO, or with status {@code 400 (Bad Request)} if the commentaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commentaires")
    public ResponseEntity<CommentaireDTO> createCommentaire(@Valid @RequestBody CommentaireDTO commentaireDTO) throws URISyntaxException {
        log.debug("REST request to save Commentaire : {}", commentaireDTO);
        if (commentaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentaireDTO result = commentaireService.save(commentaireDTO);
        return ResponseEntity.created(new URI("/api/commentaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commentaires} : Updates an existing commentaire.
     *
     * @param commentaireDTO the commentaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentaireDTO,
     * or with status {@code 400 (Bad Request)} if the commentaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commentaires")
    public ResponseEntity<CommentaireDTO> updateCommentaire(@Valid @RequestBody CommentaireDTO commentaireDTO) throws URISyntaxException {
        log.debug("REST request to update Commentaire : {}", commentaireDTO);
        if (commentaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentaireDTO result = commentaireService.save(commentaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commentaires} : get all the commentaires.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentaires in body.
     */
    @GetMapping("/commentaires")
    public ResponseEntity<List<CommentaireDTO>> getAllCommentaires(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Commentaires");
        Page<CommentaireDTO> page = commentaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commentaires/:id} : get the "id" commentaire.
     *
     * @param id the id of the commentaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commentaires/{id}")
    public ResponseEntity<CommentaireDTO> getCommentaire(@PathVariable Long id) {
        log.debug("REST request to get Commentaire : {}", id);
        Optional<CommentaireDTO> commentaireDTO = commentaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentaireDTO);
    }

    /**
     * {@code DELETE  /commentaires/:id} : delete the "id" commentaire.
     *
     * @param id the id of the commentaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        log.debug("REST request to delete Commentaire : {}", id);
        commentaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
