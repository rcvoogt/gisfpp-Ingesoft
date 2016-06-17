package unpsjb.fipm.gisfpp.entidades.workflow;

import java.util.Date;
import java.util.List;

public class InstanciaProceso {

	private DefinicionProceso definicion;
	private List<InstanciaActividad> actividades;
	private String idInstancia;
	private String keyBusiness;
	private String titulo;
	private String iniciador;
	private Date inicia;
	private Date finaliza;
	private boolean suspendido;
	private boolean finalizada;
	
	public InstanciaProceso() {
		super();
	}
	
	public InstanciaProceso(DefinicionProceso definicion, String idInstancia,
			String keyBusiness, String titulo, String iniciador,	Date inicia, Date finaliza, 
				boolean suspendido,	boolean finalizada, List<InstanciaActividad> actividades) {
		super();
		this.definicion = definicion;
		this.idInstancia = idInstancia;
		this.keyBusiness = keyBusiness;
		this.titulo = (titulo == null)?"Sin definir":titulo;
		this.iniciador = iniciador;
		this.inicia = inicia;
		this.finaliza = finaliza;
		this.suspendido = suspendido;
		this.finalizada = finalizada;
		this.actividades = actividades;
	}

	public DefinicionProceso getDefinicion() {
		return definicion;
	}

	public void setDefinicion(DefinicionProceso definicion) {
		this.definicion = definicion;
	}

	public String getIdInstancia() {
		return idInstancia;
	}

	public void setIdInstancia(String idInstancia) {
		this.idInstancia = idInstancia;
	}

	public String getKeyBusiness() {
		return keyBusiness;
	}

	public void setKeyBusiness(String keyBusiness) {
		this.keyBusiness = keyBusiness;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = (titulo == null)?"Sin definir":titulo;
	}

	public boolean isSuspendido() {
		return suspendido;
	}

	public void setSuspendido(boolean suspendido) {
		this.suspendido = suspendido;
	}

	public String getIniciador() {
		return iniciador;
	}

	public void setIniciador(String iniciador) {
		this.iniciador = iniciador;
	}

	public Date getInicia() {
		return inicia;
	}

	public void setInicia(Date inicia) {
		this.inicia = inicia;
	}

	public Date getFinaliza() {
		return finaliza;
	}

	public void setFinaliza(Date finaliza) {
		this.finaliza = finaliza;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	public List<InstanciaActividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<InstanciaActividad> actividades) {
		this.actividades = actividades;
	}

	public long getDuracion(){
		if (inicia!=null && finaliza!=null) {
			return (finaliza.getTime() - inicia.getTime());
		}
		return 0;
	}
	
}
