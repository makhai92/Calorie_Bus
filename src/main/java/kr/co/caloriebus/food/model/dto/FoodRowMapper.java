package kr.co.caloriebus.food.model.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FoodRowMapper implements RowMapper<Food>{

	@Override
	public Food mapRow(ResultSet rs, int rowNum) throws SQLException {
		Food f = new Food();
		f.setFoodName(rs.getString("foodName"));
		f.setKcal(rs.getString("kcal"));
		f.setProt(rs.getString("prot"));
		f.setFat(rs.getString("fat"));
		f.setCard(rs.getString("card"));
		f.setSugar(rs.getString("sugar"));
		f.setNat(rs.getString("nat"));
		return f;
	}

}
