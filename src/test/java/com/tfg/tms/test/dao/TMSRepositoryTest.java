package com.tfg.tms.test.dao;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.tfg.tms.TMSApplication;
import com.tfg.tms.dao.CustomerRepository;

@Transactional
@Rollback
@SpringBootTest(classes = TMSApplication.class)
public class TMSRepositoryTest {

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void testRepositoryIsNotNull() {
		Assertions.assertNotNull(customerRepository);
	}

}
