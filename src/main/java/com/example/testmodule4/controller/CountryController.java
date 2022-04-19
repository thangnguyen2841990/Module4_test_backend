package com.example.testmodule4.controller;

import com.example.testmodule4.model.City;
import com.example.testmodule4.model.Country;
import com.example.testmodule4.service.city.ICityService;
import com.example.testmodule4.service.country.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("*")
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    private ICountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> findAll(){
        List<Country> country = this.countryService.findAll();
        if (country.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(country, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable Long id){
        Optional<Country> country = this.countryService.findById(id);
        if (!country.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(country.get(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country Country){
        return  new ResponseEntity<>(this.countryService.save(Country), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<Country> editCountry(@PathVariable Long id, @RequestBody Country Country){
        Optional<Country> newCountry = this.countryService.findById(id);
        if (!newCountry.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newCountry.get().setId(id);
        return new ResponseEntity<>(this.countryService.save(newCountry.get()),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable Long id){
        Optional<Country> Country  = this.countryService.findById(id);
        if (!Country.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.countryService.deleteById(id);
        return new ResponseEntity<>(Country.get(),HttpStatus.OK);
    }
}
