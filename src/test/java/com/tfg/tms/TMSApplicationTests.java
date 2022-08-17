package com.tfg.tms;

// @formatter:off
import static org.assertj.core.api.Assertions.assertThat;
// @formatter:on

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tfg.tms.controller.AdminController;
import com.tfg.tms.controller.VisitorController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TMSApplicationTests {

	@Autowired
	private VisitorController visitorController;
	@Autowired
	private AdminController adminController;

	@Test
	public void contextLoadsEmpty() {
	}

	@Test
	public void contextLoadsVisitor() throws Exception {
		assertThat(visitorController).isNotNull();
	}

	@Test
	public void contextLoadsAdmin() throws Exception {
		assertThat(adminController).isNotNull();
	}

}
