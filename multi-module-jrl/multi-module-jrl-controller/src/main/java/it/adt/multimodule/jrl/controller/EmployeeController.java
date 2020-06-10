package it.adt.multimodule.jrl.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.adt.multimodule.jrl.dto.EmployeeDTO;
import it.adt.multimodule.jrl.exception.EmptyCollectionOrOptionalException;
import it.adt.multimodule.jrl.exception.FailedOperationException;
import it.adt.multimodule.jrl.exception.InvalidArgumentException;
import it.adt.multimodule.jrl.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	private static final Logger LOG = LogManager.getLogger();
	
	
	
	@GetMapping( "/employees" )
	public List<EmployeeDTO> getAll(){
		List<EmployeeDTO> employees = employeeService.getEmployeeList();
		employees.forEach( e -> LOG.info( "Impiegato prelevato: " + e ) );
		return employees;
	}
	
	@GetMapping( "/employees/{code}" )
	public EmployeeDTO getByCode( @PathVariable Long code )
	throws InvalidArgumentException,
		EmptyCollectionOrOptionalException
	{
		EmployeeDTO employee = employeeService.getEmployeeByCode(code);
		LOG.info( "Impiegato prelevato: " + employee );
		return employee;
	}
	
	@GetMapping( "/employees/paged/{num}-{dim}" )
	public List<EmployeeDTO> getPagedAll( @PathVariable Integer num, @PathVariable Integer dim ) 
	throws InvalidArgumentException, EmptyCollectionOrOptionalException
	{
		List<EmployeeDTO> paged = employeeService.getPagedEmpployeeList( num, dim );
		paged.forEach( p -> LOG.info( "Utente [paged] recuperato: " + p ) );
		return paged;
	}
	
	@PostMapping( "/employees" )
	public List<EmployeeDTO> postAll( @RequestBody List<EmployeeDTO> toPost )
	throws InvalidArgumentException, 
		EmptyCollectionOrOptionalException,
		FailedOperationException
	{
			List<EmployeeDTO> employees = employeeService.postEmployeeList( toPost );
			employees.forEach( e -> LOG.info( "Impiegato inserito: " + e ) );
			return employees;
	}
	
	@DeleteMapping( "/employees" )
	public String deleteAll() {
		employeeService.deleteEmployeeList();
		LOG.info( "Lista impiegati completamente cancellata" );
		return "Lista impiegati completamente cancellata";
	}
	
	@DeleteMapping( "/employees/{code}" )
	public String deleteByCode( @PathVariable Long code ) 
	throws InvalidArgumentException, 
		FailedOperationException 
	{
			employeeService.deleteEmployeeByCode(code);
			LOG.info( "Impiegato " + code + " completamente cancellata" );
			return "Impiegato " + code + " completamente cancellata";
	}
	
	@PutMapping( "/employees" )
	public List<EmployeeDTO> updateAll( @RequestBody List<EmployeeDTO> toUpdate ) 
	throws InvalidArgumentException, 
		EmptyCollectionOrOptionalException, 
		FailedOperationException
	{
		List<EmployeeDTO> employees = employeeService.updateEmployeeList( toUpdate );
		employees.forEach( e -> LOG.info( "Impiegato aggiornato: " + e ) );
		return employees;
	}
	
	@PutMapping( "/employees/{code}" )
	public EmployeeDTO updateByCode( @PathVariable Long code, @RequestBody EmployeeDTO toUpdate ) 
	throws InvalidArgumentException 
	{
		EmployeeDTO updated = employeeService.updateEmployeeByCode( code, toUpdate );
		LOG.info( "Impiegato aggiornato: " + updated );
		return updated;
	}	
	
	
	
//	@Autowired
//	private EmployeeRepository employeeRepository;
//	
//	
//	@GetMapping( "/employees" )
//	public List<EmployeeDTO> getAll(){
//		return EmployeeConverter.convertModelToDto( (List<Employee>) employeeRepository.findAll() );
//	}
//	
//	@PostMapping( "/employees" )
//	public List<EmployeeDTO> postAll( @RequestBody List<EmployeeDTO> toPost ){
//		List<Employee> models = EmployeeConverter.convertDtoToModel( toPost );
//		return EmployeeConverter.convertModelToDto( employeeRepository.saveAll( models ) );
//	}
//	
//	@DeleteMapping( "/employees/{code}" )
//	public void delete( @PathVariable Long code ) {
//		employeeRepository.deleteById( code );
//	}
//	
//	@PutMapping( "/employees/{code}" )
//	public EmployeeDTO update( @PathVariable Long code, @RequestBody EmployeeDTO toUpdate ) {
//		Employee model = EmployeeConverter.convertDtoToModel( toUpdate );
//		return EmployeeConverter.convertModelToDto( employeeRepository.save( model ) );
//	}

}




















