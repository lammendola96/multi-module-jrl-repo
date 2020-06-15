package it.adt.multimodule.jrl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table( name = "impiegati" )
public @Data class Employee {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "matricola" )
	private Long code;
	
	@Column( name = "nome" )
	private String firstname;
	
	@Column( name = "cognome" )
	private String surname;
	
	@Column( name = "eta" )
	private Integer age;
	
	@Column( name = "occupazione" )
	private String job;
	
//	@JsonBackReference  non mi serve se uso i DTO risolvendo i problemi di serializzaazione
	@ManyToOne
	@JoinColumn( name = "nome_azienda" )
	private Company company;
	
}
