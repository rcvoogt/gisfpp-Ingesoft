package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum EstadoProyecto {

	GENERADO("Generado"), ACTIVO("Activo"), SUSPENDIDO("Suspendido Temporalmente"), FINALIZADO("Finalizado");

	private String descripcion;

	private EstadoProyecto(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
