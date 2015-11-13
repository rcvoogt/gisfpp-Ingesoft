package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;

public interface ServicioPersonaJuridica {

	public Integer nuevaPersonaJuridica(PersonaJuridica persona) throws Exception;

	public void actualizarPersonaJuridica(PersonaJuridica persona) throws Exception;

	public void eliminarPersonaJuridica(PersonaJuridica persona) throws Exception;

	public List<PersonaJuridica> todoPersonaJuridica() throws Exception;
}
