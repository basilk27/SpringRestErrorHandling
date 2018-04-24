package com.mbsystem.rest.error.handling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandlingApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void stringFormaterTest() {
		String output = String.format("Basil looking at this %s", "joe");
		long zlong = 5000;

		System.out.println( zlong  );
	}
}
