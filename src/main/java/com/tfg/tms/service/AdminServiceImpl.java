package com.tfg.tms.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.tms.dao.CustomerDAO;
import com.tfg.tms.dao.DepartmentDAO;
import com.tfg.tms.dao.EmployeeDAO;
import com.tfg.tms.dao.MessageDAO;
import com.tfg.tms.dao.TicketDAO;
import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Department;
import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Message;
import com.tfg.tms.entity.Ticket;

/*
 * This class is the implementation of the admin service
 */

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * 
	 * inject the customerDAO
	 * 
	 */
	@Autowired
	private CustomerDAO customerDAO;

	@Override
	@Transactional
	public List<Customer> getCustomers() {
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional
	public Customer getCustomer(Integer id) {
		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		// if there's no id, it's a new person so encrypt their password
		if (customer.getId() == null) {
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		}
		// then save them in the database
		customerDAO.saveCustomer(customer);
	}

	@Override
	@Transactional
	public void removeCustomerTickets(Integer id) {
		customerDAO.removeCustomerTickets(id);

	}

	@Override
	@Transactional
	public void deleteCustomer(Integer id) {
		customerDAO.deleteCustomer(id);
	}

	@Override
	@Transactional
	public Set<Ticket> getCustomerTickets(Integer id) {
		return customerDAO.getCustomerTickets(id);
	}

	@Override
	@Transactional
	public Map<Integer, Set<Ticket>> getAllCustomerTickets() {
		return customerDAO.getAllCustomerTickets();
	}

	/*
	 * 
	 * inject the employeeDAO
	 * 
	 */
	@Autowired
	private EmployeeDAO employeeDAO;

	@Override
	@Transactional
	public List<Employee> getEmployees() {
		return employeeDAO.getEmployees();
	}

	@Override
	@Transactional
	public Employee getEmployee(Integer id) {
		return employeeDAO.getEmployee(id);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		// if there's no id, it's a new person so encrypt their password
		if (employee.getId() == null) {
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		}
		// then save them in the database
		employeeDAO.saveEmployee(employee);
	}

	@Override
	@Transactional
	public void clearEmployeeTickets(Integer id) {
		employeeDAO.clearEmployeeTickets(id);
	}

	@Override
	@Transactional
	public void deleteEmployee(Integer id) {
		employeeDAO.deleteEmployee(id);
	}

	/*
	 * 
	 * inject the ticketDAO
	 * 
	 */

	@Autowired
	private TicketDAO ticketDAO;

	@Override
	@Transactional
	public List<Ticket> getTickets() {
		return ticketDAO.getTickets();
	}

	@Override
	@Transactional
	public Ticket getTicket(Integer id) {
		return ticketDAO.getTicket(id);
	}

	@Override
	@Transactional
	public void saveTicket(Ticket ticket) {
		ticketDAO.saveTicket(ticket);
	}

	@Override
	@Transactional
	public void deleteTicket(Integer id) {
		ticketDAO.deleteTicket(id);
	}

	/*
	 * 
	 * inject the departmentDAO
	 * 
	 */

	@Autowired
	private DepartmentDAO departmentDAO;

	@Override
	@Transactional
	public List<Department> getDepartments() {
		return departmentDAO.getDepartments();
	}

	@Override
	@Transactional
	public Department getDepartment(Integer id) {
		return departmentDAO.getDepartment(id);
	}

	@Override
	@Transactional
	public void saveDepartment(Department department) {
		departmentDAO.saveDepartment(department);
	}

	@Override
	@Transactional
	public void clearDepartmentEmployees(Integer id) {
		departmentDAO.clearDepartmentEmployees(id);
	}

	@Override
	@Transactional
	public void clearDepartmentTickets(Integer id) {
		departmentDAO.clearDepartmentTickets(id);
	}

	@Override
	@Transactional
	public void deleteDepartment(Integer id) {
		departmentDAO.deleteDepartment(id);
	}

	/*
	 * 
	 * inject the messageDAO
	 * 
	 */

	@Autowired
	private MessageDAO messageDAO;

	@Override
	@Transactional
	public List<Message> getMessages() {
		return messageDAO.getMessages();
	}

	@Override
	@Transactional
	public Message getMessage(Integer id) {
		return messageDAO.getMessage(id);
	}

	@Override
	@Transactional
	public void saveMessage(Message message) {
		messageDAO.saveMessage(message);
	}

	@Override
	@Transactional
	public void deleteMessage(Integer id) {
		messageDAO.deleteMessage(id);
	}

	/*
	 * Special services
	 */

	@Override
	@Transactional
	public Employee getEmployeeWithFewestTicketsFromDept(Integer id) {
		return employeeDAO.getEmployeeWithFewestTicketsFromDept(id);
	}

	@Override
	@Transactional
	public List<Message> getMessagesFromTicket(Integer id) {
		return messageDAO.getMessagesFromTicket(id);
	}

}
