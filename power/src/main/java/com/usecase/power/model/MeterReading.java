package com.usecase.power.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeterReading {
		 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long connectionId;
    private Profile profile;
    private Integer value;

    
    
    public MeterReading() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MeterReading(Long id, Long connectionId, Profile profile, Integer value) {
        this.id = id;
		this.connectionId = connectionId;
        this.profile = profile;   
        this.value = value;        
    }

      
    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

   
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}
