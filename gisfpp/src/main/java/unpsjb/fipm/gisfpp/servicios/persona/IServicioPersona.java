package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface IServicioPersona {

	public Integer nuevaPersona(Persona persona) throws Exception;

	public void actualizarPersona(Persona persona) throws Exception;

	public void eliminarPersona(Persona persona) throws Exception;

	public List<Persona> recuperarTodo() throws Exception;

	public Persona recuperarxId(Integer id) throws Exception;

	public List<PersonaFisica> recuperarSoloPF() throws Exception;

	public List<PersonaJuridica> recuperarSoloPJ() throws Exception;

}
