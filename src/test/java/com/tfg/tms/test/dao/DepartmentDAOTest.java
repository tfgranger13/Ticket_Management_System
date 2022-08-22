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

import com.tfg.tms.dao.DepartmentDAO;
import com.tfg.tms.entity.Department;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Rollback
@SpringBootTest(properties = { "logging.file=logs/TMStesting.log" })
public class DepartmentDAOTest {

	@Autowired
	private DepartmentDAO departmentDAO;

	// before each test, log the name of the test being run
	@BeforeEach
	public void logTest(TestInfo testInfo) {
		log.warn("Running test " + testInfo.getDisplayName());
	}

	@Test
	public void testRepositoryIsNotNull() {
		assertNotNull(departmentDAO);
	}

	@Test
	public void testGetDepartments() {
		Department newDept = new Department();
		newDept.setName("TesterName");
		newDept.setDescription("TesterDescription");
		departmentDAO.saveDepartment(newDept);
		List<Department> allDepartments = departmentDAO.getDepartments();
		assertEquals(allDepartments.contains(newDept), true);
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 1, 3, 4, 5 })
	public void testGetDepartments(int id) {
		List<Department> allDepartments = departmentDAO.getDepartments();
		Department testDept = departmentDAO.getDepartment(id);
		assertTrue(allDepartments.contains(testDept));
	}

	@ParameterizedTest(name = "{index}-Run Test with args={0}")
	@ValueSource(ints = { 7, 14, 193 })
	public void testGetDepartmentsNotInDb(int id) {
		List<Department> allDepartments = departmentDAO.getDepartments();
		Department testDept = departmentDAO.getDepartment(id);
		assertFalse(allDepartments.contains(testDept));
	}

	@Test
	public void testGetDepartment() {
		Department newDept = new Department();
		newDept.setId(200);
		newDept.setName("TesterName");
		newDept.setDescription("TesterDescription");
		departmentDAO.saveDepartment(newDept);
		Department testDept = departmentDAO.getDepartment(200);
		assertEquals(newDept.getName(), testDept.getName());
	}

	@Test
	public void testSaveDepartment() {
		Department newDept = new Department();
		newDept.setName("TesterName");
		newDept.setDescription("TesterDescription");
		departmentDAO.saveDepartment(newDept);
		List<Department> allDepartments = departmentDAO.getDepartments();
		assertTrue(allDepartments.contains(newDept));
	}

	@Test
	public void testUpdateDepartment() {
		Department newDept = new Department();
		newDept.setId(200);
		newDept.setName("TesterName");
		newDept.setDescription("TesterDescription");
		departmentDAO.saveDepartment(newDept);
		Department middleDept = departmentDAO.getDepartment(200);
		middleDept.setName("Updated");
		departmentDAO.saveDepartment(middleDept);
		Department testDept = departmentDAO.getDepartment(200);
		assertEquals("Updated", testDept.getName());
	}

	@Test
	public void testDeleteDepartment() {
		Department testDept = departmentDAO.getDepartment(1);
		Department checkDept = testDept;
		departmentDAO.deleteDepartment(1);
		List<Department> allDepartments = departmentDAO.getDepartments();
		assertFalse(allDepartments.contains(checkDept));
	}

}
