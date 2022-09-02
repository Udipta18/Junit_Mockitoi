package com.example.junit.integration;

import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractionBaseTest {
	
	static final MySQLContainer MY_SQL_CONTAINER;
	
	static {
		MY_SQL_CONTAINER=new MySQLContainer("mysql:latest");
		
		MY_SQL_CONTAINER.start();
		
	}

}
