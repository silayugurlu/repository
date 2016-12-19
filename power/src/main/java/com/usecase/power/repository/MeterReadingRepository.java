package com.usecase.power.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.usecase.power.model.MeterReading;

@RepositoryRestResource
public interface MeterReadingRepository extends CrudRepository<MeterReading, String>{



}
