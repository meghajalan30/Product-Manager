package net.codejava.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.codejava.model.UserInfo;
import net.codejava.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	  //@Autowired
		//private UserRepository repo;
	  
	@Autowired
	private UserService userInfoDAO;
	
	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  /*
	    User user = findUserbyUsername(username);
	    UserBuilder builder = null;
	    if (user != null) {
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
	      
	      builder.roles(user.getRole());
	    } else {
	      throw new UsernameNotFoundException("User not found.");
	    }

	    return builder.build();
	  }

	  private User findUserbyUsername(String username) {
		  for(User user:repo.findAll()) {
				if((username.equals(user.getUsername())) )
				{
					return user;
				}
			}
		  return null;
		  */
		  Optional<UserInfo> userInfo = userInfoDAO.getUserInfoByUserName(username);
		  System.out.println("result: "+userInfo.toString());
			GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.get().getRole());
			return new User(userInfo.get().getUsername(), userInfo.get().getPassword(), Arrays.asList(authority));
	}
}