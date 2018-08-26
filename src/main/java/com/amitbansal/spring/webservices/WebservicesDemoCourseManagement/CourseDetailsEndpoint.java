package com.amitbansal.spring.webservices.WebservicesDemoCourseManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.amitbansal.courses.AllCourseDetailsRequest;
import com.amitbansal.courses.AllCourseDetailsResponse;
import com.amitbansal.courses.CourseDetails;
import com.amitbansal.courses.CourseDetailsRequest;
import com.amitbansal.courses.CourseDetailsResponse;
import com.amitbansal.courses.DeleteCourseDetailsRequest;
import com.amitbansal.courses.DeleteCourseDetailsResponse;
import com.amitbansal.spring.webservices.WebservicesDemoCourseManagement.soap.bean.Course;
import com.amitbansal.spring.webservices.WebservicesDemoCourseManagement.soap.service.CourseDetailsService;
import com.amitbansal.spring.webservices.WebservicesDemoCourseManagement.soap.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService courseDetailsService;
	//method
	// input - request CourseDetailsRequest
	// output - response CourseDetailsResponse
	@PayloadRoot(namespace="http://amitbansal.com/courses", localPart="CourseDetailsRequest")
	@ResponsePayload
	public CourseDetailsResponse processCourseDetailsRequest(@RequestPayload CourseDetailsRequest request){
		//CourseDetailsResponse response  = new CourseDetailsResponse();
		Course course = courseDetailsService.findById(request.getId());	
		if(course == null){
			throw new CourseNotFoundException("Invalid course id "+request.getId());
		}
		return mapCourseDetails(course);

	} 
	
	@PayloadRoot(namespace="http://amitbansal.com/courses", localPart="DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseRequest(@RequestPayload DeleteCourseDetailsRequest request){
		//CourseDetailsResponse response  = new CourseDetailsResponse();
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		Status status = courseDetailsService.deleteById(request.getId());	
		response.setStatus(mapStatus(status));
		return response;

	} 
	private com.amitbansal.courses.Status mapStatus(Status status) {
		// TODO Auto-generated method stub
		if(status == Status.FAILURE)
			return com.amitbansal.courses.Status.FAILURE;
		else
			return com.amitbansal.courses.Status.SUCCESS;
	}

	@PayloadRoot(namespace="http://amitbansal.com/courses", localPart="AllCourseDetailsRequest")
	@ResponsePayload
	public AllCourseDetailsResponse processCourseDetailsRequest(@RequestPayload AllCourseDetailsRequest request){
		//CourseDetailsResponse response  = new CourseDetailsResponse();
		List<Course> courses = courseDetailsService.findAll();		
		return mapAllCourseDetails(courses);
		
	} 
	
	
	private CourseDetailsResponse mapCourseDetails(Course course){
		CourseDetailsResponse response = new CourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}



	private AllCourseDetailsResponse mapAllCourseDetails(List<Course> courses){
		AllCourseDetailsResponse response = new AllCourseDetailsResponse();
		for(Course c: courses){
			CourseDetails mappedCourse = mapCourse(c);
			response.getCourseDetails().add(mappedCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course){
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());		
		return courseDetails;
	}
	
	
	
}
