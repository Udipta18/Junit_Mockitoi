package com.example.junit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.junit.model.Employee;
import com.example.junit.repo.EmployeeRepo;

@DataJpaTest
public class EmployeeRepoTest {

	@Autowired
	private EmployeeRepo employeeRepo;

	private Employee employee;

	
	//it will run before every test method
	@BeforeEach
	public void setUp() {
		employee = Employee.builder().firstName("UDIPTA").lastName("DAS").email("dasudipta@gmail.com").build();
	}

	// junit test for save employee operation

	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

		// given --precondition or setup
		//this boiler plate code is getting reduced by using before each annotation

		// when -- action or behaviour that we are going to test
		Employee savedEmployee = employeeRepo.save(employee);

		// then --verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	// JUNIT TEST FOR find all employee

	@Test
	public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {

		// given --precondition or setup

		Employee employee1 = Employee.builder().firstName("SOUVIK").lastName("SUR").email("dassur@gmail.com").build();

		employeeRepo.save(employee);
		employeeRepo.save(employee1);

		// when -- action or behaviour that we are going to test

		List<Employee> findAllEmployee = employeeRepo.findAll();

		// then --verify the output

		assertThat(findAllEmployee).isNotNull();
		assertThat(findAllEmployee.size()).isEqualTo(2);

	}

	// JUNIT TEST FOR FIND EMPLOYEE BY ID

	@Test
	public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

		// given --precondition or setup

		employeeRepo.save(employee);

		// when -- action or behaviour that we are going to test
		Employee employeeById = employeeRepo.findById(employee.getId()).get();

		// then --verify the output

		assertThat(employeeById).isNotNull();

	}

	// Junit test for get employee by email operation

	@DisplayName("Junit test for get employee by email operation")
	@Test
	public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {

		// given --precondition or setup

		employeeRepo.save(employee);

		// when -- action or behaviour that we are going to test

		Employee employeeByEmail = employeeRepo.findByEmail(employee.getEmail()).get();

		// then --verify the output

		assertThat(employeeByEmail).isNotNull();

	}

	// Junit test for update employee operation

	@DisplayName("Junit test for update employee  operation")
	@Test
	public void givenEmployeeObject_whenUpdateEmployeeObject_then() {

		// given --precondition or setup

		employeeRepo.save(employee);

		// when -- action or behaviour that we are going to test
		Employee employeeById = employeeRepo.findById(employee.getId()).get();
		employeeById.setEmail("das@gmail.com");
		employeeById.setFirstName("udu");
		Employee savedEmployee = employeeRepo.save(employeeById);

		// then --verify the output

		assertThat(savedEmployee.getEmail()).isEqualTo("das@gmail.com");
		assertThat(savedEmployee.getFirstName()).isEqualTo("udu");
	}

	// Junit test for delete Employee

	@DisplayName("Junit test for delete Employee")
	@Test
	public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {

		// given --precondition or setup

		employeeRepo.save(employee);
		// when -- action or behaviour that we are going to test

		employeeRepo.delete(employee);
		Optional<Employee> findById = employeeRepo.findById(employee.getId());

		// then --verify the output
		assertThat(findById).isEmpty();
	}

	// junit test for custom query jpql with index

	@DisplayName("junit test for custom query jpql with index")
	@Test
	public void givenFirstNameAndLastName_whenFindByJpql_thenReturnEmployeeObject() {

		// given --precondition or setup

		employeeRepo.save(employee);

		String firstName = "UDIPTA";
		String lastName = "DAS";

		// when -- action or behaviour that we are going to test

		Employee findByJpql = employeeRepo.findByJpql(firstName, lastName);

		// then --verify the output
		assertThat(findByJpql).isNotNull();

	}

	// junit test for custom query jpql with Named param

	@DisplayName("junit test for custom query jpql with Named param")
	@Test
	public void givenFirstNameAndLastName_whenFindByJpqlNamedParam_thenReturnEmployeeObject() {

		// given --precondition or setup

		employeeRepo.save(employee);

		String firstName = "UDIPTA";
		String lastName = "DAS";

		// when -- action or behaviour that we are going to test

		Employee findByJpql = employeeRepo.findByJpqlNamedParameters(firstName, lastName);

		// then --verify the output
		assertThat(findByJpql).isNotNull();

	}

	// junit test for custom native query sql with index

	@DisplayName("junit test for custom  native query sql with index")
	@Test
	public void givenFirstNameAndLastName_whenFindByNativeQuerySql_thenReturnEmployeeObject() {

		// given --precondition or setup

		employeeRepo.save(employee);

		// when -- action or behaviour that we are going to test

		Employee findByNativeSql = employeeRepo.findByNativeSql(employee.getFirstName(), employee.getLastName());

		// then --verify the output
		assertThat(findByNativeSql).isNotNull();

	}

	// junit test for custom native query sql with named param

	@DisplayName("junit test for custom  native query sql with index")
	@Test
	public void givenFirstNameAndLastName_whenFindByNativeQuerySqlNamedParam_thenReturnEmployeeObject() {

		// given --precondition or setup

		employeeRepo.save(employee);

		// when -- action or behaviour that we are going to test

		Employee findByNativeSqlNamedParm = employeeRepo.findByNativeSqlByParam(employee.getFirstName(),
				employee.getLastName());

		// then --verify the output
		assertThat(findByNativeSqlNamedParm).isNotNull();

	}

}
