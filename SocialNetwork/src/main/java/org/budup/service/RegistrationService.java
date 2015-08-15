package org.budup.service;

import java.util.ArrayList;
import java.util.List;

import org.budup.common.CryptoUtil;
import org.budup.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RegistrationService 
{
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CryptoUtil cryptoUtil;
	
	public List<String> createUser(String registrationDetails)
	{
		List<String> statusList=new ArrayList<String>();
		JSONObject userJSON= new JSONObject(registrationDetails);
		ObjectMapper mapper = new ObjectMapper();
		Session session=sessionFactory.openSession();
		try
		{
			Users user = mapper.readValue(userJSON.toString(), Users.class);
			user.setPassword(cryptoUtil.encrypt(user.getUsername(), user.getPassword()));
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			statusList=loginService.authenticateUser(registrationDetails);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.flush();
			session.close();
		}
		return statusList;
	}
}
