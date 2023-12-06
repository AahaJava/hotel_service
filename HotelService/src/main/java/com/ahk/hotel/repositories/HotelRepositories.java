package com.ahk.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahk.hotel.entities.Hotel;

public interface HotelRepositories extends JpaRepository<Hotel, String>{

}
