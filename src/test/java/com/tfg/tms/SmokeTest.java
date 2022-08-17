package com.tfg.tms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tfg.tms.controller.VisitorController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private VisitorController visitorController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(visitorController).isNotNull();
	}
}