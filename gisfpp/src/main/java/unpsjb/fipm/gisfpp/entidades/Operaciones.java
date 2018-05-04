package unpsjb.fipm.gisfpp.entidades;

public enum Operaciones {
	
	/**
	 * Listado de operaciones disponibles de la app. De ser necesario agregar mas operaciones, las mismas deben ser agregadas
	 * al final del listado, ya que en la BD estas son referenciadas por su posición en este listado (Enum)
	 */
	CREAR_PROYECTO("Crear Proyecto"), 
	MODIFICAR_PROYECTO("Modificar un Proyecto en particular y sus derivaciones (Sub-Proyectos e Isfpp's)"),
	MODIFICAR_PROYECTOS("Modificar cualquier Proyecto y sus derivaciones (Sub-Proyectos e Isfpp's)"),
	ELIMINAR_PROYECTO("Eliminar un Proyecto en particular"),
	ELIMINAR_PROYECTOS("Eliminar cualquier Proyecto"),
	CONSULTAR_PROYECTO("Consultar un Proyecto en particular"),
	CONSULTAR_PROYECTOS("Consultar cualquier Proyecto y sus derivaciones (Sub-Proyectos e Isfpp's)"),
	CREAR_SUBPROYECTO("Crear un Sub-Proyecto de un Proyecto en particular"),
	MODIFICAR_SUBPROYECTO("Modificar un Sub-Proyecto de un Proyecto en particular"),
	ELIMINAR_SUBPROYECTO("Eliminar un Sub-Proyecto de un Proyecto en particular"),
	CONSULTAR_SUBPROYECTO("Consultar un Sub-Proyecto de un Proyecto en particular"),
	CREAR_ISFPP("Crear Isfpp"),
	MODIFICAR_ISFPP("Modificar una Isfpp en particular"),
	ELIMINAR_ISFPP("Eliminar una Isfpp en particular"),
	CONSULTAR_ISFPP("Consultar una Isfpp"),
	CUD_ENTIDADES_AUX("Crear, Actualizar o Eliminar Entidades Auxiliares"),
	CONSULTAR_ENTIDADES_AUX("Consultar Entidades Auxiliares"),
	MANTENIMIENTO_WORKFLOW("Tareas de mantenimiento en motor de workflow"),
	CONCEDER_REVOCAR_PERMISOS("Conceder o revocar permisos a los diversos roles de la app"),
	CREAR_CONVOCATORIA("Crear convocatoria"),
	ACEPTAR_CONVOCATORIA("Aceptar convocatoria"),
	SELECCIONAR_CONVOCADOS("Seleccionar convocados")
	;
	
	
	private String titulo;

	private Operaciones(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}
	;

}
