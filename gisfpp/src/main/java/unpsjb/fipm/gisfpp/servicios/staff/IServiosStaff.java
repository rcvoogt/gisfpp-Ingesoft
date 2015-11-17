package unpsjb.fipm.gisfpp.servicios.staff;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;

public interface IServiosStaff {

	public void nuevaAsociacionStaff(StaffFI asociacion) throws Exception;

	public void editarAsociacionStaff(StaffFI asociacion) throws Exception;

	public void eliminarAsociacionStaff(StaffFI asociacion) throws Exception;

	public List<StaffFI> recuperarTodoStaff() throws Exception;
}
