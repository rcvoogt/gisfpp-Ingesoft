package unpsjb.fipm.gisfpp.entidades.persona;

public enum TIdentificador {
	DNI("DNI"), CUIL("CUIL"), CUIT("CUIT"), MATRICULA("N° Matrícula"), LEGAJO("N° de Legajo");

	private final String descripcion;

	private TIdentificador(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
