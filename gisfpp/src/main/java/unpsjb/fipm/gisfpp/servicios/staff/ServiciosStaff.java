package unpsjb.fipm.gisfpp.servicios.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.staff.IDaoStaffFI;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;

@Service("servStaffFI")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosStaff implements IServiosStaff {

	private IDaoStaffFI dao;

	@Override
	@Transactional(readOnly = false)
	public int nuevaAsociacionStaff(StaffFI asociacion) throws Exception {
		try {
			return dao.crear(asociacion);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void editarAsociacionStaff(StaffFI asociacion) throws Exception {
		try {
			dao.actualizar(asociacion);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void eliminarAsociacionStaff(StaffFI asociacion) throws Exception {
		try {
			dao.eliminar(asociacion);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<StaffFI> recuperarTodoStaff() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public StaffFI getStaffFI(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}

	}

	@Autowired(required = true)
	public void setDao(IDaoStaffFI arg) {
		dao = arg;
	}

}// fin de la clase
