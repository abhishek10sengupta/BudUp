package org.budup.controller;

import java.util.ArrayList;
import java.util.List;

import org.budup.service.LoginService;
import org.budup.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/")
public class ServiceController 
{
	@Autowired
	LoginService loginService;
	
	@Autowired
	RegistrationService registrationService;
	
	@RequestMapping(value="login", method=RequestMethod.POST, headers="accept=application/JSON")
	public List<String> login(@RequestBody String loginDetails)
	{
		List<String> status=new ArrayList<String>();
		status=loginService.authenticateUser(loginDetails);
		return status;
	}
	
	@RequestMapping(value="register" , method=RequestMethod.POST, headers="accept=application/JSON")
	public List<String> register(@RequestBody String registrationDetails)
	{
		List<String> status=new ArrayList<String>();
		status=registrationService.createUser(registrationDetails);
		return status;
	}
}
