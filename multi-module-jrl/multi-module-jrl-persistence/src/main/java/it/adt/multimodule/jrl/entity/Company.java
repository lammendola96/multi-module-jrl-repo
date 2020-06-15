package it.adt.multimodule.jrl.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table( name = "aziende" )
public @Data class Company {

	@Id
	@Column( name = "nome" )
	private String name;
	
	@Column( name = "prodotto" )
	private String product;
	
	@Column( name = "fatturato" )
	private Double turnover;
	
	@OneToMany( mappedBy = "company" )
	private List<Employee> employees;
	
}
