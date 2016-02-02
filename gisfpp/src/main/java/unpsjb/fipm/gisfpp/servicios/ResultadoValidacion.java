package unpsjb.fipm.gisfpp.servicios;

public class ResultadoValidacion {
	
	private boolean valido;
	private String mensaje;
	
	public ResultadoValidacion() {
		super();
		valido = false;
		mensaje ="";
	}

	public ResultadoValidacion(boolean resultado, String mensaje) {
		super();
		this.valido = resultado;
		this.mensaje = mensaje;
	}


	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean resultado) {
		this.valido = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
