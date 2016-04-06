package unpsjb.fipm.gisfpp.entidades;

public enum Operaciones {
	
	CREAR_PROYECTO("Crear Proyecto"), 
	MODIFICAR_PROYECTO("Modificar Proyecto"),
	MODIFICAR_PROYECTOS("Modificar cualquier Proyecto y sus derivaciones (Sub-Proyectos e Isfpp's"),
	ELIMINAR_PROYECTO("Eliminar Proyecto"),
	ELIMINAR_PROYECTOS("Eliminar cualquier Proyecto"),
	CONSULTAR_PROYECTO("Consultar Proyecto"),
	CREAR_SUBPROYECTO("Crear Sub-Proyecto"),
	ACTUALIZAR_SUBPROYECTO("Actualizar Sub-Proyecto"),
	ELIMINAR_SUBPROYECTO("Eliminar Sub-Proyecto"),
	CONSULTAR_SUBPROYECTO("Consultar Sub-Proyecto"),
	CREAR_ISFPP("Crear Isfpp"),
	ACTUALIZAR_ISFPP("Actualizar Isfpp"),
	ELIMINAR_ISFPP("Eliminar Isfpp"),
	CONSULTAR_ISFPP("Consultar Isfpp"),
	CUD_ENTIDADES_AUX("Crear, Actualizar o Eliminar Entidades Auxiliares"),
	CONSULTAR_ENTIDADES_AUX("Consultar Entidades Auxiliares"),
	MANTENIMIENTO_WORKFLOW("Tareas de mantenimiento en motor de workflow"),
	CONCEDER_REVOCAR_PERMISOS("Conceder o revocar permisos a los diversos roles de la app");
	
	private String titulo;

	private Operaciones(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}
	;

}
