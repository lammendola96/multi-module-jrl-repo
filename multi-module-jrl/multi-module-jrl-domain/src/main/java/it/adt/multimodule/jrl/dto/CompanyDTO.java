package it.adt.multimodule.jrl.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public @Data class CompanyDTO {
	
	private String name;
	
	private String product;
	
	private Double turnover;
	
	private List<Long> employeeCodes;

}
