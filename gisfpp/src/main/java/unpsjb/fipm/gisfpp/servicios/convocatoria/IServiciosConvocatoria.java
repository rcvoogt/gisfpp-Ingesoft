package unpsjb.fipm.gisfpp.servicios.convocatoria;

import java.util.List;
import java.util.Set;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;
import unpsjb.fipm.gisfpp.util.MiembroExistenteException;

public interface IServiciosConvocatoria extends IServicioGenerico<Convocatoria, Integer> {

	public List<Convocatoria> getListado() throws Exception;
	
	public Isfpp getIsfpp(Integer idConvocatoria) throws Exception;
	
	public List<Convocado> getConvocados(Integer idConvocatoria) throws Exception;
	
	public int getCantidadConvocados(Integer idConvocatoria) throws Exception;
	
	public List<Convocado> getConvocadosAceptadores(Integer idConvocatoria) throws Exception;

	public void asignar(Convocado personaAcepto) throws Exception;

	/**
	 * 
	 * @param persona
	 * @param convocable
	 * @return true si la persona esta asignada al convocable
	 */
	public boolean isAsignado(PersonaFisica persona, Convocable convocable) throws Exception;

	public boolean asignar(Set<Convocado> practicantes, Convocable convocable) throws MiembroExistenteException,Exception;	
}
