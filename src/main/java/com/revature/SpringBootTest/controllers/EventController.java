package com.revature.SpringBootTest.controllers;

import com.revature.SpringBootTest.dtos.EventDto;
import com.revature.SpringBootTest.models.Event;
import com.revature.SpringBootTest.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/events")
public class EventController {
	private EventService eventService;

	@Autowired
	public EventController(EventService eventService){this.eventService = eventService;}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EventDto> getEvents(){
		return eventService.findAll();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EventDto createEvent(@RequestBody Event event){
		if (event == null){
			throw new ResourceNotFoundException("resource cannot be null!");
		}
		return eventService.save(event);
	}

	@GetMapping(value = "/format-time/{time}")
	public String formatTime(@PathVariable String time){
		LocalTime temp = LocalTime.parse(time);
		return eventService.timeInWords(temp.getHour(),temp.getMinute());
	}
}
