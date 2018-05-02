package unpsjb.fipm.gisfpp.entidades;

public enum Roles {
	ALUMNO("Alumno"),
	COORDINADOR("Coordinador"), 
	DELEGADO("Delegado de la Facultad"), 
	PROFESOR("Profesor de Asignatura"), 
	INVESTIGADOR("Investigador"),
	JTP("Jefe de Trabajos Practicos"), 
	AUXILIAR("Auxiliar de c�tedra"),
	RESPONSABLE_PROYECTO("Responsable de Proyecto"), 
	MIEMBRO_STAFF_PROYECTO("Miembro Staff de Proyecto"),
	DEMANDANTE_PROYECTO("Demandante de Proyecto"),
	TUTOR_ACADEMICO("Tutor Acad�mico de Isfpp"), 
	TUTOR_EXTERNO("Tutor Externo de Isfpp"),
	PRACTICANTE("Practicante de una Isfpp"),
	ADMINISTRADOR("Administrador de la aplicaci�n"),
	VISITANTE("Visitante ocasional de la aplicaci�n");
	
	private String titulo;

	private Roles(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
}
