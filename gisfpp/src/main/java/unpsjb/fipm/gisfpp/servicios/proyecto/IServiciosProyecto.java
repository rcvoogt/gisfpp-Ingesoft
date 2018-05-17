package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;
import java.util.Set;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosProyecto extends IServicioGenerico<Proyecto, Integer> {

	public List<Proyecto> getProyectosActivos() throws Exception;
	/**
	 * Agrega un miembro al staff del proyecto
	 */
	public void agregarMiembroStaff(PersonaFisica persona, Proyecto proyecto);
	/**
	 * Quita un miembro del staff del proyecto
	 */
	public void quitarMiembroStaff(PersonaFisica persona, Proyecto proyecto);

	/**
	 * Agrega varios miembros al staff del proyecto
	 */
	public void agregarMiembrosStaff(Set<PersonaFisica> practicantes, Proyecto proyecto);

}
