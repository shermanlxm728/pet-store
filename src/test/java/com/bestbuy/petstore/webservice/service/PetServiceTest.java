package com.bestbuy.petstore.webservice.service;

import com.bestbuy.petstore.webservice.model.Pet;
import com.bestbuy.petstore.webservice.exceptions.BadRequestException;
import com.bestbuy.petstore.webservice.exceptions.PetNotFoundException;
import com.bestbuy.petstore.webservice.repository.PetRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetService petService;

    private final int petId = 101;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getPetById() {
        //Given
    	Pet returnPet;
        returnPet = new Pet();
        returnPet.setName("Sean");
        returnPet.setPetId(101);
        returnPet.setPrice(1.0);
        returnPet.setPetType("dog");
        //When
        Mockito.lenient().when(petRepository.findById(anyInt())).thenReturn(Optional.of(returnPet));
        Pet pet = petService.getPetById(petId);
       
        //THEN
        Mockito.verify(petRepository, Mockito.times(1)).findById(anyInt());
        assertNotNull(pet);
        assertTrue(pet.getPetId().equals(101));
        Assert.assertEquals(pet.getName(), "Sean");
        Assert.assertEquals(pet.getPetType(), "dog");
        Assert.assertEquals(pet.getPrice(), 1,0);
    }

    @Test
    void getPetByIdNotFound(){
        Mockito.when(petRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.getPetById(petId));
        Mockito.verify(petRepository, Mockito.times(1)).findById(anyInt());
    }


    @Test
    void getAllPets() {
        //Given
        List<Pet> returnPets = new ArrayList<>();
        returnPets.add(Pet.builder().petId(1).name("Sean").petType("cat").price(15).build());
        returnPets.add(Pet.builder().petId(1).name("Simon").petType("dog").price(16).build());
        //WHEN
        Mockito.when(petRepository.findAllPets()).thenReturn(returnPets);
        List<Pet> pets = petService.getAllPets();
        //THEN
        Mockito.verify(petRepository, Mockito.times(1)).findAllPets();
        assertNotNull(pets);
        assertEquals(pets.size(), 2);
        
    }

    @Test
    void createPet(){
        //Given
        Pet returnPet = Pet.builder().petId(1).name("Sean").petType("cat").price(15).build();
        //WHEN
        Mockito.when(petRepository.save(any())).thenReturn(returnPet);
        Pet pet = petService.createPet(Pet.builder().petId(1).name("Sean").petType("cat").price(15).build());
        //THEN
        Mockito.verify(petRepository, Mockito.times(1)).save(any());
        assertNotNull(pet);
        assertTrue(pet.getPetId().equals(1));
        Assert.assertEquals(pet.getName(), "Sean");
        Assert.assertEquals(pet.getPetType(), "cat");
        Assert.assertEquals(pet.getPrice(), 15,0);
    }

    @Test
    void createPetAlreadyExsit(){
        Mockito.when(petRepository.findByName("Sean")).thenReturn(Pet.builder().petId(1).name("Sean").petType("cat").price(15).build());
        assertThrows(BadRequestException.class, () -> petService.createPet(Pet.builder().petId(1).name("Sean").petType("cat").price(15).build()));

    }

    @Test
    void updatePetById(){
        //Given
        Pet petToBeUpdate = Pet.builder().petId(101).name("Sean").petType("cat").price(30).build();
        Mockito.lenient().when(petRepository.findById(anyInt())).thenReturn(Optional.of(petToBeUpdate));
        //update price in this case to 30
        Pet updatedPet = Pet.builder().petId(101).name("Sean").petType("cat").price(30).build();
        Mockito.when(petRepository.save(any())).thenReturn(petToBeUpdate);
        //WHEN
        Pet pet = petService.updatePetById(petId, Pet.builder().petId(101).name("Sean").petType("cat").price(30).build());
        assertNotNull(pet);
        assertTrue(pet.getPetId().equals(101));
        Assert.assertEquals(pet.getName(), "Sean");
        Assert.assertEquals(pet.getPetType(), "cat");
        Assert.assertEquals(pet.getPrice(), 30,0);
    }


    @Test
    void deletePetById(){
       //When
       Mockito.when(petRepository.existsById(101)).thenReturn(true).thenReturn(false);
       petService.deletePetById(101);
       //THEN
       Mockito.verify(petRepository, Mockito.times(1)).deleteById(any());
       assertEquals(petRepository.existsById(101), false);
    }

    @Test
    void deletePetByIdNotFound(){
        Mockito.when(petRepository.existsById(101)).thenReturn(false);
        assertThrows(PetNotFoundException.class, () -> petService.deletePetById(101));
    }
}