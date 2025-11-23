package in.astik.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.astik.entity.UserEntity;
import in.astik.io.UserRequest;
import in.astik.io.UserResponse;
import in.astik.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImp implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationFacade authenticationFacade;
	
	
	@Override
	public UserResponse registerUser(UserRequest request) {
		log.info("Inside registerUser");
		if (userRepository.existsByEmail(request.getEmail())) {
	        throw new RuntimeException("Email already registered");
	    }
		UserEntity newUser=convertToEntity(request);
		newUser=userRepository.save(newUser);
		return convertToResponse(newUser);
	}

	private UserEntity convertToEntity(UserRequest request)
	{
		return UserEntity.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.name(request.getName())
				.build();
	}
	private UserResponse convertToResponse(UserEntity registeredUser)
	{
		return UserResponse.builder()
							.id(registeredUser.getId())
							.email(registeredUser.getEmail())
							.name(registeredUser.getName())
							.build();
	}

	@Override
	public String findByUserId() {
		log.info("Inside findByUserId");
		String loggedInUserEmail=authenticationFacade.getAuthentication().getName();
		UserEntity loggedInUser=userRepository.findByEmail(loggedInUserEmail).orElseThrow(()->new UsernameNotFoundException("User Name Not Found"));
		return loggedInUser.getId();
	}
}
