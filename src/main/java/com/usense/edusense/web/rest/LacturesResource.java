package com.usense.edusense.web.rest;
import com.usense.edusense.service.LacturesService;
import com.usense.edusense.web.rest.errors.BadRequestAlertException;
import com.usense.edusense.web.rest.util.HeaderUtil;
import com.usense.edusense.service.dto.LacturesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lactures.
 */
@RestController
@RequestMapping("/api")
public class LacturesResource {

    private final Logger log = LoggerFactory.getLogger(LacturesResource.class);

    private static final String ENTITY_NAME = "lactures";

    private final LacturesService lacturesService;

    public LacturesResource(LacturesService lacturesService) {
        this.lacturesService = lacturesService;
    }

    /**
     * POST  /lactures : Create a new lactures.
     *
     * @param lacturesDTO the lacturesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lacturesDTO, or with status 400 (Bad Request) if the lactures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lactures")
    public ResponseEntity<LacturesDTO> createLactures(@RequestBody LacturesDTO lacturesDTO) throws URISyntaxException {
        log.debug("REST request to save Lactures : {}", lacturesDTO);
        if (lacturesDTO.getId() != null) {
            throw new BadRequestAlertException("A new lactures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LacturesDTO result = lacturesService.save(lacturesDTO);
        return ResponseEntity.created(new URI("/api/lactures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lactures : Updates an existing lactures.
     *
     * @param lacturesDTO the lacturesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lacturesDTO,
     * or with status 400 (Bad Request) if the lacturesDTO is not valid,
     * or with status 500 (Internal Server Error) if the lacturesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lactures")
    public ResponseEntity<LacturesDTO> updateLactures(@RequestBody LacturesDTO lacturesDTO) throws URISyntaxException {
        log.debug("REST request to update Lactures : {}", lacturesDTO);
        if (lacturesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LacturesDTO result = lacturesService.save(lacturesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lacturesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lactures : get all the lactures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lactures in body
     */
    @GetMapping("/lactures")
    public List<LacturesDTO> getAllLactures() {
        log.debug("REST request to get all Lactures");
        return lacturesService.findAll();
    }

    /**
     * GET  /lactures/:id : get the "id" lactures.
     *
     * @param id the id of the lacturesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lacturesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lactures/{id}")
    public ResponseEntity<LacturesDTO> getLactures(@PathVariable Long id) {
        log.debug("REST request to get Lactures : {}", id);
        Optional<LacturesDTO> lacturesDTO = lacturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lacturesDTO);
    }

    /**
     * DELETE  /lactures/:id : delete the "id" lactures.
     *
     * @param id the id of the lacturesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lactures/{id}")
    public ResponseEntity<Void> deleteLactures(@PathVariable Long id) {
        log.debug("REST request to delete Lactures : {}", id);
        lacturesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
