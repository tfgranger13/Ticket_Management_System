package com.tfg.tms.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.tfg.tms.dao.EmployeeDAO;
import com.tfg.tms.entity.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class EmployeeDAOTest {

	@Autowired
	private EmployeeDAO employeeDAO;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testRepositoryIsNotNull() {
		assertNotNull(employeeDAO);
	}

	@Test
	public void testGetEmployees() {
		Employee newEmp = new Employee();
		newEmp.setEmail("TesterEmail");
		newEmp.setFirstName("TesterFirst");
		newEmp.setLastName("TesterLast");
		newEmp.setPassword("TesterPass");
		employeeDAO.saveEmployee(newEmp);
		List<Employee> allEmployees = employeeDAO.getEmployees();
		assertEquals(allEmployees.contains(newEmp), true);
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6 })
	public void testGetEmployees(int id) {
		List<Employee> allEmployees = employeeDAO.getEmployees();
		Employee testEmp = employeeDAO.getEmployee(id);
		assertTrue(allEmployees.contains(testEmp));
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 20, 40, 199, 384 })
	public void testGetEmployeesNotInDb(int id) {
		List<Employee> allEmployees = employeeDAO.getEmployees();
		Employee testEmp = employeeDAO.getEmployee(id);
		assertFalse(allEmployees.contains(testEmp));
	}

	@Test
	public void testGetEmployee() {
		Employee newEmp = new Employee();
		newEmp.setId(200);
		newEmp.setEmail("TesterEmail");
		newEmp.setFirstName("TesterFirst");
		newEmp.setLastName("TesterLast");
		newEmp.setPassword("TesterPass");
		employeeDAO.saveEmployee(newEmp);
		Employee testEmp = employeeDAO.getEmployee(200);
		assertEquals(newEmp.getFirstName(), testEmp.getFirstName());
	}

	@Test
	public void testGetEmployeeByEmail() {
		Employee newEmp = new Employee();
		newEmp.setEmail("TesterEmail");
		newEmp.setFirstName("TesterFirst");
		newEmp.setLastName("TesterLast");
		newEmp.setPassword("TesterPass");
		employeeDAO.saveEmployee(newEmp);
		Employee testEmp = employeeDAO.getEmployeeByEmail("TesterEmail");
		assertEquals(newEmp.getFirstName(), testEmp.getFirstName());
	}

	@Test
	public void testSaveEmployee() {
		Employee newEmp = new Employee();
		newEmp.setEmail("TesterEmail");
		newEmp.setFirstName("TesterFirst");
		newEmp.setLastName("TesterLast");
		newEmp.setPassword("TesterPass");
		employeeDAO.saveEmployee(newEmp);
		List<Employee> allEmployees = employeeDAO.getEmployees();
		assertTrue(allEmployees.contains(newEmp));
	}

	@Test
	public void testUpdateEmployee() {
		Employee newEmp = new Employee();
		newEmp.setId(200);
		newEmp.setEmail("TesterEmail");
		newEmp.setFirstName("TesterFirst");
		newEmp.setLastName("TesterLast");
		newEmp.setPassword("TesterPass");
		employeeDAO.saveEmployee(newEmp);
		Employee middleEmp = employeeDAO.getEmployee(200);
		middleEmp.setFirstName("Updated");
		employeeDAO.saveEmployee(middleEmp);
		Employee testEmp = employeeDAO.getEmployee(200);
		assertEquals("Updated", testEmp.getFirstName());
	}

	@Test
	public void testDeleteEmployee() {
		Employee newEmp = new Employee();
		newEmp.setEmail("TesterEmail");
		newEmp.setFirstName("TesterFirst");
		newEmp.setLastName("TesterLast");
		newEmp.setPassword("TesterPass");
		employeeDAO.saveEmployee(newEmp);
		Employee testEmp = employeeDAO.getEmployeeByEmail("TesterEmail");
		employeeDAO.deleteEmployee(testEmp.getId());
		List<Employee> allEmployees = employeeDAO.getEmployees();
		assertFalse(allEmployees.contains(testEmp));
	}

}
