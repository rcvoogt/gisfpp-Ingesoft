package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface IServicioPersonaJuridica {

	public Integer nuevaPersonaJuridica(PersonaJuridica persona) throws Exception;

	public void actualizarPersonaJuridica(PersonaJuridica persona) throws Exception;

	public void eliminarPersonaJuridica(PersonaJuridica persona) throws Exception;

	public List<PersonaJuridica> recuperarTodo() throws Exception;

	public PersonaJuridica recuperarxId(Integer Id) throws Exception;

}
