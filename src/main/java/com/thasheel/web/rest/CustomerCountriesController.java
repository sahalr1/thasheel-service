package com.thasheel.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thasheel.domain.Country;
import com.thasheel.domain.Customer;
import com.thasheel.service.CountryService;
import com.thasheel.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;


@RestController
@RequestMapping("/api/customer")
public class CustomerCountriesController {
	
	 private final Logger log = LoggerFactory.getLogger(CustomerCountriesController.class);

	    private static final String ENTITY_NAME = "country";

	    @Value("${jhipster.clientApp.name}")
	    private String applicationName;

	    private final CountryService countryService;

	    public CustomerCountriesController(CountryService countryService) {
	        this.countryService = countryService;
	    }

	    

	    /**
	     * {@code GET  /countries} : get all the countries.
	     *
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
	     */
	    @GetMapping("/countries")
	    public List<Country> getAllCountries() {
	        log.debug("REST request to get all Countries");
	        return countryService.findAll();
	    }

	    /**
	     * {@code GET  /countries/:id} : get the "id" country.
	     *
	     * @param id the id of the country to retrieve.
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the country, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/countries/{id}")
	    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
	        log.debug("REST request to get Country : {}", id);
	        Optional<Country> country = countryService.findOne(id);
	        return ResponseUtil.wrapOrNotFound(country);
	    }
	    
	    
	    @PutMapping("/countries/{countyId}")
	    public ResponseEntity<Customer> setCountry(@PathVariable Long countyId) {
	        log.debug("REST request to set Country for customer : {}", countyId);
	        Optional<Customer> country = countryService.setCountryForCustomer(countyId);
	        return ResponseUtil.wrapOrNotFound(country);
	    }
	    

}
