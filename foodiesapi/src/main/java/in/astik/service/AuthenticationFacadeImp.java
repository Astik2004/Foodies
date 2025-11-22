package in.astik.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationFacadeImp implements AuthenticationFacade{

	@Override
	public Authentication getAuthentication() {
		log.info("Inside AuthenticationFacadeImp");
		
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
       