package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaJuridica;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioPJ extends IServicioGenerico<PersonaJuridica, Integer> {

	
	/**
	 * Devuelve el listado de organizaciones cuyo nombre (o razon social) contienen el patron de busqueda 
	 * pasado como parametro
	 * @param patronNombre (String): patron de busqueda
	 * @return Listado de Organizaciones (List<PersonaJuridica>)
	 * @throws Exception
	 */
	public List<PersonaJuridica> getxNombre(String patronNombre) throws Exception;

	
	/**
	 * 
	 * @param patronValor (String): Valor del CUIT a buscar
	 * @return (List<PersonaFisica>) Listado de organizaciones resultado de la busqueda.
	 * @throws Exception
	 */
	public List<PersonaJuridica> getxCuit(String patronValor) throws Exception;
	
}
