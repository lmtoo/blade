package cn.accessbright.blade.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "city_key_generator")
	private Integer id;

	@Column(name = "NAME")
	private String name;	

	@Column(name = "CONTRYCODE")
	private String contryCode;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "POPULATION")
	private int population;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContryCode() {
		return contryCode;
	}

	public void setContryCode(String contryCode) {
		this.contryCode = contryCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}