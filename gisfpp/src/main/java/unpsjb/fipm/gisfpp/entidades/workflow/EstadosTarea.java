package unpsjb.fipm.gisfpp.entidades.workflow;

public enum EstadosTarea {

	ASIGNADA("Asignada"), PROPUESTA("Propuesta"), DELEGADA("Delegada"), REALIZADA("Realizada");
	
	private String titulo;

	private EstadosTarea(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
}
