package com.example.junit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.junit.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	
	Optional<Employee> findByEmail(String email);
	
	 // define custom query using JPQL with index params
	@Query("select e from Employee e where e.firstName=?1 and e.lastName=?2")
	Employee findByJpql(String firstName,String lastName);
	
	// define custom query using JPQL with named params
	@Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
	Employee findByJpqlNamedParameters(@Param("firstName") String firstName,@Param("lastName") String lastName);
	
	@Query(value=("select * from employees e where e.first_name=?1 and e.last_name=?2"),nativeQuery = true)
	Employee findByNativeSql(String firstName,String lastName);
	
	
	@Query(value=("select * from employees e where e.first_name=:firstName and e.last_name=:lastName"),nativeQuery = true)
	Employee findByNativeSqlByParam(@Param("firstName") String firstName,@Param("lastName") String lastName);
}
