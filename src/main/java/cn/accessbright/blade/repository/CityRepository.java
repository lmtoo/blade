package cn.accessbright.blade.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.accessbright.blade.domain.City;

public interface CityRepository extends CrudRepository<City, String> {

	@Transactional
	@Modifying
	@Query("update City t set t.population=:population where t.id=:id")
	int updatePopulationById(@Param("population") int population, @Param("id") String id);

	@Query("select t from City t")
	List<City> getList();

	List<City> findCityByName(@Param("name") String name);
}