package com.lcwd.user.service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lcwd.user.service.entities.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
	
	@GetMapping("/users/{userId}")
	List<Rating> getRatingByUserId(@PathVariable String userId);
	
	@PostMapping("/rating")
	Rating createRating(Rating values);
	
	
	

}
