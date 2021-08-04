package net.codejava.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.codejava.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import net.codejava.dto.UserLoginDto;
import net.codejava.dto.UserRegistrationDto;
import net.codejava.services.UserService;


@Controller
public class AppController {
	String token;
	@Autowired
	private UserService userService; 
	
	@RequestMapping({"/","/home"})
	public String home(Model model)
	{
		model.addAttribute("accessToken", token);
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public ModelAndView loginCheck(@ModelAttribute("user") UserLoginDto userDto) throws IOException {
		 String result= userService.loginCheck(userDto);
		//System.out.println("user details: "+userService.getUserInfoByUserName("harry"));
		 if (result=="unauthorized")
		 return new ModelAndView("redirect:" + "/login?error");
		 else {
			 ObjectMapper mapper = new ObjectMapper();
			 JsonNode actualObj = mapper.readTree(result);
			 String res = actualObj.get("access_token").toString();
			 System.out.println(actualObj.get("access_token"));
			 token=res.substring(1,res.length()-1);
			 //System.out.println(token);
			 return new ModelAndView("redirect:" + "/index?access_token="+token,"accessToken",token);
		 }
	}


	
	@GetMapping("/registration")
	public String registration( @ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult bindingResult,Model model) {
		model.addAttribute("accessToken",token);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registrationCheck( @ModelAttribute("user")  @Valid UserRegistrationDto userDto, BindingResult bindingResult) {
		String result= userService.registrationCheck(userDto);
		if(result=="invalid")
		{
			bindingResult.rejectValue("username", null, "There is already an account registered with that username");
		}
		if (bindingResult.hasErrors()) {
            return "registration";
        }
		UserInfo userinf=userService.save(userDto);
		return "redirect:/registration?success&access_token="+token;
	}
	

}

