package com.ahk.hotel.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahk.hotel.entities.Hotel;
import com.ahk.hotel.exception.ResourceNotFoundException;
import com.ahk.hotel.repositories.HotelRepositories;
import com.ahk.hotel.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService{
	
	@Autowired
	HotelRepositories hotelRepositories;

	@Override
	public Hotel saveHotel(Hotel hotel) {
	
		hotel.setHotelId(UUID.randomUUID().toString());
		return hotelRepositories.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotels() {
		// TODO Auto-generated method stub
		return hotelRepositories.findAll();
	}

	@Override
	public Hotel getHotel(String hotelId) {
		// TODO Auto-generated method stub
		return hotelRepositories.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel with given id is not found on server !! "+hotelId));
	}

}
