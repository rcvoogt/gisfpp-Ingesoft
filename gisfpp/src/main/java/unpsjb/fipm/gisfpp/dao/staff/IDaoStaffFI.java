package unpsjb.fipm.gisfpp.dao.staff;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;

public interface IDaoStaffFI extends DaoGenerico<StaffFI, Integer> {

	public List<PersonaFisica> getListadoPersonas() throws Exception;
	
	public List<StaffFI> getMiembroPorRol(ECargosStaffFi rol) throws Exception;
	
	public void actualizarOguardar(StaffFI instancia);

	public boolean existe(String legajo);

}
