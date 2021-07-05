package com.bestbuy.petstore.webservice.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bestbuy.petstore.webservice.model.Pet;
 

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>{
	Pet findByName(String name);

	@Query(value = "SELECT * FROM pet LIMIT 100", nativeQuery = true)
	List<Pet> findAllPets();
}
