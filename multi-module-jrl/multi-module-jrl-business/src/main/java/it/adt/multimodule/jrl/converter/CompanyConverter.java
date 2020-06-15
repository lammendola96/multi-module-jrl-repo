package it.adt.multimodule.jrl.converter;

import java.util.ArrayList;
import java.util.List;

import it.adt.multimodule.jrl.dto.CompanyDTO;
import it.adt.multimodule.jrl.entity.Company;
import it.adt.multimodule.jrl.entity.Employee;

public class CompanyConverter {
	
	public static Company convertDtoToModel( CompanyDTO dto ) {
		if( dto == null ) return null;
		Company model = new Company();
		model.setName( dto.getName() );
		model.setProduct( dto.getProduct() );
		model.setTurnover( dto.getTurnover() );
//		if( dto.getEmployeeCodes() == null ) {
//			model.setEmployees( null );
//		}
//		else {
//			List<Employee> employees = new ArrayList<Employee>();
//			dto.getEmployeeCodes().forEach( c -> {
//				Employee temp = new Employee();
//				temp.setCode( c );
//				dto.getEmployeeCodes().add( c );
//			} );
//			model.setEmployees( employees );
//		}
		//questa conversione mi servirà solo nella post e update, perchè ho un body in cui, anche se inserisco
		//una lista di matricole, non posso modificare le foreign keys degli impiegati per farla riferire a 
		//quesa azienda
		model.setEmployees( null );
		return model;
	}
	
	public static List<Company> convertDtoToModel( List<CompanyDTO> dtos ){
		if( dtos == null ) return null;
		List<Company> models = new ArrayList<>();
		dtos.forEach( d -> models.add( CompanyConverter.convertDtoToModel( d ) ) );
		return models;
	}
	
	public static CompanyDTO convertModelToDto( Company model ) {
		if( model == null ) return null;
		List<Long> codes = null;
		if( model.getEmployees() != null ) {
			codes = new ArrayList<Long>();
			for ( Employee employee : model.getEmployees() ) {
				codes.add( employee.getCode() );
			}
		}
		return new CompanyDTO(
					model.getName(),
					model.getProduct(),
					model.getTurnover(),
					codes
				);
	}
	
	public static List<CompanyDTO> convertModelToDto( List<Company> models ){
		if( models == null ) return null;
		List<CompanyDTO> dtos = new ArrayList<>();
		models.forEach( m -> dtos.add( CompanyConverter.convertModelToDto( m ) ) );
		return dtos;
	}

}
