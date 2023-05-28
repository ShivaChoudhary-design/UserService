 package com.lcwd.user.service.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createrUser(@RequestBody User user){
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
		
	}
	
	@GetMapping("/{id}")
	//@CircuitBreaker(name ="ratingHotelBreaker",fallbackMethod ="ratingHotelFallback")
	@Retry(name = "ratingHotelService",fallbackMethod ="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable("id") String userId){
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	//creating fallback method for circuit breaker
	
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		User user = User.builder()
		      .email("dummy@gmail.com")
		      .name("dummy")
		      .about("This user is created dummy beacuse some service is down")
		      .userId("1423")
		      .build();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User>allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}

}
