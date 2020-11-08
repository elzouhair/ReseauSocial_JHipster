package com.elkili.mypackage.web.rest;

import com.elkili.mypackage.ReseausocialjhApp;
import com.elkili.mypackage.domain.Eleve;
import com.elkili.mypackage.repository.EleveRepository;
import com.elkili.mypackage.service.EleveService;
import com.elkili.mypackage.service.dto.EleveDTO;
import com.elkili.mypackage.service.mapper.EleveMapper;
import com.elkili.mypackage.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.elkili.mypackage.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EleveResource} REST controller.
 */
@SpringBootTest(classes = ReseausocialjhApp.class)
public class EleveResourceIT {

    private static final String DEFAULT_NOM_ELEVE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ELEVE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_ELEVE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_ELEVE = "BBBBBBBBBB";

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private EleveMapper eleveMapper;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEleveMockMvc;

    private Eleve eleve;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EleveResource eleveResource = new EleveResource(eleveService);
        this.restEleveMockMvc = MockMvcBuilders.standaloneSetup(eleveResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eleve createEntity(EntityManager em) {
        Eleve eleve = new Eleve()
            .nomEleve(DEFAULT_NOM_ELEVE)
            .prenomEleve(DEFAULT_PRENOM_ELEVE);
        return eleve;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eleve createUpdatedEntity(EntityManager em) {
        Eleve eleve = new Eleve()
            .nomEleve(UPDATED_NOM_ELEVE)
            .prenomEleve(UPDATED_PRENOM_ELEVE);
        return eleve;
    }

    @BeforeEach
    public void initTest() {
        eleve = createEntity(em);
    }

    @Test
    @Transactional
    public void createEleve() throws Exception {
        int databaseSizeBeforeCreate = eleveRepository.findAll().size();

        // Create the Eleve
        EleveDTO eleveDTO = eleveMapper.toDto(eleve);
        restEleveMockMvc.perform(post("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isCreated());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeCreate + 1);
        Eleve testEleve = eleveList.get(eleveList.size() - 1);
        assertThat(testEleve.getNomEleve()).isEqualTo(DEFAULT_NOM_ELEVE);
        assertThat(testEleve.getPrenomEleve()).isEqualTo(DEFAULT_PRENOM_ELEVE);
    }

    @Test
    @Transactional
    public void createEleveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eleveRepository.findAll().size();

        // Create the Eleve with an existing ID
        eleve.setId(1L);
        EleveDTO eleveDTO = eleveMapper.toDto(eleve);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEleveMockMvc.perform(post("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEleves() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        // Get all the eleveList
        restEleveMockMvc.perform(get("/api/eleves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eleve.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomEleve").value(hasItem(DEFAULT_NOM_ELEVE.toString())))
            .andExpect(jsonPath("$.[*].prenomEleve").value(hasItem(DEFAULT_PRENOM_ELEVE.toString())));
    }
    
    @Test
    @Transactional
    public void getEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        // Get the eleve
        restEleveMockMvc.perform(get("/api/eleves/{id}", eleve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eleve.getId().intValue()))
            .andExpect(jsonPath("$.nomEleve").value(DEFAULT_NOM_ELEVE.toString()))
            .andExpect(jsonPath("$.prenomEleve").value(DEFAULT_PRENOM_ELEVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEleve() throws Exception {
        // Get the eleve
        restEleveMockMvc.perform(get("/api/eleves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        int databaseSizeBeforeUpdate = eleveRepository.findAll().size();

        // Update the eleve
        Eleve updatedEleve = eleveRepository.findById(eleve.getId()).get();
        // Disconnect from session so that the updates on updatedEleve are not directly saved in db
        em.detach(updatedEleve);
        updatedEleve
            .nomEleve(UPDATED_NOM_ELEVE)
            .prenomEleve(UPDATED_PRENOM_ELEVE);
        EleveDTO eleveDTO = eleveMapper.toDto(updatedEleve);

        restEleveMockMvc.perform(put("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isOk());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeUpdate);
        Eleve testEleve = eleveList.get(eleveList.size() - 1);
        assertThat(testEleve.getNomEleve()).isEqualTo(UPDATED_NOM_ELEVE);
        assertThat(testEleve.getPrenomEleve()).isEqualTo(UPDATED_PRENOM_ELEVE);
    }

    @Test
    @Transactional
    public void updateNonExistingEleve() throws Exception {
        int databaseSizeBeforeUpdate = eleveRepository.findAll().size();

        // Create the Eleve
        EleveDTO eleveDTO = eleveMapper.toDto(eleve);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEleveMockMvc.perform(put("/api/eleves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eleveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Eleve in the database
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEleve() throws Exception {
        // Initialize the database
        eleveRepository.saveAndFlush(eleve);

        int databaseSizeBeforeDelete = eleveRepository.findAll().size();

        // Delete the eleve
        restEleveMockMvc.perform(delete("/api/eleves/{id}", eleve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eleve> eleveList = eleveRepository.findAll();
        assertThat(eleveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eleve.class);
        Eleve eleve1 = new Eleve();
        eleve1.setId(1L);
        Eleve eleve2 = new Eleve();
        eleve2.setId(eleve1.getId());
        assertThat(eleve1).isEqualTo(eleve2);
        eleve2.setId(2L);
        assertThat(eleve1).isNotEqualTo(eleve2);
        eleve1.setId(null);
        assertThat(eleve1).isNotEqualTo(eleve2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EleveDTO.class);
        EleveDTO eleveDTO1 = new EleveDTO();
        eleveDTO1.setId(1L);
        EleveDTO eleveDTO2 = new EleveDTO();
        assertThat(eleveDTO1).isNotEqualTo(eleveDTO2);
        eleveDTO2.setId(eleveDTO1.getId());
        assertThat(eleveDTO1).isEqualTo(eleveDTO2);
        eleveDTO2.setId(2L);
        assertThat(eleveDTO1).isNotEqualTo(eleveDTO2);
        eleveDTO1.setId(null);
        assertThat(eleveDTO1).isNotEqualTo(eleveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eleveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eleveMapper.fromId(null)).isNull();
    }
}
