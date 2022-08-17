package com.tfg.tms.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tfg.tms.entity.Customer;
import com.tfg.tms.entity.Department;
import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Message;
import com.tfg.tms.entity.Ticket;

/*
 * This class is the interface for the admin service
 */

public interface AdminService {

	// services for customers
	public List<Customer> getCustomers();

	public Customer getCustomer(Integer id);

	public void saveCustomer(Customer customer);

	public void deleteCustomer(Integer id);

	public Set<Ticket> getCustomerTickets(Integer id);

	public Map<Integer, Set<Ticket>> getAllCustomerTickets();

	// services for employees
	public List<Employee> getEmployees();

	public Employee getEmployee(Integer id);

	public void saveEmployee(Employee employee);

	public void clearEmployeeTickets(Integer id);

	public void deleteEmployee(Integer id);

	// services for tickets
	public List<Ticket> getTickets();

	public Ticket getTicket(Integer id);

	public void saveTicket(Ticket ticket);

	public void deleteTicket(Integer id);

	// services for departments
	public List<Department> getDepartments();

	public Department getDepartment(Integer id);

	public void saveDepartment(Department department);

	public void clearDepartmentEmployees(Integer id);

	public void deleteDepartment(Integer id);

	public void removeCustomerTickets(Integer id);

	public void clearDepartmentTickets(Integer id);

	// services for messages

	public List<Message> getMessages();

	public Message getMessage(Integer id);

	public void saveMessage(Message message);

	public void deleteMessage(Integer id);

	// special services

	public Employee getEmployeeWithFewestTicketsFromDept(Integer id);

	public List<Message> getMessagesFromTicket(Integer id);

}
