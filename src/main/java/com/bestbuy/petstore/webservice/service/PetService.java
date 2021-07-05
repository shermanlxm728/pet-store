package com.bestbuy.petstore.webservice.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bestbuy.petstore.webservice.model.Pet;
import com.bestbuy.petstore.webservice.exceptions.BadRequestException;
import com.bestbuy.petstore.webservice.exceptions.PetNotFoundException;
import com.bestbuy.petstore.webservice.exceptions.SqlConnectionException;
import com.bestbuy.petstore.webservice.repository.PetRepository;


@Component
@AllArgsConstructor
@NoArgsConstructor
public class PetService implements IService{
	
	@Autowired
	private PetRepository petRepository;
	
	@Override
	public Pet getPetById(int id) {
		try {
			Optional<Pet> pet = petRepository.findById(id);
			if (!pet.isPresent()) {
				throw new PetNotFoundException(String.format("Pet with id: %d not found.", id));
			}
			else {
				return pet.get();
			}	
		}
		catch(SqlConnectionException ex){
			throw new SqlConnectionException(String.format("Error on getPetById with message %s. ", ex.getMessage()));
		}
	}

	public List<Pet> getAllPets() {
		try {
			List<Pet> pets = petRepository.findAllPets();
			return pets;
		}
		catch(SqlConnectionException ex){
			throw new SqlConnectionException(String.format("Error on getAllPets with message %s. ", ex.getMessage()));
		}
	}

 
	public void deletePetById(int id) {
		if(!petRepository.existsById(id)) {
			throw new PetNotFoundException(String.format("Pet with id: %d not found.", id));
		}

		try {
			petRepository.deleteById(id);
		}
		catch(SqlConnectionException ex) {
			throw new SqlConnectionException(String.format("Error on deletePet with message %s. ", ex.getMessage()));
		}
	}

	public Pet createPet(Pet pet) {
		Pet petInDB = petRepository.findByName(pet.getName());
		if(petInDB != null) {
			throw new BadRequestException(String.format("Pet with name: %s in database already exsit. ", petInDB.getName()));
		}
		
		try {
			return petRepository.save(pet);
		}
		catch(SqlConnectionException ex){
			throw new SqlConnectionException(String.format("Error on createPet with message %s. ", ex.getMessage()));
		}
	}


	public Pet updatePetById(int id, Pet pet) {		
		Pet petInDB = getPetById(id);
		if(pet.getName() != null) {
			petInDB.setName(pet.getName());
		}
		
		if(pet.getPetType() != null) {
			petInDB.setPetType(pet.getPetType());
			
		}
		
		if(pet.getPrice() > 0) {
			petInDB.setPrice(pet.getPrice());
		}
		
		try {
			return petRepository.save(petInDB);
		}
		catch(SqlConnectionException ex){
			throw new SqlConnectionException(String.format("Error on updatePetById with message %s. ", ex.getMessage()));
		}
	}
}
