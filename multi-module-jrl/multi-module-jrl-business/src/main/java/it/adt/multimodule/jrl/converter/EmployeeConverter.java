package it.adt.multimodule.jrl.converter;

import java.util.ArrayList;
import java.util.List;

import it.adt.multimodule.jrl.dto.EmployeeDTO;
import it.adt.multimodule.jrl.entity.Company;
import it.adt.multimodule.jrl.entity.Employee;


public class EmployeeConverter {
	
	public static Employee convertDtoToModel( EmployeeDTO dto ) {
		if( dto == null ) return null;
		Employee model = new Employee();
		model.setCode( dto.getCode() );
		model.setFirstname( dto.getFirstname() );
		model.setSurname( dto.getSurname() );
		model.setAge( dto.getAge() );
		model.setJob( dto.getJob() );
		if( dto.getCompanyName() == null ) {
			model.setCompany( null );
		}
		else {
			Company temp = new Company();
			temp.setName( dto.getCompanyName() );
			model.setCompany( temp );
		}
		return model;
	}
	
	public static List<Employee> convertDtoToModel( List<EmployeeDTO> dtos ){
		if( dtos == null ) return null;
		List<Employee> models = new ArrayList<>();
		dtos.forEach( d -> models.add( EmployeeConverter.convertDtoToModel( d ) ) );
		return models;
	}
	
	public static EmployeeDTO convertModelToDto( Employee model ) {
		return model == null? null : new EmployeeDTO(
					model.getCode(),
					model.getFirstname(),
					model.getSurname(),
					model.getAge(),
					model.getJob(),
					model.getCompany() == null? null : model.getCompany().getName()
				);
	}
	
	public static List<EmployeeDTO> convertModelToDto( List<Employee> models ){
		if( models == null ) return null;
		List<EmployeeDTO> dtos = new ArrayList<>();
		models.forEach( m -> dtos.add( EmployeeConverter.convertModelToDto( m ) ) );
		return dtos;
	}

}
