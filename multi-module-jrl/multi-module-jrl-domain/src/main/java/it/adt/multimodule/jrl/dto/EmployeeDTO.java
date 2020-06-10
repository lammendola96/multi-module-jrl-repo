package it.adt.multimodule.jrl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class EmployeeDTO {
	
	private Long code;
	
	private String firstname;

	private String surname;
	
	private Integer age;
	
	private String job;
	
	private String companyName;

}
