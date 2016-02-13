package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum ERolStaffIsfpp {
	
	TUTOR_ACADEMICO("Tutor Académico"), TUTOR_EXTERNO("Tutor Externo");
	
	private String titulo;

	private ERolStaffIsfpp(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}

}
