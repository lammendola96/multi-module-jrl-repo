package it.adt.multimodule.jrl.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.adt.multimodule.jrl.converter.CompanyConverter;
import it.adt.multimodule.jrl.dto.CompanyDTO;
import it.adt.multimodule.jrl.entity.Company;
import it.adt.multimodule.jrl.exception.EmptyCollectionOrOptionalException;
import it.adt.multimodule.jrl.exception.FailedOperationException;
import it.adt.multimodule.jrl.exception.InvalidArgumentException;
import it.adt.multimodule.jrl.repository.CompanyRepository;

@Service
@Transactional
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	private static final Logger LOG = LogManager.getLogger();
	
	
	//GET
	public List<CompanyDTO> getCompanyList() {
		LOG.debug( "getCompanyList()" );
		List<Company> got = ( List<Company> ) companyRepository.findAll();
		return CompanyConverter.convertModelToDto( got );
	}
	
	public CompanyDTO getCompanyByName( String name ) 
	throws InvalidArgumentException, EmptyCollectionOrOptionalException{
		LOG.debug( "getCompanyByName()" );
		if( name==null ) throw new InvalidArgumentException( "[GET] - code=null" );
		Optional<Company> got = companyRepository.findById( name ); 
		if( !got.isPresent() ) throw new EmptyCollectionOrOptionalException( "[GET] Azenda con nome=" + name + " NON ESISTENTE!" );
		return CompanyConverter.convertModelToDto( got.get() );
	}

	
	//POST
	public List<CompanyDTO> postCompanyList( List<CompanyDTO> toPostDto ) 
	throws InvalidArgumentException, 
	EmptyCollectionOrOptionalException,
	FailedOperationException 
	{
		LOG.debug( "postCompanyList()" );
		if( toPostDto==null ) throw new InvalidArgumentException( "[POST] - (lista) toPostDto=null" );
		if( toPostDto.isEmpty() ) throw new EmptyCollectionOrOptionalException( "[POST] toPostDto.isEmpty()" );
		List<Company> toPost = CompanyConverter.convertDtoToModel( toPostDto );
		toPost = companyRepository.saveAll( toPost );
		if( toPost.isEmpty() ) throw new FailedOperationException( "[POST] - risultato di saveAll è empty" );
		toPostDto = CompanyConverter.convertModelToDto( toPost );
		return toPostDto;
		
	}

	
	//DELETE
	public void deleteCompanyList() {
		LOG.debug( "deleteCompanyList()" );
		companyRepository.deleteAll();
	}
	
	public void deleteCompanyByName( String name ) 
	throws InvalidArgumentException, 
	FailedOperationException 
	{
		LOG.debug( "deleteCompanyByName()" );
		if( name==null ) throw new InvalidArgumentException( "[DELETE] - code=null" );
		try {
			companyRepository.deleteById( name );
		} catch (Exception e) {
			// TODO: handle exception
			throw new FailedOperationException( "[DELETE] Errore in cancellazione azienda=" + name + ". Caller Exception Message: " + e.getMessage() );
		}
	}
	
	
	//UPDATE
	public List<CompanyDTO> updateCompanyList( List<CompanyDTO> toUpdateDto ) 
	throws InvalidArgumentException, 
	EmptyCollectionOrOptionalException, 
	FailedOperationException 
	{
		LOG.debug( "updateCompanyList()" );
		if( toUpdateDto==null ) throw new InvalidArgumentException( "[UPDATE] (lista) toUpdateDto=null" );
		if( toUpdateDto.isEmpty() ) throw new EmptyCollectionOrOptionalException( "[UPDATE] toUpdateDto.isEmpty()" );
		try {
			for (CompanyDTO companyDTO : toUpdateDto) {
				this.updateCompanyByName( companyDTO.getName(), companyDTO );
			}
		}
		catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			throw new FailedOperationException( "[UPDATE] Impossibile eseguire il commit. Caller Exception Message: " + e.getMessage() );
		}
		return toUpdateDto;
	}
	
	public CompanyDTO updateCompanyByName( String name, CompanyDTO toUpdateDto )
	throws InvalidArgumentException,
	FailedOperationException
	{
		LOG.debug( "updateCompanyByName()" );
		if( name==null ) throw new InvalidArgumentException( "[UPDATE] code=null" );
		if( toUpdateDto==null ) throw new InvalidArgumentException( "[UPDATE] toUpdateDto=null" );
		toUpdateDto.setName( name );
		Company toUpdate = CompanyConverter.convertDtoToModel( toUpdateDto );
		toUpdate = companyRepository.save( toUpdate );
		toUpdateDto = CompanyConverter.convertModelToDto( toUpdate );
		return toUpdateDto;
	}
	
}



































































