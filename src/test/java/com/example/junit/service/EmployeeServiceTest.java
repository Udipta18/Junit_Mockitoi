package com.example.junit.service;

import java.lang.module.ResolutionException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.junit.model.Employee;
import com.example.junit.repo.EmployeeRepo;
import com.example.junit.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	
	@Mock
	private EmployeeRepo employeeRepo;
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	
	private Employee employee;
	@BeforeEach
	public void setUp() {
		 employee=Employee.builder()
				.id(1L)
				.firstName("UDIPTA")
				.lastName("DAS")
				.email("das@gmail.com")
				.build();
	}
	
	
	//Junit test for save Employee
	
	@DisplayName("Junit test for save Employee")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

		//given  --precondition or setup
		 given(employeeRepo.findByEmail(employee.getEmail()))
         .willReturn(Optional.empty());
		 
		 given(employeeRepo.save(employee)).willReturn(employee);

		//when -- action or behaviour that we are going to test
		  Employee saveEmployee = employeeService.saveEmployee(employee);

		//then --verify the output
      assertThat(saveEmployee).isNotNull();
	}
	
	
	
	//Junit test for save Existing Employee
	
		@DisplayName("Junit test for save Existing Employee")
		@Test
		public void givenExistingEmployeeObject_whenSaveEmployee_thenThrowException() {

			//given  --precondition or setup
			 given(employeeRepo.findByEmail(employee.getEmail()))
	         .willReturn(Optional.of(employee));
			 
			// given(employeeRepo.save(employee)).willReturn(employee);

			//when -- action or behaviour that we are going to test
			 //to handling the exception
			org.junit.jupiter.api.Assertions.assertThrows(ResolutionException.class, () ->{
				employeeService.saveEmployee(employee);
			});

			//then --verify the output
			//so that save method never called when exception occurs
			verify(employeeRepo,never()).save(any(Employee.class));
	      
		}
		
		//Junit for get all employees
		@DisplayName("Junit for get all employees")
		@Test
		public void givenEmployyeList_whengetAllEmployees_thenReturnEmployeeList() {
			
			

			// given --precondition or setup
			
			Employee employee2=Employee.builder()
						.id(2L)
						.firstName("DEMON")
						.lastName("DAS")
						.email("demon@gmail.com")
						.build();
			 
			given(employeeRepo.findAll()).willReturn(List.of(employee,employee2));

			// when -- action or behaviour that we are going to test
			
			List<Employee> allEmployees = employeeService.getAllEmployees();

			// then --verify the output
			assertThat(allEmployees).isNotNull();
			assertThat(allEmployees.size()).isEqualTo(2);

		}
		
		
		
		       //Junit for get all employees where list is null (negative scenario)
				@DisplayName("Junit for get all employees")
				@Test
				public void givenEmptyEmployyeList_whengetAllEmployees_thenReturnEmptyEmployeeList() {
					
					

					// given --precondition or setup
					
					Employee employee2=Employee.builder()
								.id(2L)
								.firstName("DEMON")
								.lastName("DAS")
								.email("demon@gmail.com")
								.build();
					 
					given(employeeRepo.findAll()).willReturn(Collections.emptyList());

					// when -- action or behaviour that we are going to test
					
					List<Employee> allEmployees = employeeService.getAllEmployees();

					// then --verify the output
					assertThat(allEmployees).isEmpty();
					assertThat(allEmployees.size()).isEqualTo(0);

				}
				
				
				
				//Junit for gte employees with id
				@DisplayName("Junit for get all employees")
				@Test
				public void givenEmployeeId_whenFindById_thenReturnEmployeeObject() {
					
					

					// given --precondition or setup
					 
					given(employeeRepo.findById(1L)).willReturn(Optional.of(employee));
					
					// when -- action or behaviour that we are going to test
					
					Employee employeeFindById = employeeService.findById(employee.getId()).get();

					// then --verify the output
					assertThat(employeeFindById).isNotNull();
			

				}
				
				//Junit for update employee by id
				@DisplayName("Junit for update employee by id")
				@Test
				public void givenEmployeeId_whenUpdateEmployee_thenReturnEmployeeObject() {
					
					

					// given --precondition or setup
					 
					given(employeeRepo.findById(1L)).willReturn(Optional.of(employee));
					given(employeeRepo.save(employee)).willReturn(employee);
					
					// when -- action or behaviour that we are going to test
					
					Employee updatedEmployee = employeeService.updateEmployee(1L);

					// then --verify the output
					assertThat(updatedEmployee).isNotNull();
					assertThat(updatedEmployee.getFirstName()).isEqualTo("SOUVIK");
					assertThat(updatedEmployee.getLastName()).isEqualTo("SUR");

				}
				
				//Junit for delete employee by id
				@DisplayName("Junit for delete employee by id")
				@Test
				public void givenEmployeeId_whenDeleteEmployee_thenEmployeeDeleted() {
					
					

					// given --precondition or setup
					 
					given(employeeRepo.findById(1L)).willReturn(Optional.of(employee));
					
					// when -- action or behaviour that we are going to test
					
					employeeService.deleteEmployee(1L);

					// then --verify the output
					verify(employeeRepo,times(1)).delete(employee);

				}
		

}
