package com.bestbuy.petstore.webservice.service;

import java.util.List;

import com.bestbuy.petstore.webservice.model.Pet;

public interface IService {
	List<Pet> getAllPets();
	void deletePetById(int id);
	Pet createPet(Pet pet);
	Pet updatePetById(int id, Pet pet);
	Pet getPetById(int id);
}
