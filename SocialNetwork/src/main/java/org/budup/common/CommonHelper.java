package org.budup.common;
import java.util.UUID;
import org.budup.entity.Users;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class CommonHelper 
{
	@Value("${spring.redis.host}")
	public String redisHost;
	
	public String cacheUser(Users user)
	{
		String usertoken="";
		UUID token=UUID.randomUUID();
		usertoken=token.toString();
		JSONObject userJson= new JSONObject(user);
		Jedis jedis=new Jedis(redisHost);
		jedis.set(usertoken,userJson.toString());
		jedis.close();
		return usertoken;
	}
}
