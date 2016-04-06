package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum ERolStaffProyecto {
	
	RESPONSABLE_PROYECTO("Responsable de Proyecto"), MIEMBRO_STAFF_PROYECTO("Miembro Staff de Proyecto");
	
	private String titulo;

	private ERolStaffProyecto(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}

}
