package com.usecase.power.service;

import java.util.List;

import com.usecase.power.model.Fraction;
import com.usecase.power.model.MeterReading;

public interface MeterService {

	Iterable<Fraction> getAllFranction();

	Iterable<MeterReading> getAllMeterReading();

	MeterReading findByMonth();

	void createFractions(List<Fraction> fractions);

	void createMeterReadings(List<MeterReading> meterReadings);

	void getConsumption(int month,int year,long connectionId);

}
