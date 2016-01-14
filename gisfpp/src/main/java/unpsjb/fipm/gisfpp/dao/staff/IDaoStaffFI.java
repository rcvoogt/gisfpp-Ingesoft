package unpsjb.fipm.gisfpp.dao.staff;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;

public interface IDaoStaffFI extends DaoGenerico<StaffFI, Integer> {

	public List<PersonaFisica> getListadoPersonas() throws Exception;
}
