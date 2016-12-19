package com.usecase.power.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.usecase.power.model.MeterReading;
import com.usecase.power.repository.MeterReadingRepository;


@RestController
@RequestMapping("/meter")
public class MeterReadingController {

//	@RequestMapping(method = RequestMethod.GET)
//	String home() {
//		return "Hello World!";
//	}

	@Autowired
	private MeterReadingRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<MeterReading>> getAllReadings() {
		return new ResponseEntity<Collection<MeterReading>>((Collection<MeterReading>) repository.findAll(), HttpStatus.OK);
	}
}
