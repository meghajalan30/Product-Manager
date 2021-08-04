package net.codejava.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.codejava.dto.UserLoginDto;
import net.codejava.dto.UserRegistrationDto;
import net.codejava.model.UserInfo;
import net.codejava.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;

	public UserRepository getRepo() {
		return repo;
	}

	public void setRepo(UserRepository repo) {
		this.repo = repo;
	}

	public String loginCheck(UserLoginDto userDto){
		
		try {
            URL url = new URL ("http://localhost:8889/oauth/token?grant_type=password&username="+userDto.getUsername()+"&password="+userDto.getPassword());
            String encoding = Base64.getEncoder().encodeToString(("clientId"+":"+"secret").getBytes("UTF-8"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in=
                    new BufferedReader(new InputStreamReader(content));
            String line;
            
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                return line;
            }
           
        } catch(Exception e) {
            e.printStackTrace();
        }
		 return "unauthorized";
				//return "redirect:/login?error";
	}
	
	
	/*public String tokenVerification(String token) {
		int f=0;
		try {
			
            URL url = new URL ("http://localhost:8889/index");
            //String encoding = Base64.getEncoder().encodeToString(token.toString().getBytes("UTF-8"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Bearer " + token);
            InputStream content = connection.getInputStream();
            BufferedReader in =
                    new BufferedReader (new InputStreamReader (content));
            String line;
            
            while ((line = in.readLine()) != null) {
            	f=1;
                System.out.println(line);
               // return line;
            }
            System.out.println(f);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
		 //return "unauthorized";
		if(f==1)
        {
        	return "redirect:/index ";
        }
        else
        	return "redirect:/";
		
	}
*/
	
	public String registrationCheck(UserRegistrationDto userDto) {
		//Iterable<UserInfo> userInfo=  repo.findAll();
		//for(UserInfo users:userInfo){
		for(UserInfo user:repo.findAll()) {
			if((userDto.getUsername().equals(user.getUsername())))
			{
				return "invalid";
			}
		}

		return "valid";
		//return "invalid";
		
	}
	
	public UserInfo save(UserRegistrationDto userDto) {
		String password=new BCryptPasswordEncoder().encode(userDto.getPassword());
		UserInfo newUser=new UserInfo(userDto.getUsername(),password,userDto.getRole());
		return repo.save(newUser);
	}

	public Optional<UserInfo> getUserInfoByUserName(String username) {

		Optional<UserInfo> user=repo.findById(username);
		System.out.println(user);
		return user;
	}



	
}

