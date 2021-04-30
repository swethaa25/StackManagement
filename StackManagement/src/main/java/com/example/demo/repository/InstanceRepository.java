package com.example.demo.repository;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Instance;


@Repository
public interface InstanceRepository extends JpaRepository<Instance, Long>{
	
	@Transactional
	@Modifying
	@Query("update Instance i set i.status = ?1 WHERE i.instance_name = ?2")
	void savebyname(String status,String instance_name);
	
	
	@Query(value = "SELECT * FROM instance i WHERE i.instance_name = ?1", nativeQuery = true)
	Instance searchbyname(String instance_name);
	
}
