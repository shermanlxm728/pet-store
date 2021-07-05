package com.bestbuy.petstore.webservice.api.controller;

import java.util.List;

import com.bestbuy.petstore.webservice.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bestbuy.petstore.webservice.model.Pet;
import com.bestbuy.petstore.webservice.validator.PetValidator;

import javax.validation.Valid;


@RestController
public class PetController {
	
	@Autowired
	@Qualifier("petService")
	private IService petService;
	
	@Autowired
	private PetValidator petValidator;
	
	
	@RequestMapping(method = RequestMethod.GET, path= "/pet/{id}")
	public ResponseEntity<Object> getPetById(@Valid @PathVariable String id) {
		petValidator.validatePetId(id);
		int idInteger = Integer.parseInt(id);
		Pet pet = petService.getPetById(idInteger);
		return new ResponseEntity<>(pet, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path= "/pets")
	public ResponseEntity<Object> getAllPets(){
		List<Pet> pets = petService.getAllPets();
		return new ResponseEntity<>(pets, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path= "/pet/{id}")
	public ResponseEntity<Object> deleteById(@Valid @PathVariable String id) {
		petValidator.validatePetId(id);
		int idInteger = Integer.parseInt(id);
		petService.deletePetById(idInteger);
		return new ResponseEntity<>(String.format("Pet with id: %s deleted.", id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path= "/pet/{id}")
	public ResponseEntity<Object> updatePetById(@Valid @RequestBody Pet pet, @Valid @PathVariable String id){
		petValidator.validatePetAndPetId(id, pet);
		int idInteger = Integer.parseInt(id);
		petService.updatePetById(idInteger, pet);
		return new ResponseEntity<>(String.format("Pet with id: %s updated.", id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, path= "/pet" )
	public ResponseEntity<Object> createPet(@Valid @RequestBody Pet pet){
		petValidator.validatePet(pet);
		petService.createPet(pet);
		return new ResponseEntity<>("Pet create successfully.", HttpStatus.CREATED);
	}
}
