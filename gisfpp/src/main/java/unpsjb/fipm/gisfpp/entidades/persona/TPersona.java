package unpsjb.fipm.gisfpp.entidades.persona;

public enum TPersona {
	PF("Persona Física"), PJ("Persona Jurídica");

	private String descripcion;

	private TPersona(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
