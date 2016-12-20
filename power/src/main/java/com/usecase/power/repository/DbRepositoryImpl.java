package com.usecase.power.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.usecase.power.model.Fraction;
import com.usecase.power.model.MeterReading;

public class DbRepositoryImpl implements DbRepository {

	@Autowired
	FractionRepository fractionRepository;

	@Autowired
	MeterReadingRepository meterReadingRepository;

	@Override
	public Iterable<Fraction> getAllFranction() {
		return fractionRepository.findAll();
	}

	@Override
	public Iterable<MeterReading> getAllMeterReading() {
		return meterReadingRepository.findAll();
	}

	@Override
	public MeterReading findByMonth() {
		return meterReadingRepository.findByMonth();
	}

	@Override
	public void createFractions(List<Fraction> fractions) {
		fractionRepository.save(fractions);
	}

	@Override
	public void createMeterReadings(List<MeterReading> meterReadings) {
		meterReadingRepository.save(meterReadings);

	}

}
