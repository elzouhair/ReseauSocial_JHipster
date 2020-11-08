package com.elkili.mypackage.web.rest;

import com.elkili.mypackage.service.ProfService;
import com.elkili.mypackage.web.rest.errors.BadRequestAlertException;
import com.elkili.mypackage.service.dto.ProfDTO;

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
 * REST controller for managing {@link com.elkili.mypackage.domain.Prof}.
 */
@RestController
@RequestMapping("/api")
public class ProfResource {

    private final Logger log = LoggerFactory.getLogger(ProfResource.class);

    private static final String ENTITY_NAME = "prof";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfService profService;

    public ProfResource(ProfService profService) {
        this.profService = profService;
    }

    /**
     * {@code POST  /profs} : Create a new prof.
     *
     * @param profDTO the profDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profDTO, or with status {@code 400 (Bad Request)} if the prof has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profs")
    public ResponseEntity<ProfDTO> createProf(@RequestBody ProfDTO profDTO) throws URISyntaxException {
        log.debug("REST request to save Prof : {}", profDTO);
        if (profDTO.getId() != null) {
            throw new BadRequestAlertException("A new prof cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfDTO result = profService.save(profDTO);
        return ResponseEntity.created(new URI("/api/profs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profs} : Updates an existing prof.
     *
     * @param profDTO the profDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profDTO,
     * or with status {@code 400 (Bad Request)} if the profDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profs")
    public ResponseEntity<ProfDTO> updateProf(@RequestBody ProfDTO profDTO) throws URISyntaxException {
        log.debug("REST request to update Prof : {}", profDTO);
        if (profDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfDTO result = profService.save(profDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profs} : get all the profs.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profs in body.
     */
    @GetMapping("/profs")
    public ResponseEntity<List<ProfDTO>> getAllProfs(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Profs");
        Page<ProfDTO> page = profService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profs/:id} : get the "id" prof.
     *
     * @param id the id of the profDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profs/{id}")
    public ResponseEntity<ProfDTO> getProf(@PathVariable Long id) {
        log.debug("REST request to get Prof : {}", id);
        Optional<ProfDTO> profDTO = profService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profDTO);
    }

    /**
     * {@code DELETE  /profs/:id} : delete the "id" prof.
     *
     * @param id the id of the profDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profs/{id}")
    public ResponseEntity<Void> deleteProf(@PathVariable Long id) {
        log.debug("REST request to delete Prof : {}", id);
        profService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
