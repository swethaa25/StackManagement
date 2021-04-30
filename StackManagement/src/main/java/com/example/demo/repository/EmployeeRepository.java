package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	 @Query(value = "SELECT * FROM employee e WHERE e.stack = ?1 LIMIT 3", nativeQuery = true)
	 List<Employee> recent(String stack);
	
	 @Query(value = "SELECT * FROM employee e WHERE e.Employeeid = ?1", nativeQuery = true)
	 List<Employee> findemp(Long Employeeid);
	 @Modifying
	  @Query(value = "insert into employee(employeeid,first_name,email,stack,time_stamp) VALUES (:id,:firstName,:email,:stack,:time_stamp)", nativeQuery = true)
	   @Transactional
	   void savebyinsert(@Param("id") Long Employeeid,  @Param("firstName")String firstName,@Param("email")String email,@Param("stack")String stack,@Param("time_stamp")String time_stamp);
	 @Query(value = "SELECT status FROM instance i WHERE i.instance_name = ?1", nativeQuery = true)
	 String status(String instance_name);
	
}
