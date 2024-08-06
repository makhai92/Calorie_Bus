package kr.co.caloriebus.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import kr.co.caloriebus.product.model.service.ProductService;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String home(Model model) {
    	List list = productService.selectAllProduct();
    	model.addAttribute("list",list);
        
        return "index"; 
    }
}
