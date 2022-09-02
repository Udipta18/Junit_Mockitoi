package com.example.junit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.junit.model.Employee;
import com.example.junit.service.EmployeeService;

import lombok.Delegate;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	
	private EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService=employeeService;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	
	@GetMapping
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	
	@PostMapping("/{id}")
	public ResponseEntity<Employee>  updateEmployeeById(@PathVariable("id") Long id){
		
		
		Employee updatedEmployee = employeeService.updateEmployee(id);
		if(updatedEmployee!=null)
		return new ResponseEntity<Employee>(updatedEmployee,HttpStatus.OK);
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
	public Employee getById(@PathVariable("id") Long id) {
		 Employee employee = employeeService.findById(id).get();
		 return employee;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<String>("Successfully deleted",HttpStatus.OK);
	}
}
