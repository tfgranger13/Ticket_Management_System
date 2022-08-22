package com.tfg.tms.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Ticket;

/*
 * This class connects to the database to perform CRUD operations on employees
 */

// links with Spring
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public EmployeeDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	// transactions handled in service
	public List<Employee> getEmployees() {

		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

		// execute the query and get results
		List<Employee> employees = theQuery.getResultList();

		// return the results
		return employees;
	}

	@Override
	public Employee getEmployee(Integer id) {

		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// get the employee
		Employee employee = currentSession.get(Employee.class, id);

		return employee;

	}

	@Override
	public Employee getEmployeeByEmail(String email) throws NoResultException {

		try {

			// get the current session from hibernate
			Session currentSession = entityManager.unwrap(Session.class);

			// create a query
			Query<Employee> query = currentSession.createQuery("from Employee e where e.email=:email", Employee.class);

			// set the parameter
			query.setParameter("email", email);

			// return the customer
			return query.getSingleResult();
		}

		// if the user doesn't exist, it throws an error
		catch (NoResultException e) {
			// so return null instead
			return null;
		}
	}

	@Override
	public void saveEmployee(Employee employee) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// save the user in the session
		currentSession.saveOrUpdate(employee);

	}

	@Override
	public void clearEmployeeTickets(Integer id) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// get the employee
		Employee employee = currentSession.get(Employee.class, id);

		// get the employee's tickets
		Set<Ticket> empTickets = employee.getTickets();

		// loop thru the tickets and set the employee to null
		// this saves the ticket when the employee is deleted
		for (Ticket ticket : empTickets) {
			ticket.setEmployee(null);
			currentSession.update(ticket);
		}

	}

	@Override
	public void deleteEmployee(Integer id) {
		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// get the employee
		Employee employee = currentSession.get(Employee.class, id);

		// save the user in the session
		currentSession.delete(employee);
	}

	@Override
	public Employee getEmployeeWithFewestTicketsFromDept(Integer id) {

		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// create the query for the employees in the selected department
		Query<Employee> query = currentSession.createQuery("from Employee e where e.department.id=:department_id",
				Employee.class);

		// set the parameter
		query.setParameter("department_id", id);

		// get the list of all employees from that department
		List<Employee> employeeList = query.getResultList();

		// set the lowest tickets employee equal to the first employee in the results
		Employee lowest = employeeList.get(0);

		// for each employee in the results
		for (Employee employee : employeeList) {
			// if their tickets size is fewer than the current lowest
			if (employee.getTickets().size() < lowest.getTickets().size()) {
				// set the lowest equal to this employee
				lowest = employee;
			}
		}

		// return the lowest ticket employee from that dept
		return lowest;
	}

}
