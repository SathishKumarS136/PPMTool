package com.sathish.reactapplication.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sathish.reactapplication.domain.User;
import com.sathish.reactapplication.payload.JWTLoginSuccessResponse;
import com.sathish.reactapplication.payload.LoginRequest;
import com.sathish.reactapplication.security.JwtTokenProvider;
import com.sathish.reactapplication.security.SecurityConstants;
import com.sathish.reactapplication.service.MapValidationErrorService;
import com.sathish.reactapplication.service.UserService;
import com.sathish.reactapplication.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationErrorService errorService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = errorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		User saveUser = userService.saveUser(user);
		return new ResponseEntity<User>(saveUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = errorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(token, true));

	}

}
