package com.usecase.power.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.usecase.power.model.Fraction;

@RepositoryRestResource
public interface FractionRepository extends CrudRepository<Fraction, Long>{

}
