package com.ahk.user.service.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ahk.user.service.entities.Hotel;
import com.ahk.user.service.entities.Rating;
import com.ahk.user.service.entities.User;
import com.ahk.user.service.exception.ResourceNotFoundException;
import com.ahk.user.service.external.services.HotelService;
import com.ahk.user.service.repository.UserRepository;
import com.ahk.user.service.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	HotelService hotelService;
	
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !! "+userId));
		
		// fetch ratings
		
		//ArrayList<Rating> ratingForUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId, ArrayList.class);
		//Rating[] ratingForUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId, Rating[].class);
		Rating[] ratingForUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);

		List<Rating> ratings =  Arrays.stream(ratingForUser).toList();
		
		logger.info("{}", ratings);
		
		
		List<Rating> ratingList = ratings.stream().map(rating->{
			// Call Hotel Service
			//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);
			//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			
			//logger.info("response status code {}", forEntity.getStatusCode());
			rating.setHotel(hotel);
			return rating;
			
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		
		return user;
	}

}
