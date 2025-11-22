package in.astik.service;


import in.astik.io.UserRequest;
import in.astik.io.UserResponse;


public interface UserService {
	UserResponse registerUser(UserRequest request);
	String findByUserId();
}
