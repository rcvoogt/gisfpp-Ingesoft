package unpsjb.fipm.gisfpp.entidades.workflow;

import java.util.Date;

public class DefinicionProceso {
	
		private String idDespliegue;
		private Date fecha_despliegue;
		private String idDefinicion;
		private String keyDefinicion;
		private String nombre;
		private String descripcion;
		private String categoria;
		private int version;
		private boolean suspendido;
		
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
		
		public DefinicionProceso(String idDefinicion, String keyDefinicion,
				String nombre, String descripcion, int version, String categoria,
				boolean suspendido, String idDespliegue, Date fecha_despliegue) {
			super();
			this.idDespliegue = idDespliegue;
			this.idDefinicion = idDefinicion;
			this.keyDefinicion = keyDefinicion;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.version = version;
			this.suspendido = suspendido;
			this.fecha_despliegue = fecha_despliegue;
			this.categoria = categoria;
		}

		public String getIdDespliegue() {
			return idDespliegue;
		}

		public void setIdDespliegue(String idDespliegue) {
			this.idDespliegue = idDespliegue;
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

		public String getKeyDefinicion() {
			return keyDefinicion;
		}

		public void setKeyDefinicion(String keyDefinicion) {
			this.keyDefinicion = keyDefinicion;
		}

		public boolean isSuspendido() {
			return suspendido;
		}

		public void setSuspendido(boolean suspendido) {
			this.suspendido = suspendido;
		}

		public Date getFecha_despliegue() {
			return fecha_despliegue;
		}

		public void setFecha_despliegue(Date fecha_despliegue) {
			this.fecha_despliegue = fecha_despliegue;
		}

		public String getCategoria() {
			return categoria;
		}

		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}
		
}
