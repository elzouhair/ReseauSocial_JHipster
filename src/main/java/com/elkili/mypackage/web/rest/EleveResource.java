package com.elkili.mypackage.web.rest;

import com.elkili.mypackage.service.EleveService;
import com.elkili.mypackage.web.rest.errors.BadRequestAlertException;
import com.elkili.mypackage.service.dto.EleveDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.elkili.mypackage.domain.Eleve}.
 */
@RestController
@RequestMapping("/api")
public class EleveResource {

    private final Logger log = LoggerFactory.getLogger(EleveResource.class);

    private static final String ENTITY_NAME = "eleve";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EleveService eleveService;

    public EleveResource(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    /**
     * {@code POST  /eleves} : Create a new eleve.
     *
     * @param eleveDTO the eleveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eleveDTO, or with status {@code 400 (Bad Request)} if the eleve has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eleves")
    public ResponseEntity<EleveDTO> createEleve(@RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to save Eleve : {}", eleveDTO);
        if (eleveDTO.getId() != null) {
            throw new BadRequestAlertException("A new eleve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity.created(new URI("/api/eleves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eleves} : Updates an existing eleve.
     *
     * @param eleveDTO the eleveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eleveDTO,
     * or with status {@code 400 (Bad Request)} if the eleveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eleveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eleves")
    public ResponseEntity<EleveDTO> updateEleve(@RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to update Eleve : {}", eleveDTO);
        if (eleveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eleveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eleves} : get all the eleves.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eleves in body.
     */
    @GetMapping("/eleves")
    public ResponseEntity<List<EleveDTO>> getAllEleves(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Eleves");
        Page<EleveDTO> page = eleveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eleves/:id} : get the "id" eleve.
     *
     * @param id the id of the eleveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eleveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eleves/{id}")
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Long id) {
        log.debug("REST request to get Eleve : {}", id);
        Optional<EleveDTO> eleveDTO = eleveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eleveDTO);
    }

    /**
     * {@code DELETE  /eleves/:id} : delete the "id" eleve.
     *
     * @param id the id of the eleveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eleves/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        log.debug("REST request to delete Eleve : {}", id);
        eleveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
