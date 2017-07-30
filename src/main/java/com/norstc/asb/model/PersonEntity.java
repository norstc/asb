package com.norstc.asb.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public class PersonEntity extends NamedEntity{
	@Column(name="first_name")
	@NotEmpty
	private String firstName;
	
	@Column(name="last_name")
	@NotEmpty
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	
}
