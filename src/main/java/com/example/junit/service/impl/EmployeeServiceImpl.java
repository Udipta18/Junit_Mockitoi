package com.example.junit.service.impl;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.junit.model.Employee;
import com.example.junit.repo.EmployeeRepo;
import com.example.junit.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public Employee saveEmployee(Employee emp) {
		 
		
		         Optional<Employee> findByEmail = employeeRepo.findByEmail(emp.getEmail());
		               if(findByEmail.isPresent()) {
		                   	   throw new ResolutionException("Employee already exists");
		                      }
		return employeeRepo.save(emp);
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeRepo.findAll();
	}

	@Override
	public Optional<Employee> findById(long id) {
		
		return employeeRepo.findById(id);
	}

	@Override
	public Employee updateEmployee(long id) {
		 Employee employee = employeeRepo.findById(id).get();
		  employee.setFirstName("SOUVIK");
		  employee.setLastName("SUR");
		  employeeRepo.save(employee);
		return employee;
	}

	@Override
	public void deleteEmployee(long id) {
		 Employee employee = employeeRepo.findById(id).get();
		 employeeRepo.delete(employee);
		
	}

}
