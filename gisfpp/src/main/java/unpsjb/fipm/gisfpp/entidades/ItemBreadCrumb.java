package unpsjb.fipm.gisfpp.entidades;

import java.util.HashMap;
import java.util.Map;

public class ItemBreadCrumb {
	private String zul;
	private String titulo;
	private Map<String, Object> argsLlamada;
	

	public ItemBreadCrumb(String zul, String titulo, Map<String, Object> argsLlamada) {
		super();
		this.zul = zul;
		this.titulo = titulo;
		this.argsLlamada = argsLlamada;
	}


	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getZul() {
		return zul;
	}
	public Map<String, Object> getArgsLlamada() {
		return argsLlamada;
	}
}
