package unpsjb.fipm.gisfpp.entidades.staff;

public enum ECargosStaffFi {
	COORDINADOR("Coordinador"), DELEGADO("Delegado de la Facultad"), PROFESOR("Profesor de Asignatura"), INVESTIGADOR("Investigador"),
		JTP("Jefe de Trabajos Practicos"), AUXILIAR("Auxiliar"), ALUMNO("Alumno");

	private String titulo;

	private ECargosStaffFi(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

}
