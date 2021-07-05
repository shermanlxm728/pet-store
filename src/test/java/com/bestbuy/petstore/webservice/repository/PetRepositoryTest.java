package com.bestbuy.petstore.webservice.repository;

import com.bestbuy.petstore.webservice.model.Pet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Test
    public void findByName(){
        Pet pet = petRepository.findByName("Max");
        assertNotNull(pet);
    }

    @Test
    public void findAllPets(){
       List<Pet> pets = petRepository.findAllPets();
       assertEquals(pets.size(), 2);
    }
}