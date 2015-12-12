package unpsjb.fipm.gisfpp.util.security;

import java.io.Serializable;

public class RolUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idPersona;
	private String rol;
	private String en;
	private String tabla;
	private Integer idTabla;

	public RolUsuario() {
		super();
	}

	public RolUsuario(Integer idPersona, String rol, String en, String tabla, Integer idTabla) {
		super();
		this.idPersona = idPersona;
		this.rol = rol;
		this.en = en;
		this.tabla = tabla;
		this.idTabla = idTabla;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Integer getIdTabla() {
		return idTabla;
	}

	public void setIdTabla(int idTabla) {
		this.idTabla = idTabla;
	}

}// fin de la clase
