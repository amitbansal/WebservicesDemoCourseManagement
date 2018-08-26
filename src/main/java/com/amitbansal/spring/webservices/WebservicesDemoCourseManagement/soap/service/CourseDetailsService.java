package com.amitbansal.spring.webservices.WebservicesDemoCourseManagement.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amitbansal.spring.webservices.WebservicesDemoCourseManagement.soap.bean.Course;

@Component
public class CourseDetailsService {

	public enum Status {
		SUCCESS, FAILURE;
	}
	private static List<Course> courses  = new ArrayList<>();
	static {
		Course course1 = new Course(1, "Spring 1", " 10 steps");
		courses.add(course1);
		Course course2 = new Course(2, "Spring MVC", " 10 xamples");
		courses.add(course2);
		Course course3 = new Course(3, "Spring Boot", "6k students");
		courses.add(course3);
		Course course4 = new Course(4, "Spring Maven", "10 steps maven");
		courses.add(course4);
		
	}
	
	public Course findById(Integer id){
		return courses.stream().filter(c-> id.equals(c.getId()))
		.findFirst()
		.orElse(null);		
	}

	

	public List<Course> findAll(){
		return courses;
	}
	
	public Status deleteById(int id){
		Iterator<Course> iterator = courses.iterator();
		while(iterator.hasNext()){
			Course course = iterator.next();
			if(course.getId() == id){
				iterator.remove();
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;

	}
}

