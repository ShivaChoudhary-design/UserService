package com.lcwd.user.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.external.services.RatingService;
import com.lcwd.user.service.repositroy.UserRepository;
import com.lcwd.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private RatingService ratingService;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given Id is not present on server!"));
		// it will not return ratings
		// fetch rating of the above user from Rating service
		/*
		 * ArrayList<Rating> ratingsOfUser =
		 * restTemplate.getForObject("http://localhhost:8003/ratings/users/"+user.
		 * getUserId(), ArrayList.class); user.setRatings(ratingsOfUser);
		 * 
		 * ratingsOfUser.stream().map(rating->{ //api call to hotel service to get the
		 * hotel //set the hotel to rating //return the rating
		 * 
		 * 
		 * 
		 * });
		 */
		List<Rating> ratings = ratingService.getRatingByUserId(user.getUserId());

		for (Rating rating : ratings) {
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
		}
		user.setRatings(ratings);

		return user;

	}

}
