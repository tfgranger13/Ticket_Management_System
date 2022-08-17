package com.tfg.tms.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tfg.tms.entity.Department;
import com.tfg.tms.entity.Employee;
import com.tfg.tms.entity.Ticket;

/*
 * This class connects to the database to perform CRUD operations on departments
 */

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {

//	@Autowired
//	private SessionFactory sessionFactory;

	// define field for entitymanager
	private EntityManager entityManager;

	// set up constructor injection
	@Autowired
	public DepartmentDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Department> getDepartments() {

		// get the current session from hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Query<Department> theQuery = currentSession.createQuery("from Department", Department.class);

		// execute the query and get results
		List<Department> departments = theQuery.getResultList();

		// return the results
		return departments;
	}

	@Override
	public Department getDepartment(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Department department = currentSession.get(Department.class, id);
		return department;
	}

	@Override
	public void saveDepartment(Department department) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(department);
	}

	@Override
	public void clearDepartmentEmployees(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Department department = currentSession.get(Department.class, id);
		// get the set of employees in this department
		Set<Employee> employees = department.getEmployees();
		// for each employee in the set
		for (Employee employee : employees) {
			// set their department to null
			employee.setDepartment(null);
			// update the employee
			currentSession.update(employee);
		}

	}

	@Override
	public void clearDepartmentTickets(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Department department = currentSession.get(Department.class, id);
		// get the set of employees in this department
		Set<Ticket> tickets = department.getTickets();
		// for each employee in the set
		for (Ticket ticket : tickets) {
			// set their department to null
			ticket.setDepartment(null);
			// update the employee
			currentSession.update(ticket);
		}
	}

	@Override
	public void deleteDepartment(Integer id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Department department = currentSession.get(Department.class, id);
		currentSession.delete(department);
	}

}
