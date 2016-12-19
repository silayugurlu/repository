package com.usecase.power.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.usecase.power.model.MeterReading;

public interface MeterReadingRepository extends CrudRepository<MeterReading, String>{



}
