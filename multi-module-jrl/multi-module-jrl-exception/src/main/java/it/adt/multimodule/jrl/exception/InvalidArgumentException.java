package it.adt.multimodule.jrl.exception;

public class InvalidArgumentException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String defaultMsg = "ERRORE PASSAGGIO ARGOMENTI: ";
	
	
	public InvalidArgumentException( String msg ) {
		super( defaultMsg + " " + msg );
	}
	
}