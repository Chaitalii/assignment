package com.hellofresh.challenge;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utilities.Utils;

public class DataProviderCheck {

	Utils util = new Utils();
	
	
	
	
	
	@Test
	public void signInTest(Method method) throws Exception {
		System.out.println("Executing test");
		
		
		
	}

}
