package com.user.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.external.HotelService;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private HotelService hotelService;

	private ResponseEntity<Hotel> forEntity;

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(int userId) {
		User user = userRepository.findById(userId).orElseThrow(null);
// integrating rating service in user service
		Rating[] ratings = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(),
				Rating[].class);
		List<Rating> ratinglist = Arrays.stream(ratings).toList();

//	integrating hotel service in user service 
		ratinglist = ratinglist.stream().map(rating -> {
//			by using restTemplate
			
//			ResponseEntity<Hotel> forEntity = restTemplate
//					.getForEntity("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//			Hotel hotel = forEntity.getBody();
			
			// by using feignClient 
			Hotel hotel = hotelService.getHotel(Integer.parseInt(rating.getHotelId()));
			
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratinglist);
		return user;
	}
}
