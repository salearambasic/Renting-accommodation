package com.project.Rentingaccommodation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.service.CityService;

@RestController
@RequestMapping(value="/api/cities")
public class CityController {
	@Autowired
	private CityService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<City>> getCities() {
		List<City> cities = service.findAll();
		return new ResponseEntity<>(cities, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getCity(@PathVariable Long id) {
		City city = service.findOne(id);
		if (city == null) {
			return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addCity(@RequestBody City city) {
		if (city.getName() == null || city.getName() == "" ||
			city.getZipcode() == null || city.getZipcode() == "" ||
			city.getCountry() == null) {
			return new ResponseEntity<>("All fields are required.", HttpStatus.FORBIDDEN);
		}
		for (City c : service.findAll()) {
			if (c.getName().equals(city.getName()) && c.getCountry().equals(city.getCountry())) {
				return new ResponseEntity<>("This city already exists.", HttpStatus.NOT_ACCEPTABLE);
			}
		}
		service.save(city);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateCity(@PathVariable Long id, @RequestBody City city) {
		if (city.getName() == null || city.getName() == "" ||
			city.getZipcode() == null || city.getZipcode() == "" ||
			city.getCountry() == null) {
			return new ResponseEntity<>("All fields are required.", HttpStatus.FORBIDDEN);
		}
		City foundCity = service.findOne(id);
		if (foundCity != null) {
			foundCity.setCountry(city.getCountry());
			foundCity.setName(city.getName());
			foundCity.setZipcode(city.getZipcode());
			service.save(foundCity);
			return new ResponseEntity<>(foundCity, HttpStatus.OK);
		}
		return new ResponseEntity<>("City doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCity(@PathVariable Long id) {
		City city = service.delete(id);
		if (city == null) {
			return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
}
