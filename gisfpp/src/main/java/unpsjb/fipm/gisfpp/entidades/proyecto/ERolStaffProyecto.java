package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum ERolStaffProyecto {
	
	RESPONSABLE("Responsable de Proyecto"), MIEMBRO_STAFF("Miembro Staff");
	
	private String titulo;

	private ERolStaffProyecto(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}

}
