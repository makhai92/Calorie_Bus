package kr.co.caloriebus.food.cotroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.caloriebus.food.model.dto.Food;


@Controller
@RequestMapping(value = "/foodNutrition")
public class FoodController {
	
	@GetMapping(value = "/food")
	public String food() {
		return "foodNutrition/food";
	}
	
	//음식
		@ResponseBody
		@GetMapping(value = "/foodTotal")
		public List Food(String pageNo) {
			String url = "https://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1";
			//serviceKey 사용시 자바에서 용청할 땐 decode된 키 사용
			String serviceKey ="4pwXqbRSi397KU+gN7TRkoTxPKj2oT8AQMmGGiLMeeTM8gM6yWf60SsPzaoY/KUb2D/vKrGX4vbW+VfURqDo4g==";
			String numOfRows = "10";
			String resultType ="xml";
			List list = new ArrayList<Food>();	
			try {
				Document document = Jsoup.connect(url)
								.data("serviceKey",serviceKey)
								.data("pageNo",pageNo)
								.data("numOfRows",numOfRows)	
								.ignoreContentType(true)
								.get();
			Elements elements = document.select("item"); //결과로 받은 XML문서중 item 태그만 선택(10개)
			for(int i=0;i<elements.size();i++) {
				Element item = elements.get(i);
				String foodName = item.select("DESC_KOR").text(); //식품이름
				String kcal = item.select("NUTR_CONT1").text();	//열량
				String prot = item.select("NUTR_CONT2").text();	//탄수화물
				String fat = item.select("NUTR_CONT3").text();	//단백질
				String card = item.select("NUTR_CONT4").text();	//지방
				String sugar = item.select("NUTR_CONT5").text(); //당류
				String nat = item.select("NUTR_CONT6").text();	//나트륨
				
				Food f = new Food(foodName,kcal,prot,fat,card,sugar,nat);
				list.add(f);
				}
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		
		

}