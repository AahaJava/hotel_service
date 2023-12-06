package com.ahk.hotel.service;

import java.util.List;

import com.ahk.hotel.entities.Hotel;

public interface HotelService {

	Hotel saveHotel(Hotel hotel);
	List<Hotel> getAllHotels();
	Hotel getHotel(String hotelId);
}
