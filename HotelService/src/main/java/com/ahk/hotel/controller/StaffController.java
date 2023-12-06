package com.ahk.hotel.controller;

import java.util.Arrays;
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

import com.ahk.hotel.entities.Hotel;
import com.ahk.hotel.service.HotelService;

@RestController
@RequestMapping("/staffs")
public class StaffController {

	
	@GetMapping
	public ResponseEntity<List<String>> getHotels(){
	
		List<String> staffs = Arrays.asList("Aman", "Lateef");
		return  new ResponseEntity<List<String>>(staffs, HttpStatus.OK);
	}
	

}
