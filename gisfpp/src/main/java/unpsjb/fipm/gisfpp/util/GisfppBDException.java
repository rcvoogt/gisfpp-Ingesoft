package unpsjb.fipm.gisfpp.util;

public class GisfppBDException extends GisfppException {

	public GisfppBDException(String mensaje) {
		super(mensaje);
	}
	
	public GisfppBDException (String mensaje, Throwable exc){
		super(mensaje, exc);
	}

}
