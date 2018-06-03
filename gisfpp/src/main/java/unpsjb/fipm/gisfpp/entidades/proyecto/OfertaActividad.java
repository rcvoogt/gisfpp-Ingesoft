package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;

public class OfertaActividad implements Comparable{
	
	private Proyecto proyecto;
	private SubProyecto subProyecto;
	
	public OfertaActividad() {
		// TODO Auto-generated constructor stub
	}
	
	public OfertaActividad(Proyecto p, SubProyecto s) {
		this.proyecto = p;
		this.subProyecto = s;
		// TODO Auto-generated constructor stub
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}
	
	public SubProyecto getSubProyecto() {
		return subProyecto;
	}
	
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public void setSubProyecto(SubProyecto subProyecto) {
		this.subProyecto = subProyecto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((proyecto == null) ? 0 : proyecto.hashCode());
		result = prime * result + ((subProyecto == null) ? 0 : subProyecto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfertaActividad other = (OfertaActividad) obj;
		if (proyecto == null) {
			if (other.proyecto != null)
				return false;
		} else if (!proyecto.equals(other.proyecto))
			return false;
		if (subProyecto == null) {
			if (other.subProyecto != null)
				return false;
		} else if (!subProyecto.equals(other.subProyecto))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		OfertaActividad aux = (OfertaActividad) o;
		return this.getProyecto().getFecha_fin().compareTo(aux.getProyecto().getFecha_fin());
	}
}
