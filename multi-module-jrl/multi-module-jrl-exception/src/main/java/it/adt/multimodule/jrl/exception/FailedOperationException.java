package it.adt.multimodule.jrl.exception;

public class FailedOperationException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String defaultMsg = "OPERAZIONE FALLITA!";
	
	
	public FailedOperationException() {
		super( defaultMsg );
	}
	
	public FailedOperationException( String msg ) {
		super( defaultMsg + " " + msg );
	}
	
}
