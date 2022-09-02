package com.example.junit.service;

import java.util.List;
import java.util.Optional;

import com.example.junit.model.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee emp);
	
	List<Employee> getAllEmployees();
	
	Optional<Employee> findById(long id);
	
	Employee updateEmployee(long id);
	
	void deleteEmployee(long id);
}
