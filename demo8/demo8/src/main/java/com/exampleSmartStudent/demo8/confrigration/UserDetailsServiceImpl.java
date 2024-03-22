package com.exampleSmartStudent.demo8.confrigration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.exampleSmartStudent.demo8.Dao.UserRepository;
import com.exampleSmartStudent.demo8.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Configuration
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetching user from database
		//User user = userRepository.getUserByUserName(username);
		Optional <User>user = userRepository.getUserByUserName(username);
		if(user.isPresent()) {
			//System.out.println("find user ");
			var userObj = user.get();
			return org.springframework.security.core.userdetails.User
					.builder()
					.username(userObj.getEmail())
					.password(userObj.getPassword())
					.roles(getRoles(userObj))
					.build();
		}
		else {
			throw new UsernameNotFoundException(username);
		}



//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//		System.out.println("find user ");
//	CustomUserDetails customUserDetails = new CustomUserDetails(user);
//		return customUserDetails;
	}
	private String[] getRoles(User user) {
		if(user.getRole()==null) return new String[]{"USER"};

		return user.getRole().split(",");
	}


}
