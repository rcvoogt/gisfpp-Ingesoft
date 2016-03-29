package unpsjb.fipm.gisfpp.servicios.workflow.entidades;

public class DefinicionProceso {
	
		private String idDefinicion;
		private String nombre;
		private String descripcion;
		private int version;
		
		public DefinicionProceso() {
			super();
		}

		public DefinicionProceso(String idDefinicion, String nombre, String descripcion,
				int version) {
			super();
			this.idDefinicion = idDefinicion;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.version = version;
		}

		public String getIdDefinicion() {
			return idDefinicion;
		}

		public void setIdDefinicion(String idDefinicion) {
			this.idDefinicion = idDefinicion;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
		
}
