package net.codejava.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.model.Product;
import net.codejava.model.Products;

@Controller
public class ProductController {
 
	String token;

	@RequestMapping("/products")
	public String viewProducts( Model model) {
		RestTemplate restTemplate;
		Products result;
		restTemplate=new RestTemplate();
		result= restTemplate.getForObject("http://localhost:8878/product/", Products.class);
		model.addAttribute("listProducts",result);
		return "products";
	}

//	@RequestMapping("/products")
//	public String viewProducts( Model model) {
//		RestTemplate restTemplate;
//		ResponseEntity<Products> result;
//		restTemplate=new RestTemplate();
//
//
//		result=restTemplate.exchange("http://localhost:8878/product/", HttpMethod.GET, null, new ParameterizedTypeReference<Products>() {
//		});
//		if (result.getStatusCode() == HttpStatus.OK) {
//			log.debug("Request successful");
//			User user = response.getBody();
//			this.updateCache(user);
//			return user;
//		}
//		model.addAttribute("listProducts",result);
//		return "products";
//	}

	@RequestMapping("/products/{id}")
	public ModelAndView showProductDetail(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("product_detail");
		RestTemplate restTemplate;
		Product result;
		restTemplate=new RestTemplate();
		result= restTemplate.getForObject("http://localhost:8878/product/edit/"+id, Product.class);
		mav.addObject("product", result);
		return mav;
	}

	@RequestMapping("/index")
	public String viewHomePage(@RequestParam("access_token") String accesstoken, Model model) {
		token=accesstoken;
		//System.out.println("token= "+token);
		RestTemplate restTemplate;
		Products result;
		restTemplate=new RestTemplate();
		result= restTemplate.getForObject("http://localhost:8878/product/", Products.class);
		model.addAttribute("listProducts",result);
		model.addAttribute("accessToken",token);
		return "index";	
	}
	
	@RequestMapping("/new")
	public String showNewProductPage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		model.addAttribute("accessToken", token);
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		RestTemplate restTemplate;
		restTemplate=new RestTemplate();
	    restTemplate.postForObject("http://localhost:8878/product/save", product, Product.class);
		return "redirect:/index?access_token="+token;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		RestTemplate restTemplate;
		Product result;
		restTemplate=new RestTemplate();
		result= restTemplate.getForObject("http://localhost:8878/product/edit/"+id, Product.class);
		mav.addObject("product", result);
		mav.addObject("accessToken", token);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		RestTemplate restTemplate;
		restTemplate=new RestTemplate();
		restTemplate.getForObject("http://localhost:8878/product/delete/"+id, Product.class);
		return "redirect:/index?access_token="+token;
	}
	
}
