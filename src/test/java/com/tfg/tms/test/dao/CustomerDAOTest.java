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

import com.tfg.tms.dao.CustomerDAO;
import com.tfg.tms.entity.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class CustomerDAOTest {

	@Autowired
	private CustomerDAO customerDAO;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testRepositoryIsNotNull() {
		assertNotNull(customerDAO);
	}

	@Test
	public void testGetCustomers() {
		Customer newCust = new Customer();
		newCust.setEmail("TesterEmail");
		newCust.setFirstName("TesterFirst");
		newCust.setLastName("TesterLast");
		newCust.setPassword("TesterPass");
		customerDAO.saveCustomer(newCust);
		List<Customer> allCustomers = customerDAO.getCustomers();
		assertEquals(allCustomers.contains(newCust), true);
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 3, 4, 5, 6, 15, 16, 17 })
	public void testGetCustomers(int id) {
		List<Customer> allCustomers = customerDAO.getCustomers();
		Customer testCust = customerDAO.getCustomer(id);
		assertTrue(allCustomers.contains(testCust));
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 30, 40, 90, 34732 })
	public void testGetCustomersNotInDb(int id) {
		List<Customer> allCustomers = customerDAO.getCustomers();
		Customer testCust = customerDAO.getCustomer(id);
		assertFalse(allCustomers.contains(testCust));
	}

	@Test
	public void testGetCustomer() {
		Customer newCust = new Customer();
		newCust.setId(200);
		newCust.setEmail("TesterEmail");
		newCust.setFirstName("TesterFirst");
		newCust.setLastName("TesterLast");
		newCust.setPassword("TesterPass");
		customerDAO.saveCustomer(newCust);
		Customer testCust = customerDAO.getCustomer(200);
		assertEquals(newCust.getFirstName(), testCust.getFirstName());
	}

	@Test
	public void testGetCustomerByEmail() {
		Customer newCust = new Customer();
		newCust.setEmail("TesterEmail");
		newCust.setFirstName("TesterFirst");
		newCust.setLastName("TesterLast");
		newCust.setPassword("TesterPass");
		customerDAO.saveCustomer(newCust);
		Customer testCust = customerDAO.getCustomerByEmail("TesterEmail");
		assertEquals(newCust.getFirstName(), testCust.getFirstName());
	}

	@Test
	public void testSaveCustomer() {
		Customer newCust = new Customer();
		newCust.setEmail("TesterEmail");
		newCust.setFirstName("TesterFirst");
		newCust.setLastName("TesterLast");
		newCust.setPassword("TesterPass");
		customerDAO.saveCustomer(newCust);
		List<Customer> allCustomers = customerDAO.getCustomers();
		assertTrue(allCustomers.contains(newCust));
	}

	@Test
	public void testUpdateCustomer() {
		Customer newCust = new Customer();
		newCust.setId(200);
		newCust.setEmail("TesterEmail");
		newCust.setFirstName("TesterFirst");
		newCust.setLastName("TesterLast");
		newCust.setPassword("TesterPass");
		customerDAO.saveCustomer(newCust);
		Customer middleCust = customerDAO.getCustomer(200);
		middleCust.setFirstName("Updated");
		customerDAO.saveCustomer(middleCust);
		Customer testCust = customerDAO.getCustomer(200);
		assertEquals("Updated", testCust.getFirstName());
	}

	@Test
	public void testDeleteCustomer() {
		Customer newCust = new Customer();
		newCust.setEmail("TesterEmail");
		newCust.setFirstName("TesterFirst");
		newCust.setLastName("TesterLast");
		newCust.setPassword("TesterPass");
		customerDAO.saveCustomer(newCust);
		Customer testCust = customerDAO.getCustomerByEmail("TesterEmail");
		customerDAO.deleteCustomer(testCust.getId());
		List<Customer> allCustomers = customerDAO.getCustomers();
		assertFalse(allCustomers.contains(testCust));
	}

}
