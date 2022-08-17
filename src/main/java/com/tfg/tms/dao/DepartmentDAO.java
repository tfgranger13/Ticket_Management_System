package com.tfg.tms.dao;

import java.util.List;

import com.tfg.tms.entity.Department;

/*
 * This class is the interface for the department dao and declares which methods must be included in its implementation
 */

public interface DepartmentDAO {

	public List<Department> getDepartments();

	public Department getDepartment(Integer id);

	public void saveDepartment(Department department);

	public void deleteDepartment(Integer id);

	public void clearDepartmentEmployees(Integer id);

	public void clearDepartmentTickets(Integer id);

}
