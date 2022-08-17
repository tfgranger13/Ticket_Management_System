package com.tfg.tms.dao;

import java.util.List;

import com.tfg.tms.entity.Employee;

/*
 * This class is the interface for the employee dao and declares which methods must be included in its implementation
 */

public interface EmployeeDAO {

	public List<Employee> getEmployees();

	public Employee getEmployee(Integer id);

	public void saveEmployee(Employee employee);

	public void clearEmployeeTickets(Integer id);

	public void deleteEmployee(Integer id);

	public Employee getEmployeeByEmail(String email);

	public Employee getEmployeeWithFewestTicketsFromDept(Integer id);

}
