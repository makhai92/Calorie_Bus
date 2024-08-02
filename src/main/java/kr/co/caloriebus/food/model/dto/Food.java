package kr.co.caloriebus.food.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Food {
	private String foodName;			//식품명
	private String kcal;				//에너지(칼로리)
	private String prot;				//단백질
	private String fat;					//지방
	private String card;				//탄수화물
	private String sugar;				//당류
	private String nat;					//나트륨
	
}