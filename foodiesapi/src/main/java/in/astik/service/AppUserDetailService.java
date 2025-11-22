package in.astik.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.astik.entity.UserEntity;
import in.astik.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@AllArgsConstructor
@Slf4j
public class AppUserDetailService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("Inside LoadbyUserName Method");
		UserEntity user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found!"));
		return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
	}

}
