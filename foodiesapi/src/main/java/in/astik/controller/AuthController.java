package in.astik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.astik.io.AuthenticationRequest;
import in.astik.io.AuthenticationResponse;
import in.astik.service.AppUserDetailService;
import in.astik.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final AppUserDetailService userDetailsService;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse>login(@RequestBody AuthenticationRequest request)
	{
		log.info("inside login");
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		final UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
		final String jwtToken=jwtUtil.genrateToken(userDetails);
		AuthenticationResponse response=new AuthenticationResponse(request.getEmail(), jwtToken);

		return new ResponseEntity<AuthenticationResponse>(response,HttpStatus.OK);
	}

}
