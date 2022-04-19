package com.example.testmodule4.controller;

import com.example.testmodule4.model.City;
import com.example.testmodule4.service.city.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private ICityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> findAll(){
        List<City> cities = this.cityService.findAll();
        if (cities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(cities, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id){
        Optional<City> city = this.cityService.findById(id);
        if (!city.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(city.get(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city){
        return  new ResponseEntity<>(this.cityService.save(city), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<City> editCity(@PathVariable Long id, @RequestBody City city){
        Optional<City> newCity = this.cityService.findById(id);
        if (!newCity.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newCity.get().setId(id);
        newCity.get().setName(city.getName());
        newCity.get().setCountry(city.getCountry());
        newCity.get().setArea(city.getArea());
        newCity.get().setPopulation(city.getPopulation());
        newCity.get().setDescription(city.getDescription());
        newCity.get().setGdp(city.getGdp());
        this.cityService.save(newCity.get());
        return new ResponseEntity<>(newCity.get(),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<City> deleteCity(@PathVariable Long id){
        Optional<City> city  = this.cityService.findById(id);
        if (!city.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.cityService.deleteById(id);
        return new ResponseEntity<>(city.get(),HttpStatus.OK);
    }
}
