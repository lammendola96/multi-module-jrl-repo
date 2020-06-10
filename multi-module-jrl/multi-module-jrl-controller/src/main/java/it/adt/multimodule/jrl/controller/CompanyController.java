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

import it.adt.multimodule.jrl.dto.CompanyDTO;
import it.adt.multimodule.jrl.exception.EmptyCollectionOrOptionalException;
import it.adt.multimodule.jrl.exception.FailedOperationException;
import it.adt.multimodule.jrl.exception.InvalidArgumentException;
import it.adt.multimodule.jrl.service.CompanyService;

@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	private static final Logger LOG = LogManager.getLogger();
	
	
	//GET
	@GetMapping( "/companies" )
	public List<CompanyDTO> getAll(){
		List<CompanyDTO> companies = companyService.getCompanyList();
		if (companies.isEmpty()) {
			LOG.warn("NESSUNA AZIENDA ESISTENTE!");
			return companies;
		}
		companies.forEach(c -> LOG.info("Azienda prelevata: " + c));
		return companies;
	}
	
	@GetMapping( "/companies/{name}" )
	public CompanyDTO getByName( @PathVariable String name ) 
			throws InvalidArgumentException, EmptyCollectionOrOptionalException  
	{
		CompanyDTO company = companyService.getCompanyByName(name);
		LOG.info("Azienda prelevata: " + company);
		return company;
	}
	
	
	//POST
	@PostMapping( "/companies" )
	public List<CompanyDTO> postAll( @RequestBody List<CompanyDTO> toPost ) 
			throws InvalidArgumentException, EmptyCollectionOrOptionalException, FailedOperationException{
		List<CompanyDTO> posted = companyService.postCompanyList( toPost );
		posted.forEach( c -> LOG.info( "Azienda inserita: " + c ) );
		return posted;
	}
	
	
	//DELETE
	@DeleteMapping( "/companies" )
	public String deleteAll() {
		companyService.deleteCompanyList();
		LOG.info( "Lista di aziende completamente cancellata" );
		return "Lista di aziende completamente cancellata";
	}
	
	@DeleteMapping( "/companies/{name}" )
	public String deleteByName( @PathVariable String name ) 
			throws InvalidArgumentException, FailedOperationException {
		companyService.deleteCompanyByName( name );
		LOG.info( "Azienda " + name + " cancellata" );
		return "Azienda " + name + " cancellata";
	}
	
	
	//UPDATE
	@PutMapping( "/companies" )
	public List<CompanyDTO> updateAll( @RequestBody List<CompanyDTO> toUpdate ) 
			throws InvalidArgumentException, EmptyCollectionOrOptionalException, FailedOperationException{
		List<CompanyDTO> updated = companyService.updateCompanyList( toUpdate );
		updated.forEach( c -> LOG.info( "Azienda aggiornata: " + c ) );
		return updated;
	}
	
	@PutMapping( "/companies/{name}" )
	public CompanyDTO updateByName( @PathVariable String name, @RequestBody CompanyDTO toUpdate ) 
			throws InvalidArgumentException, FailedOperationException {
		CompanyDTO updated = companyService.updateCompanyByName( name, toUpdate );
		LOG.info( "Azienda aggiornata: " + updated );
		return updated;
	}

//	@Autowired
//	private CompanyRepository companyRepository;
//	
//	
//	@GetMapping( "/companies" )
//	public List<CompanyDTO> getAll(){
//		return CompanyConverter.convertModelToDto( (List<Company>) companyRepository.findAll() );
//	}
//	
//	@PostMapping( "/companies" )
//	public List<CompanyDTO> postAll( @RequestBody List<CompanyDTO> toPost ){
//		List<Company> models = CompanyConverter.convertDtoToModel( toPost );
//		return CompanyConverter.convertModelToDto( companyRepository.saveAll( models ) );
//	}
//	
//	@DeleteMapping( "/companies/{name}" )
//	public void delete( @PathVariable String name ) {
//		companyRepository.deleteById( name );
//	}
//	
//	@PutMapping( "/companies/{name}" )
//	public CompanyDTO update( @PathVariable String name, @RequestBody CompanyDTO toUpdate ) {
//		Company model = CompanyConverter.convertDtoToModel( toUpdate );
//		return CompanyConverter.convertModelToDto( companyRepository.save( model ) );
//	}
	
}
