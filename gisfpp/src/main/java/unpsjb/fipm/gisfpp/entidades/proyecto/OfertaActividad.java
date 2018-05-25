package unpsjb.fipm.gisfpp.entidades.proyecto;

public class OfertaActividad {
	
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

}
