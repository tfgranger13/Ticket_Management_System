package com.tfg.tms.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Ticket;
import com.tfg.tms.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testServiceIsNotNull() {
		assertNotNull(adminService);
	}

	@Test
	public void testgetEmployeeWithFewestTicketsFromDept() {
		Employee newEmployee = new Employee();
		newEmployee.setEmail("TesterEmail");
		newEmployee.setFirstName("Test");
		newEmployee.setLastName("Test");
		newEmployee.setPassword("Test");
		newEmployee.setTickets(new HashSet<Ticket>());
		newEmployee.setDepartment(adminService.getDepartment(3));
		adminService.saveEmployee(newEmployee);
		assertEquals(newEmployee, adminService.getEmployeeWithFewestTicketsFromDept(3));
	}

}
