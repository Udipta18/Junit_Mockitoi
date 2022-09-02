package com.example.junit.integration;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.junit.model.Employee;
import com.example.junit.repo.EmployeeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment =WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class EmployeeControllerIContainerItTests2 extends AbstractionBaseTest{
	
	
	

	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
    private EmployeeRepo employeeRepo;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
	 employeeRepo.deleteAll();	
	}
	
	
	    //junit for testing createEmployee api
	
		@DisplayName("junit for testing createEmployee api")
		@Test
		public void givenEmployeeObject_whenSaveEmployee_thenREturnJson() throws JsonProcessingException, Exception {
			
			
			//to check  Docker container details
			/*
			 * System.out.println(mySQLContainer.getUsername());
			 * System.out.println(mySQLContainer.getPassword());
			 * System.out.println(mySQLContainer.getDatabaseName());
			 * System.out.println(mySQLContainer.getJdbcUrl());
			 */

			//given  --precondition or setup
			Employee employee = Employee.builder()
	                .firstName("Ramesh")
	                .lastName("Fadatare")
	                .email("ramesh@gmail.com")
	                .build();
			
			
			

			//when -- action or behaviour that we are going to test
			ResultActions response = mockMvc.perform(post("/api/employee")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(employee)));
			

			//then --verify the output

			response.andDo(print())
			   .andExpect(status().isCreated())
			   .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
			   .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
			   .andExpect(jsonPath("$.email", is(employee.getEmail())));
		}
		
		@Test
		public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() throws JsonProcessingException, Exception {

			//given  --precondition or setup
			List<Employee> emp=new ArrayList<>();
			emp.add(Employee.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build());
			emp.add(Employee.builder().firstName("UDIPTA").lastName("DAS").email("DAS@gmail.com").build());
			employeeRepo.saveAll(emp);
			
			//when -- action or behaviour that we are going to test
	              ResultActions response = mockMvc.perform(get("/api/employee"));
	            		 
	              
			
			//then --verify the output
	          response.andDo(print())
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.size()",is(emp.size())));
	              
		}
		
		// positive scenario - valid employee id
	    // JUnit test for GET employee by id REST API
	    @Test
	    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
	        // given - precondition or setup
	 
	        Employee employee = Employee.builder()
	                .firstName("Ramesh")
	                .lastName("Fadatare")
	                .email("ramesh@gmail.com")
	                .build();
	        employeeRepo.save(employee);

	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employee.getId()));

	        // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
	                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
	                .andExpect(jsonPath("$.email", is(employee.getEmail())));

	    }
	    
	  //junit for update employee by id
		
		@DisplayName("junit for update employee by id")
		@Test
		public void givenEmployeeId_whenUpdatedEmpById_thenReturnUpdatedEmployee() throws JsonProcessingException, Exception {

			//given  --precondition or setup
			Employee employee = Employee.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build();
			employeeRepo.save(employee);
			Employee employee2 = Employee.builder().firstName("SOUVIK").lastName("SUR").email("ramesh@gmail.com").build();
			
		    
		    //for negative case
		    //given(employeeService.updateEmployee(1L)).willReturn(null);

			//when -- action or behaviour that we are going to test
	            ResultActions response = mockMvc.perform(post("/api/employee/{id}",employee.getId()));
	            		
			
			//then --verify the output
	            response.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.firstName", is(employee2.getFirstName())));

		}
		
		//junit test for delete employee
	    
	    @DisplayName("junit test for delete employee")
	    @Test
		public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessfullyDeleted() throws Exception {

			//given  --precondition or setup
	    	   Employee employee = Employee.builder()
		                .firstName("Ramesh")
		                .lastName("Fadatare")
		                .email("ramesh@gmail.com")
		                .build();
		        employeeRepo.save(employee);
		        
		      
	    	 

			//when -- action or behaviour that we are going to test
	    	 
	    	 ResultActions result = mockMvc.perform(delete("/api/employee/{id}",employee.getId()));

			//then --verify the output
	    	 
	    	 result.andDo(print())
	    	       .andExpect(status().isOk());

		}
	
}
