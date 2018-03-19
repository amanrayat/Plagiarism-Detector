package com.team208.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team208.domain.StudentEntity;
import com.team208.domain.StudentRepository;

@Controller
@RequestMapping(path="/team208") 
public class MainController {
	
	// This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
	@Autowired 
	private StudentRepository userRepository;
	
	 @RequestMapping("/")
	  @ResponseBody
	  public String index() {
	    return "Hello World!!!";
	  }


	 @GetMapping(path="/add") // Map ONLY GET Requests
		public @ResponseBody String addNewUser (@RequestParam String userId, @RequestParam String name, @RequestParam String userRole,
				@RequestParam String password, @RequestParam String email) {
			// @ResponseBody means the returned String is the response, not a view name
			// @RequestParam means it is a parameter from the GET or POST request

			StudentEntity n = new StudentEntity();
			n.setStudentId(userId);
			n.setName(name);
			n.setUserRole(userRole);
			n.setPassword(password);
			n.setEmail(email);
			userRepository.save(n);
			return "Saved";
		}
	 
	 @GetMapping(path="/all")
		public @ResponseBody Iterable<StudentEntity> getAllUsers() {
			// This returns a JSON or XML with the users
			return userRepository.findAll();
		}
}