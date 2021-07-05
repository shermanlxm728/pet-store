package com.bestbuy.petstore.webservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Integer petId;
	
	private String name;

	@JsonProperty(value = "type")
	private String petType;
	
	private double price;
	
}
