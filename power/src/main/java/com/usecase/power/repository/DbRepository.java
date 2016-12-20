package com.usecase.power.repository;

import java.util.List;

import com.usecase.power.model.Fraction;
import com.usecase.power.model.MeterReading;

public interface DbRepository {
	Iterable<Fraction> getAllFranction();

	Iterable<MeterReading> getAllMeterReading();

	MeterReading findByMonth();
	
	void createFractions(List<Fraction> fractions);
	
	void createMeterReadings(List<MeterReading> meterReadings);
}
