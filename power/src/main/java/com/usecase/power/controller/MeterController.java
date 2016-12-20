package com.usecase.power.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usecase.power.model.Fraction;
import com.usecase.power.model.MeterReading;
import com.usecase.power.repository.DbRepository;


@RestController
@RequestMapping("/meter")
public class MeterController {

//	@RequestMapping(method = RequestMethod.GET)
//	String home() {
//		return "Hello World!";
//	}

	@Autowired
	private DbRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<MeterReading>> getAllReadings() {
		return new ResponseEntity<Collection<MeterReading>>((Collection<MeterReading>) repository.getAllMeterReading(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Fraction>> getAllFraction() {
		return new ResponseEntity<Collection<Fraction>>((Collection<Fraction>) repository.getAllFranction(), HttpStatus.OK);
	}
	
	
	//importfraction
	//impoert meter reading
	//getConsumption
	
}
