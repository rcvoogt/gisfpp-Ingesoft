package unpsjb.fipm.gisfpp.servicios.staff;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServiciosStaffFI extends IServicioGenerico<StaffFI, Integer> {

	/**
	 * Este metodo devuelve un listado de objetos PersonaFisica que pertenecen al Staff de la FI.
	 * @return List<PersonaFisica>: Listado de Personas miembros del Staff-FI
	 * @throws Exception
	 */
	public List<PersonaFisica> getListadoStaffPersonas() throws Exception;
	
	public List<StaffFI> getMiembroPorRol(ECargosStaffFi rol) throws Exception;
	
}
