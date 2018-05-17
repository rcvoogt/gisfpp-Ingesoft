package unpsjb.fipm.gisfpp.entidades.convocatoria;

public enum TipoConvocatoria {
	PROYECTO("Convocatoria para staff de proyecto"), SUBPROYECTO("Convocatoria para staff de subproyecto"), ISFPP("Convocatoria para practicantes de ISFPP");

	private final String descripcion;

	private TipoConvocatoria(String desc) {
		this.descripcion = desc;
	}

	public String getDescripcion() {
		return descripcion;
	}

}// fin del enum
