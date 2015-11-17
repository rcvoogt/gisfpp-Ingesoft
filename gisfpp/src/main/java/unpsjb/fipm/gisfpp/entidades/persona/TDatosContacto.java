package unpsjb.fipm.gisfpp.entidades.persona;

public enum TDatosContacto {
	EMAIL("E-Mail"), TEL_FIJO("Nº Linea Fija"), TEL_CELULAR("N° Celular"), FAX("Nº Fax"), RED_SOCIAL(
			"Link cuenta Red Social "), WEB("Link Web");

	private final String titulo;

	private TDatosContacto(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

}
