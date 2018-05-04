package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum ERespuestaConvocado {
	
	ACEPTADA("Aceptada"), RECHAZADA("Rechazada"), SIN_RESPUESTA("Sin respuesta");
	
	private String respuesta;

	private ERespuestaConvocado(String respuestaRecibida) {
		this.respuesta = respuestaRecibida;
	}
	
	public String getRespuesta(){
		return respuesta;
	}

}
