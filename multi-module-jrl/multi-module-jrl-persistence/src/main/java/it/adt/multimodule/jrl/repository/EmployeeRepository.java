package it.adt.multimodule.jrl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.adt.multimodule.jrl.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
