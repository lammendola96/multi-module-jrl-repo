package it.adt.multimodule.jrl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.adt.multimodule.jrl.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String>{

}
