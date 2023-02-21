package com.jun.app.modules.event.endpoint.form;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.jun.app.modules.event.domain.entity.EventType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


public class EventForm {
	 @NotBlank
	    @Length(max = 50)
	    private String title;

	    private String description;

	    private EventType eventType = EventType.FCFS;

	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	    private LocalDateTime endEnrollmentDateTime;

	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	    private LocalDateTime startDateTime;

	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	    private LocalDateTime endDateTime;

	    @Min(2)
	    private Integer limitOfEnrollments = 2;

	}
	

