package com.tfg.tms.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.tfg.tms.service.VisitorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class VisitorServiceTest {

	@Autowired
	private VisitorService visitorService;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testServiceIsNotNull() {
		assertNotNull(visitorService);
	}

	@Test
	public void testCheckIfUserExist() {
		assertTrue(visitorService.checkIfUserExist("a@a.a"));
	}

	@Test
	public void testCheckIfUserExistFalse() {
		assertFalse(visitorService.checkIfUserExist("fakeEmail"));
	}

}
