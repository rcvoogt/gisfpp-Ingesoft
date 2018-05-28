package unpsjb.fipm.gisfpp.entidades.convocatoria;

import java.util.List;
import java.util.Set;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.util.MiembroExistenteException;

public interface Convocable {

	public List<Convocatoria> getConvocatorias() throws Exception ;
	
	public String getTipoConvocatoria() throws Exception ;
	
	public String getTitulo() throws Exception ;
	
	public void setConvocados(Set<Convocado> nuevosConvocados) throws MiembroExistenteException,Exception;
	
	public boolean isAsignador(PersonaFisica persona) throws Exception;
}
