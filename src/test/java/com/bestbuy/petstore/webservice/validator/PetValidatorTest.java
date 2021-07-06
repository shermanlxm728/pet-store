package com.bestbuy.petstore.webservice.validator;

import com.bestbuy.petstore.webservice.model.Pet;
import com.bestbuy.petstore.webservice.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class PetValidatorTest {
    PetValidator petValidator;
    Pet pet;

    @BeforeEach
    void  setUp(){
        petValidator = new PetValidator();
    }

    @Test
    void validatePet_EmptyName() {
        pet = Pet.builder().petId(101).name("").price(16).petType("dog").build();
        assertThrows(BadRequestException.class, () -> petValidator.validatePet(pet));
    }

    @Test
    void validatePet_NullName() {
        pet = Pet.builder().petId(101).price(16).petType("dog").build();
        assertThrows(BadRequestException.class, () -> petValidator.validatePet(pet));
    }

    @Test
    void validatePet_EmptyType() {
        pet = Pet.builder().petId(101).name("Sean").price(16).petType("").build();
        assertThrows(BadRequestException.class, () -> petValidator.validatePet(pet));
    }

    @Test
    void validatePet_NullType() {
        pet = Pet.builder().petId(101).name("Sean").price(16).build();
        assertThrows(BadRequestException.class, () -> petValidator.validatePet(pet));
    }

    @Test
    void validatePet_NoPrice() {
        pet = Pet.builder().petId(101).name("").petType("dog").build();
        assertThrows(BadRequestException.class, () -> petValidator.validatePet(pet));
    }

    @Test
    void validatePet_PriceWithZero() {
        pet = Pet.builder().petId(101).name("").price(0).petType("dog").build();
        assertThrows(BadRequestException.class, () -> petValidator.validatePet(pet));
    }

    @Test
    void validatePetId() {
        String id = "123ABC";
        assertThrows(BadRequestException.class, ()-> petValidator.validatePetId(id));
    }

    @Test
    void validatePetAndPetId_HappyPath() {
        String id = "101";
        pet = Pet.builder().petId(101).name("Sean").price(10).petType("dog").build();
        assertDoesNotThrow(() -> petValidator.validatePetAndPetId(id, pet));
    }
}