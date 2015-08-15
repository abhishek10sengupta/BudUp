package org.budup.service;

import java.util.ArrayList;
import java.util.List;
import org.budup.common.CommonHelper;
import org.budup.common.CryptoUtil;
import org.budup.dao.BudUpDao;
import org.budup.entity.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService 
{
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	BudUpDao budUpDao;
	
	@Autowired
	CryptoUtil cryptoUtil;
	
	@Autowired
	CommonHelper commonHelper;
	
	public List<String> authenticateUser(String loginDetails)
	{
		List<String> status=new ArrayList<String>();
		String finalStatus="Wrong Username and Password Combination";
		JSONObject loginObj=new JSONObject(loginDetails);
		String username=loginObj.getString("username");
		String password=loginObj.getString("password");
		Session session=sessionFactory.openSession();
		String encPwd="";
		try
		{
			session.beginTransaction();
			Query query=session.createQuery(budUpDao.GET_USERS_BY_USERNAME);
			query.setString("username", username);
			ArrayList<Users> listOfUser=(ArrayList<Users>)query.list();
			if(!listOfUser.isEmpty())
			{
				for(Users user:listOfUser)
				{
					encPwd=cryptoUtil.decrypt(username, user.getPassword());
					if(password.equals(encPwd))
					{
						finalStatus=commonHelper.cacheUser(user);
						System.out.println(finalStatus);
					}
				}
			}
			session.getTransaction().commit();
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
		status.add(finalStatus);
		return status;
	}
}
