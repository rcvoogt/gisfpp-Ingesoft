package unpsjb.fipm.gisfpp.entidades.convocatoria;

import java.util.List;

public interface Convocable {

	public List<Convocatoria> getConvocatorias() throws Exception ;
	
	public String getTipoConvocatoria() throws Exception ;
	
	public String getTitulo() throws Exception ;
	
}