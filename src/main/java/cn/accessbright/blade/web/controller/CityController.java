package cn.accessbright.blade.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.accessbright.blade.domain.City;
import cn.accessbright.blade.repository.CityRepository;
import cn.accessbright.blade.service.CityService;

@RestController
public class CityController {
	@Autowired
	private CityService cityService;

	@Autowired
	private CityRepository repository;

	@RequestMapping("/citys")
	public List<City> getCityList() {
		return cityService.getList();
	}

	@RequestMapping("/repcitys")
	public List<City> repCityList() {
		return repository.getList();
	}

	@RequestMapping("/citys/save")
	public City createOne() {
		City city = new City();
		city.setName("北京");
		city.setContryCode("010");
		city.setDistrict("20000");
		city.setPopulation(2000000);
		repository.save(city);
		return city;
	}

	@RequestMapping("/bj")
	public List<City> getBJ() {
		return repository.findCityByName("北京");
	}
}