package com.cx.demo.secure.commons;

import java.io.Serializable;

public class CxDemoUser implements Serializable {

	private static final long serialVersionUID = 7890L;
	
	private String id;
	private String name;
	private String surname;
	private String country;

	public CxDemoUser() {}

	public CxDemoUser(String id, String name, String surname, String country) {
		setId(id);
		setName(name);
		setSurname(surname);
		setCountry(country);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return name + " " + surname;
	}

	@Override
	public int hashCode() {
		final int prime = CxDemoProperties.HASH_SEED;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CxDemoUser other = (CxDemoUser) obj;
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
