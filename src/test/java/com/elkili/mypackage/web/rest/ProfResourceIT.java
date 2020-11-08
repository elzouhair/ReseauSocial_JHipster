package com.elkili.mypackage.web.rest;

import com.elkili.mypackage.ReseausocialjhApp;
import com.elkili.mypackage.domain.Prof;
import com.elkili.mypackage.repository.ProfRepository;
import com.elkili.mypackage.service.ProfService;
import com.elkili.mypackage.service.dto.ProfDTO;
import com.elkili.mypackage.service.mapper.ProfMapper;
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
 * Integration tests for the {@Link ProfResource} REST controller.
 */
@SpringBootTest(classes = ReseausocialjhApp.class)
public class ProfResourceIT {

    private static final String DEFAULT_NOM_PROF = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PROF = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_PROF = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_PROF = "BBBBBBBBBB";

    @Autowired
    private ProfRepository profRepository;

    @Autowired
    private ProfMapper profMapper;

    @Autowired
    private ProfService profService;

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

    private MockMvc restProfMockMvc;

    private Prof prof;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfResource profResource = new ProfResource(profService);
        this.restProfMockMvc = MockMvcBuilders.standaloneSetup(profResource)
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
    public static Prof createEntity(EntityManager em) {
        Prof prof = new Prof()
            .nomProf(DEFAULT_NOM_PROF)
            .prenomProf(DEFAULT_PRENOM_PROF);
        return prof;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prof createUpdatedEntity(EntityManager em) {
        Prof prof = new Prof()
            .nomProf(UPDATED_NOM_PROF)
            .prenomProf(UPDATED_PRENOM_PROF);
        return prof;
    }

    @BeforeEach
    public void initTest() {
        prof = createEntity(em);
    }

    @Test
    @Transactional
    public void createProf() throws Exception {
        int databaseSizeBeforeCreate = profRepository.findAll().size();

        // Create the Prof
        ProfDTO profDTO = profMapper.toDto(prof);
        restProfMockMvc.perform(post("/api/profs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profDTO)))
            .andExpect(status().isCreated());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeCreate + 1);
        Prof testProf = profList.get(profList.size() - 1);
        assertThat(testProf.getNomProf()).isEqualTo(DEFAULT_NOM_PROF);
        assertThat(testProf.getPrenomProf()).isEqualTo(DEFAULT_PRENOM_PROF);
    }

    @Test
    @Transactional
    public void createProfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profRepository.findAll().size();

        // Create the Prof with an existing ID
        prof.setId(1L);
        ProfDTO profDTO = profMapper.toDto(prof);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfMockMvc.perform(post("/api/profs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfs() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        // Get all the profList
        restProfMockMvc.perform(get("/api/profs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prof.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomProf").value(hasItem(DEFAULT_NOM_PROF.toString())))
            .andExpect(jsonPath("$.[*].prenomProf").value(hasItem(DEFAULT_PRENOM_PROF.toString())));
    }
    
    @Test
    @Transactional
    public void getProf() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        // Get the prof
        restProfMockMvc.perform(get("/api/profs/{id}", prof.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prof.getId().intValue()))
            .andExpect(jsonPath("$.nomProf").value(DEFAULT_NOM_PROF.toString()))
            .andExpect(jsonPath("$.prenomProf").value(DEFAULT_PRENOM_PROF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProf() throws Exception {
        // Get the prof
        restProfMockMvc.perform(get("/api/profs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProf() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        int databaseSizeBeforeUpdate = profRepository.findAll().size();

        // Update the prof
        Prof updatedProf = profRepository.findById(prof.getId()).get();
        // Disconnect from session so that the updates on updatedProf are not directly saved in db
        em.detach(updatedProf);
        updatedProf
            .nomProf(UPDATED_NOM_PROF)
            .prenomProf(UPDATED_PRENOM_PROF);
        ProfDTO profDTO = profMapper.toDto(updatedProf);

        restProfMockMvc.perform(put("/api/profs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profDTO)))
            .andExpect(status().isOk());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeUpdate);
        Prof testProf = profList.get(profList.size() - 1);
        assertThat(testProf.getNomProf()).isEqualTo(UPDATED_NOM_PROF);
        assertThat(testProf.getPrenomProf()).isEqualTo(UPDATED_PRENOM_PROF);
    }

    @Test
    @Transactional
    public void updateNonExistingProf() throws Exception {
        int databaseSizeBeforeUpdate = profRepository.findAll().size();

        // Create the Prof
        ProfDTO profDTO = profMapper.toDto(prof);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfMockMvc.perform(put("/api/profs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProf() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        int databaseSizeBeforeDelete = profRepository.findAll().size();

        // Delete the prof
        restProfMockMvc.perform(delete("/api/profs/{id}", prof.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prof.class);
        Prof prof1 = new Prof();
        prof1.setId(1L);
        Prof prof2 = new Prof();
        prof2.setId(prof1.getId());
        assertThat(prof1).isEqualTo(prof2);
        prof2.setId(2L);
        assertThat(prof1).isNotEqualTo(prof2);
        prof1.setId(null);
        assertThat(prof1).isNotEqualTo(prof2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfDTO.class);
        ProfDTO profDTO1 = new ProfDTO();
        profDTO1.setId(1L);
        ProfDTO profDTO2 = new ProfDTO();
        assertThat(profDTO1).isNotEqualTo(profDTO2);
        profDTO2.setId(profDTO1.getId());
        assertThat(profDTO1).isEqualTo(profDTO2);
        profDTO2.setId(2L);
        assertThat(profDTO1).isNotEqualTo(profDTO2);
        profDTO1.setId(null);
        assertThat(profDTO1).isNotEqualTo(profDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profMapper.fromId(null)).isNull();
    }
}
