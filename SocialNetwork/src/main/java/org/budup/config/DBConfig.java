package org.budup.config;

import java.util.Properties;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;


@Configuration
public class DBConfig 
{
	  @Value("${spring.datasource.driver-class-name}")
	  private String DB_DRIVER;
	  
	  @Value("${spring.datasource.password}")
	  private String DB_PASSWORD;
	  
	  @Value("${spring.datasource.url}")
	  private String DB_URL;
	  
	  @Value("${spring.datasource.username}")
	  private String DB_USERNAME;

	  @Value("${spring.jpa.properties.hibernate.dialect}")
	  private String HIBERNATE_DIALECT;
	  
	  @Value("${spring.jpa.show-sql}")
	  private String HIBERNATE_SHOW_SQL;
	  
	  @Value("${spring.jpa.properties.hibernate.dialect}")
	  private String HIBERNATE_HBM2DDL_AUTO;

	  @Bean
	  public DataSource dataSource() 
	  {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(DB_DRIVER);
	    dataSource.setUrl(DB_URL);
	    dataSource.setUsername(DB_USERNAME);
	    dataSource.setPassword(DB_PASSWORD);
	    return dataSource;
	  }
	  
	  @Bean
	  public LocalSessionFactoryBean sessionFactoryBean() 
	  {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan("org.budup.entity");
	    Properties hibernateProperties = new Properties();
	    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
	    hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
	    hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	    sessionFactoryBean.setHibernateProperties(hibernateProperties);
	    return sessionFactoryBean;
	  }
	  
	  @Bean
	  public SessionFactory sessionFactory()
	  {
		  SessionFactory sessionFactory = sessionFactoryBean().getObject();
		  return sessionFactory;
	  }
}
