package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;

public interface IServiciosPersonaFisica {

	public Integer nuevaPersonaFisica(PersonaFisica persona) throws Exception;

	public void actualizarPersonaFisica(PersonaFisica persona) throws Exception;

	public void eliminarPersonaFisica(PersonaFisica persona) throws Exception;

	public List<PersonaFisica> recuperarTodo() throws Exception;

	public PersonaFisica recuperarxId(Integer id) throws Exception;

}
