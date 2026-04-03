package in.astik.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.astik.entity.UserEntity;
import in.astik.exception.DuplicateResourceException;
import in.astik.exception.ResourceNotFoundException;
import in.astik.io.UserRequest;
import in.astik.io.UserResponse;
import in.astik.mapper.UserMapper;
import in.astik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationFacade authenticationFacade;
	private final UserMapper userMapper;
	
	@Override
	public UserResponse registerUser(UserRequest request) {
		log.info("Inside registerUser");
		if (userRepository.existsByEmail(request.getEmail())) {
	        throw new DuplicateResourceException("Email already registered");
	    }
		UserEntity newUser = userMapper.toEntity(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
		newUser = userRepository.save(newUser);
		return userMapper.toResponse(newUser);
	}

	@Override
	public String findByUserId() {
		log.info("Inside findByUserId");
		String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
		UserEntity loggedInUser = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User Name Not Found"));
		return loggedInUser.getId();
	}
}
