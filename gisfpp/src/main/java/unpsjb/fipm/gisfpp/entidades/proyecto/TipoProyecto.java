package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum TipoProyecto {
	INTERNO("Interno"), EMPRESA("Empresa"), INVESTIGACION("Investigación"), EXTENSION("Extensión");

	private final String descripcion;

	private TipoProyecto(String desc) {
		this.descripcion = desc;
	}

	public String getDescripcion() {
		return descripcion;
	}

}// fin del enum
