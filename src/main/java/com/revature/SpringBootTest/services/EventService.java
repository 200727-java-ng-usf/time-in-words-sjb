package com.revature.SpringBootTest.services;

import com.revature.SpringBootTest.daos.EventDao;
import com.revature.SpringBootTest.dtos.EventDto;
import com.revature.SpringBootTest.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventService {

	@Autowired
	private EventDao eventDao;

	private static String[] nums = {"one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen","twenty","twenty one","twenty two","twenty three","twenty four","twenty five","twenty six","twenty seven","twenty eight","twenty nine","thirty"};

	public String timeInWords(int h, int m) {
		if(m == 0){
			return (nums[h-1] + " o' clock");
		} else if (m == 1){
			return (nums[m-1] + " minute past " + nums[h-1]);
		} else if (m < 30 && m != 15){
			return (nums[m-1] + " minutes past " + nums[h-1]);
		} else if (m == 15){
			return ("quarter past " + nums[h-1]);
		} else if (m == 30){
			return ("half past " + nums[h-1]);
		} else if (m != 45){
			return (nums[(30 - (m-30))-1] + " minutes to " + nums[h]);
		} else{
			return ("quarter to " + nums[h]);
		}
	}

	public String timeInWords(LocalDateTime e){
		return timeInWords(
				e.getHour(),
				e.getMinute());
	}

	@Transactional(readOnly = true)
	public List<EventDto> findAll(){
		return StreamSupport.stream(eventDao.findAll().spliterator(), false)
				.map(e -> new EventDto(
						e.getName(),
						e.getLocation(),
						timeInWords(e.getDate())))
				.collect(Collectors.toList());
	}

	@Transactional
	public EventDto save(Event event) {
		eventDao.save(event);
		return new EventDto(
				event.getName(),
				event.getLocation(),
				(""
						+ event.getDate().getYear()
						+ "-"
						+ event.getDate().getMonthValue()
						+ "-"
						+ event.getDate().getDayOfMonth()
						+ " "
						+ timeInWords(event.getDate())));
	}
}
