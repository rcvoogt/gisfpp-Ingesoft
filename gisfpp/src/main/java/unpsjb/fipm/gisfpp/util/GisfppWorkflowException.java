package unpsjb.fipm.gisfpp.util;

@SuppressWarnings("serial")
public class GisfppWorkflowException extends GisfppException {

	public GisfppWorkflowException(String mensaje) {
		super(mensaje);
	}
	
	public GisfppWorkflowException(String mensaje, Throwable exc){
		super(mensaje, exc);
	}

}
