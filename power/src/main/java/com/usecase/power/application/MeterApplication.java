package com.usecase.power.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.usecase.power.model.MeterReading;
import com.usecase.power.repository.MeterReadingRepository;

@SpringBootApplication
@EnableJpaRepositories("com.usecase.power.repository")
@EntityScan("com.usecase.power.model")
@ComponentScan("com.usecase.power.controller")
public class MeterApplication {

	public static void main(String[] args) {
		 SpringApplication.run(MeterApplication.class, args);

	}
	
	@Bean
    public CommandLineRunner initializeDb(MeterReadingRepository repository){
        return (args) -> {
            repository.deleteAll();
            //Insert some random pies
            for(int i = 0; i < 10; i++) {
//                repository.save(new MeterReading(i+1L,1L,"a",2016,3,2));
            }
        };
    }

}
