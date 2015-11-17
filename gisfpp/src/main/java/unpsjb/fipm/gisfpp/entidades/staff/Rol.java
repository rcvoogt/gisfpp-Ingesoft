package unpsjb.fipm.gisfpp.entidades.staff;

public enum Rol {
	COORDINADOR_EXTENSIONES("Coordinador de Extensiones"), DELEGADO("Delegado Academico"), PROFESOR("Profesor"), JTP(
			"Jefe de Trabajo Practico"), AUXILIAR_ASIGNATURA("Auxiliar de Asignatura");

	private String descripcion;

	private Rol(String arg) {
		this.descripcion = arg;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
