package cn.accessbright.blade.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cn.accessbright.blade.domain.City;

@Service
public class CityService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<City> getList(){
		String sql="select * from city";
		return jdbcTemplate.query(sql, new RowMapper<City>(){
			@Override
			public City mapRow(ResultSet rs, int rowNum) throws SQLException {
				City city=new City();
				city.setId(rs.getInt("ID"));
				city.setName(rs.getString("NAME"));
				city.setDistrict(rs.getString("DISTRICT"));
				city.setContryCode(rs.getString("COUNTRYCODE"));
				city.setPopulation(rs.getInt("POPULATION"));
				return city;
			}
		});
	}
}
