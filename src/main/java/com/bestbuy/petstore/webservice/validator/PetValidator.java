package com.bestbuy.petstore.webservice.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.bestbuy.petstore.webservice.model.Pet;
import com.bestbuy.petstore.webservice.exceptions.BadRequestException;
@Component
public class PetValidator {
	public void validatePet(Pet pet) {
		if(StringUtils.isEmpty(pet.getName())){
			throw new BadRequestException("Pet name value could not miss and value can not be null or empty");
		}
		if(StringUtils.isEmpty(pet.getPetType())) {
			throw new BadRequestException("Pet type value could not miss and value can not be null or empty");
		}
		if(pet.getPrice() <= 0) {
			throw new BadRequestException("Pet price could not miss and value can not less than zero");
		}
	}

	private boolean isValidId(String id) {
		Pattern pattern = Pattern.compile("^[1-9]\\d*$");
		return (id != null && pattern.matcher(id).matches());
	}

	public void validatePetId(String id) {
		if(!isValidId(id)) {
			throw new BadRequestException("Id format is not correct");
		}
	}

	public void validatePetAndPetId(String id, Pet pet) {
		validatePetId(id);
		validatePet(pet);
	}
}
