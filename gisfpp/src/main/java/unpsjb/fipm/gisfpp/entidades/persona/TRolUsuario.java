package unpsjb.fipm.gisfpp.entidades.persona;

public enum TRolUsuario {
	ADMIN("Administrador de la App"), COORD_EXT("Coordinador de Extensiones"), RESP_PROY(
			"Responsable de Proyecto"), RESP_SUBPROY("Responsable de Sub-Proyecto"), ALUMNO("Alumno");

	private String titulo;

	private TRolUsuario(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

}
