package com.example.junit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito.BDDMyOngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import com.example.junit.model.Employee;
import com.example.junit.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EmployeeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//junit for testing createEmployee api
	
	@DisplayName("junit for testing createEmployee api")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenREturnJson() throws JsonProcessingException, Exception {

		//given  --precondition or setup
		Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
		
		given(employeeService.saveEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0) );
		

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

		given(employeeService.getAllEmployees()).willReturn(emp);
		//when -- action or behaviour that we are going to test
              ResultActions response = mockMvc.perform(get("/api/employee")
            		  .contentType(MediaType.APPLICATION_JSON)
            		  .content(objectMapper.writeValueAsString(emp)));
              
		
		//then --verify the output
          response.andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()",is(emp.size())));
              
	}
	//junit for update employee by id
	
	@DisplayName("junit for update employee by id")
	@Test
	public void givenEmployeeId_whenUpdatedEmpById_thenReturnUpdatedEmployee() throws JsonProcessingException, Exception {

		//given  --precondition or setup
		Employee employee = Employee.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build();
		Employee employee2 = Employee.builder().firstName("SOUVIK").lastName("SUR").email("ramesh@gmail.com").build();
		
	    given(employeeService.updateEmployee(1L)).willReturn(employee2);
	    
	    //for negative case
	    //given(employeeService.updateEmployee(1L)).willReturn(null);

		//when -- action or behaviour that we are going to test
            ResultActions response = mockMvc.perform(post("/api/employee/{id}",employee.getId()));
            		
		
		//then --verify the output
            response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(employee2.getFirstName())));

	}
	

	 // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }
    
    
    
    //junit test for delete employee
    
    @DisplayName("junit test for delete employee")
    @Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessfullyDeleted() throws Exception {

		//given  --precondition or setup
    	      	  
    	 willDoNothing().given(employeeService).deleteEmployee(1L);

		//when -- action or behaviour that we are going to test
    	 
    	 ResultActions result = mockMvc.perform(delete("/api/employee/{id}",1L));

		//then --verify the output
    	 
    	 result.andDo(print())
    	       .andExpect(status().isOk());

	}
	
}
