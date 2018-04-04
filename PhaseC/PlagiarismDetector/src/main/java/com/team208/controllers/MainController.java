package com.team208.controllers;



import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team208.domain.AssignmentEntity;
import com.team208.domain.AssignmentRepository;
import com.team208.domain.AssignmentSubmissionRepository;
import com.team208.domain.CourseEntity;
import com.team208.domain.CourseRepository;
import com.team208.domain.UserEntity;
import com.team208.domain.UserRepository;
import com.team208.jsonresponse.AllSubmissionResponse;
import com.team208.jsonresponse.LoginJsonBean;
import com.team208.jsonresponse.LoginResponse;
import com.team208.jsonresponse.StatusBean;
import com.team208.jsonresponse.UserJsonBean;
import com.team208.utilities.Constants;

@CrossOrigin
@Controller
@RequestMapping(path="/team208") 
public class MainController {

	/**
	 * Logger
	 */
	private static final Logger logger = 
			Logger.getLogger(MainController.class.getName());

	// This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private CourseRepository courseRepository;

	@Autowired 
	private AssignmentSubmissionRepository submissionRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Hello World!!!  Team208 Homepage";
	}


	@RequestMapping(path="/login")
	public @ResponseBody LoginResponse login(@RequestBody LoginJsonBean userDetails)  {
		LoginResponse response = null;
		StatusBean statusbean = new StatusBean();
		UserEntity n = null;
		try {
			n = userRepository.findByNEUId(userDetails.getUserId());
			if(n != null) {

				if(userDetails.getPassword().equals(n.getPassword())) {
					response = new LoginResponse();
					response.setUser(n);
					statusbean.setStatus(Constants.SUCCESS_STATUS);
					statusbean.setStatusCode(Constants.SUCCESS_STATUS_CODE);
					response.setStatus(statusbean);
				}
				else {
					response = new LoginResponse();
					response.setUser(n);
					statusbean.setStatus(Constants.INCORRECT_CREDENTIALS);
					statusbean.setStatusCode(Constants.INCORRECT_CREDENTIALS_CODE);
					response.setStatus(statusbean);
				}
			}else {
				response = new LoginResponse();
				response.setUser(n);
				statusbean.setStatus(Constants.UNREGISTERED_STATUS);
				statusbean.setStatusCode(Constants.UNREGISTERED_STATUS_CODE);
				response.setStatus(statusbean);
			}

		} catch (Exception e) {
			logger.info(Constants.CONTEXT+e.getMessage());
			response = new LoginResponse();
			response.setUser(n);
			statusbean.setStatus(Constants.FAILURE_EXCEPTION_STATUS);
			statusbean.setStatusCode(Constants.FAILURE_EXCEPTION_STATUS_CODE);
			response.setStatus(statusbean);

		}


		return response  ;

	}

	@RequestMapping(path="/registerUser", method = RequestMethod.POST  ) // Map ONLY GET Requests
	public @ResponseBody StatusBean addNewUser (@RequestBody UserJsonBean user) {

		StatusBean status = new StatusBean();
		try {
			UserEntity n = new UserEntity();

			n.setUserId(user.getUserId());
			n.setName(user.getName());
			n.setUserRole(user.getUserRole());
			n.setPassword(user.getPassword());
			n.setEmail(user.getEmail());

			userRepository.save(n);

			status.setStatus(Constants.SUCCESS_STATUS);
			status.setStatusCode(Constants.SUCCESS_STATUS_CODE);

		}catch (Exception e) {

			logger.info(Constants.CONTEXT+e.getMessage());
			status.setStatus(Constants.FAILURE_EXCEPTION_STATUS);
			status.setStatusCode(Constants.FAILURE_EXCEPTION_STATUS_CODE);
		}

		return status;
	}



	@GetMapping(path="/findStudent")
	public @ResponseBody LoginResponse findStudent(@RequestParam Long userId ) {
		LoginResponse response = null;
		StatusBean status = new StatusBean();
		UserEntity n = null;
		try {
		n = userRepository.findByNEUId(userId);
		if(n != null) {

			response = new LoginResponse();
			response.setUser(n);
			status.setStatus(Constants.SUCCESS_STATUS);
			status.setStatusCode(Constants.SUCCESS_STATUS_CODE);
			response.setStatus(status);
			
		}else {
			response = new LoginResponse();
			response.setUser(n);
			status.setStatus(Constants.UNREGISTERED_STATUS);
			status.setStatusCode(Constants.UNREGISTERED_STATUS_CODE);
			response.setStatus(status);
		}
		}catch (Exception e) {
			logger.info(Constants.CONTEXT+e.getMessage());
			response = new LoginResponse();
			response.setUser(n);
			status.setStatus(Constants.FAILURE_EXCEPTION_STATUS);
			status.setStatusCode(Constants.FAILURE_EXCEPTION_STATUS_CODE);
			response.setStatus(status);

		}

		return response  ;

	}
	
	
	@GetMapping(path="/allSubmissionsByCourse")
	public @ResponseBody AllSubmissionResponse allSubmissionsByCourse(@RequestParam String courseAbbr,@RequestParam int assignmentNo ) {
		AllSubmissionResponse subs = new AllSubmissionResponse();
		StatusBean status = new StatusBean();
		try {
				CourseEntity course = courseRepository.findByAbbr(courseAbbr);
				
				if(courseRepository.existsById(course.getCourseId())) {
				
				int courseId = course.getCourseId();
				
				AssignmentEntity assignment = assignmentRepository.findByNoAndCourse(assignmentNo, courseId );
				
				if(assignmentRepository.existsById(assignment.getAssignmentId()) ) {
					
					
					
					int assignmentId = assignment.getAssignmentId();
					subs.setSubmissions(submissionRepository.findSubmissionByAssignmentId(assignmentId));
					status.setStatus(Constants.SUCCESS_STATUS);
					status.setStatusCode(Constants.SUCCESS_STATUS_CODE);
					subs.setStatus(status);
					
				}else {
					status.setStatus(Constants.UNAVAILABLE_ASSIGNMENT);
					status.setStatusCode(Constants.UNAVAILABLE_ASSIGNMENT_CODE);
					subs.setStatus(status);
				}
				}else {
					status.setStatus(Constants.UNAVAILABLE_COURSE);
					status.setStatusCode(Constants.UNAVAILABLE_COURSE_CODE);
					subs.setStatus(status);
			
				}
				
				
			
		}catch (Exception e) {
			logger.info(Constants.CONTEXT+e.getMessage());
			status.setStatus(Constants.FAILURE_EXCEPTION_STATUS);
			status.setStatusCode(Constants.FAILURE_EXCEPTION_STATUS_CODE);
			subs.setStatus(status);
		}
		
		return subs;
		
	}
}
