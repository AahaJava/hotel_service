package com.ahk.user.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahk.user.service.entities.User;
import com.ahk.user.service.service.UserService;
import com.ahk.user.service.service.impl.UserServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	int retryCount=1;
	@GetMapping("/{usersId}")
	//@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	//@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback" )
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback" )
	public ResponseEntity<User> getUser(@PathVariable(name = "usersId") String userId){
		logger.info("retry count: {}", retryCount);
		retryCount++;
		
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
		
		logger.info("Fallback is executed because service is down : ", ex.getMessage());

		User user = User.builder()
				.name("dummy")
				.email("dummy@gmail.com")
				.userId("123456789")
				.about("This user is created dummy because service is down.")
				.build();
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

}
