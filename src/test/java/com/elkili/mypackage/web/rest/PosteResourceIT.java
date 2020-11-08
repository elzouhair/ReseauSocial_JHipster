package com.elkili.mypackage.web.rest;

import com.elkili.mypackage.ReseausocialjhApp;
import com.elkili.mypackage.domain.Poste;
import com.elkili.mypackage.domain.User;
import com.elkili.mypackage.repository.PosteRepository;
import com.elkili.mypackage.service.PosteService;
import com.elkili.mypackage.service.dto.PosteDTO;
import com.elkili.mypackage.service.mapper.PosteMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.elkili.mypackage.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elkili.mypackage.domain.enumeration.Type;
/**
 * Integration tests for the {@Link PosteResource} REST controller.
 */
@SpringBootTest(classes = ReseausocialjhApp.class)
public class PosteResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Type DEFAULT_TYPE = Type.COURS;
    private static final Type UPDATED_TYPE = Type.TP;

    private static final byte[] DEFAULT_FICHIER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_CONTENT_TYPE = "image/png";

    @Autowired
    private PosteRepository posteRepository;

    @Autowired
    private PosteMapper posteMapper;

    @Autowired
    private PosteService posteService;

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

    private MockMvc restPosteMockMvc;

    private Poste poste;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PosteResource posteResource = new PosteResource(posteService);
        this.restPosteMockMvc = MockMvcBuilders.standaloneSetup(posteResource)
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
    public static Poste createEntity(EntityManager em) {
        Poste poste = new Poste()
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .fichier(DEFAULT_FICHIER)
            .fichierContentType(DEFAULT_FICHIER_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        poste.setUser(user);
        return poste;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poste createUpdatedEntity(EntityManager em) {
        Poste poste = new Poste()
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        poste.setUser(user);
        return poste;
    }

    @BeforeEach
    public void initTest() {
        poste = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoste() throws Exception {
        int databaseSizeBeforeCreate = posteRepository.findAll().size();

        // Create the Poste
        PosteDTO posteDTO = posteMapper.toDto(poste);
        restPosteMockMvc.perform(post("/api/postes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isCreated());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeCreate + 1);
        Poste testPoste = posteList.get(posteList.size() - 1);
        assertThat(testPoste.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPoste.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPoste.getFichier()).isEqualTo(DEFAULT_FICHIER);
        assertThat(testPoste.getFichierContentType()).isEqualTo(DEFAULT_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPosteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = posteRepository.findAll().size();

        // Create the Poste with an existing ID
        poste.setId(1L);
        PosteDTO posteDTO = posteMapper.toDto(poste);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPosteMockMvc.perform(post("/api/postes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostes() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        // Get all the posteList
        restPosteMockMvc.perform(get("/api/postes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poste.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fichierContentType").value(hasItem(DEFAULT_FICHIER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER))));
    }
    
    @Test
    @Transactional
    public void getPoste() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        // Get the poste
        restPosteMockMvc.perform(get("/api/postes/{id}", poste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poste.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fichierContentType").value(DEFAULT_FICHIER_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier").value(Base64Utils.encodeToString(DEFAULT_FICHIER)));
    }

    @Test
    @Transactional
    public void getNonExistingPoste() throws Exception {
        // Get the poste
        restPosteMockMvc.perform(get("/api/postes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoste() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        int databaseSizeBeforeUpdate = posteRepository.findAll().size();

        // Update the poste
        Poste updatedPoste = posteRepository.findById(poste.getId()).get();
        // Disconnect from session so that the updates on updatedPoste are not directly saved in db
        em.detach(updatedPoste);
        updatedPoste
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .fichier(UPDATED_FICHIER)
            .fichierContentType(UPDATED_FICHIER_CONTENT_TYPE);
        PosteDTO posteDTO = posteMapper.toDto(updatedPoste);

        restPosteMockMvc.perform(put("/api/postes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isOk());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeUpdate);
        Poste testPoste = posteList.get(posteList.size() - 1);
        assertThat(testPoste.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPoste.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPoste.getFichier()).isEqualTo(UPDATED_FICHIER);
        assertThat(testPoste.getFichierContentType()).isEqualTo(UPDATED_FICHIER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPoste() throws Exception {
        int databaseSizeBeforeUpdate = posteRepository.findAll().size();

        // Create the Poste
        PosteDTO posteDTO = posteMapper.toDto(poste);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPosteMockMvc.perform(put("/api/postes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poste in the database
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoste() throws Exception {
        // Initialize the database
        posteRepository.saveAndFlush(poste);

        int databaseSizeBeforeDelete = posteRepository.findAll().size();

        // Delete the poste
        restPosteMockMvc.perform(delete("/api/postes/{id}", poste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poste> posteList = posteRepository.findAll();
        assertThat(posteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poste.class);
        Poste poste1 = new Poste();
        poste1.setId(1L);
        Poste poste2 = new Poste();
        poste2.setId(poste1.getId());
        assertThat(poste1).isEqualTo(poste2);
        poste2.setId(2L);
        assertThat(poste1).isNotEqualTo(poste2);
        poste1.setId(null);
        assertThat(poste1).isNotEqualTo(poste2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PosteDTO.class);
        PosteDTO posteDTO1 = new PosteDTO();
        posteDTO1.setId(1L);
        PosteDTO posteDTO2 = new PosteDTO();
        assertThat(posteDTO1).isNotEqualTo(posteDTO2);
        posteDTO2.setId(posteDTO1.getId());
        assertThat(posteDTO1).isEqualTo(posteDTO2);
        posteDTO2.setId(2L);
        assertThat(posteDTO1).isNotEqualTo(posteDTO2);
        posteDTO1.setId(null);
        assertThat(posteDTO1).isNotEqualTo(posteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(posteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(posteMapper.fromId(null)).isNull();
    }
}
