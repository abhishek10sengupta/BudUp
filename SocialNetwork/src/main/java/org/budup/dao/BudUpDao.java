package org.budup.dao;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BudUpDao 
{
	public final String GET_USERS_BY_USERNAME="from org.budup.entity.Users u where u.username=:username";
}
