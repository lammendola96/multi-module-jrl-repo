package it.adt.multimodule.jrl.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.adt.multimodule.jrl.converter.EmployeeConverter;
import it.adt.multimodule.jrl.dto.EmployeeDTO;
import it.adt.multimodule.jrl.entity.Employee;
import it.adt.multimodule.jrl.exception.EmptyCollectionOrOptionalException;
import it.adt.multimodule.jrl.exception.FailedOperationException;
import it.adt.multimodule.jrl.exception.InvalidArgumentException;
import it.adt.multimodule.jrl.repository.EmployeeRepository;


@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	private static final Logger LOG = LogManager.getLogger();
	
	
	//GET
	public List<EmployeeDTO> getEmployeeList() {
		LOG.debug( "getEmployeeList()" );
		List<Employee> got = ( List<Employee> ) employeeRepository.findAll();
		return EmployeeConverter.convertModelToDto( got );
	}
	
	public EmployeeDTO getEmployeeByCode( Long code ) 
	throws InvalidArgumentException, 
		EmptyCollectionOrOptionalException 
	{
		LOG.debug( "getEmployeeByCode()" );
		if( code==null ) throw new InvalidArgumentException( "[GET] - code=null" );
		Optional<Employee> got = employeeRepository.findById( code );
		if( !got.isPresent() ) throw new EmptyCollectionOrOptionalException( "[GET] Impiegato con matricola=" + code + " NON ESISTENTE!" );
		return EmployeeConverter.convertModelToDto( got.get() );
	}

	//GET - paged
	public List<EmployeeDTO> getPagedEmpployeeList( Integer num, Integer dim )
	throws InvalidArgumentException,
		EmptyCollectionOrOptionalException
	{
		LOG.debug( "getEmployeeList()" );
		if( num==null || dim==null ) throw new InvalidArgumentException( "[GET] Non sono stati inseriti correttamente i valori di 'num' e 'dim' (= null) " );
		Pageable pageable = PageRequest.of( num, dim );
		Page<Employee> employeePage = employeeRepository.findAll(pageable);
		if( employeePage==null ) throw new EmptyCollectionOrOptionalException( "[GET] Recupero della pagina di impiegati desiderata FALLITO (=null)" );
		return EmployeeConverter.convertModelToDto( employeePage.getContent() );
	}
	
	//POST
	public List<EmployeeDTO> postEmployeeList( List<EmployeeDTO> toPostDto ) 
	throws InvalidArgumentException, 
		EmptyCollectionOrOptionalException,
		FailedOperationException 
	{
		LOG.debug( "postEmployeeList()" );
		if( toPostDto==null ) throw new InvalidArgumentException( "[POST] - (lista) toPostDto=null" );
		if( toPostDto.isEmpty() ) throw new EmptyCollectionOrOptionalException( "[POST] toPostDto.isEmpty()" );
		List<Employee> toPost = EmployeeConverter.convertDtoToModel( toPostDto );
		toPost = employeeRepository.saveAll( toPost );
		if( toPost.isEmpty() ) throw new FailedOperationException( "[POST] - risultato di saveAll è empty" );
		toPostDto = EmployeeConverter.convertModelToDto( toPost );
		return toPostDto;
	}
	
	
	//DELETE
	public void deleteEmployeeList() {
		LOG.debug( "deleteEmployeeList()" );
		employeeRepository.deleteAll();
	}
	
	public void deleteEmployeeByCode( Long code ) 
	throws InvalidArgumentException, 
		FailedOperationException 
	{
		LOG.debug( "deleteEmployeeByCode()" );
		if( code==null ) throw new InvalidArgumentException( "[DELETE] - code=null" );
		try {
			employeeRepository.deleteById(code);
		} catch (Exception e) {
			// TODO: handle exception
			throw new FailedOperationException( "[DELETE] Errore in cancellazione matricola=" + code + ". Caller Exception Message: " + e.getMessage() );
		}
	}
	
	
	//UPDATE
	public List<EmployeeDTO> updateEmployeeList( List<EmployeeDTO> toUpdateDto ) 
	throws InvalidArgumentException, 
		EmptyCollectionOrOptionalException, 
		FailedOperationException 
	{
		LOG.debug( "updateEmployeeList()" );
		if( toUpdateDto==null ) throw new InvalidArgumentException( "[UPDATE] (lista) toUpdateDto=null" );
		if( toUpdateDto.isEmpty() ) throw new EmptyCollectionOrOptionalException( "[UPDATE] toUpdateDto.isEmpty()" );
		try {
			for (EmployeeDTO employeeDTO : toUpdateDto) {
				this.updateEmployeeByCode( employeeDTO.getCode(), employeeDTO );
			}
		}
		catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			throw new FailedOperationException( "[UPDATE] Impossibile eseguire il commit. Caller Exception Message: " + e.getMessage() );
		}
		return toUpdateDto;
	}
	
	public EmployeeDTO updateEmployeeByCode( Long code, EmployeeDTO toUpdateDto ) 
	throws InvalidArgumentException 
	{
		LOG.debug( "updateEmployeeByCode()" );
		if( code==null ) throw new InvalidArgumentException( "[UPDATE] code=null" );
		if( toUpdateDto==null ) throw new InvalidArgumentException( "[UPDATE] toUpdateDto=null" );
		toUpdateDto.setCode( code );
		Employee toUpdate = EmployeeConverter.convertDtoToModel( toUpdateDto );
		toUpdate = employeeRepository.save( toUpdate );
		toUpdateDto = EmployeeConverter.convertModelToDto( toUpdate );
		return toUpdateDto;
	}
	
}






















































