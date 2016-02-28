package unpsjb.fipm.gisfpp.servicios.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.staff.IDaoStaffFI;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;

@Service("servStaffFI")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosStaff implements IServiciosStaffFI {

	private IDaoStaffFI dao;

	
	@Autowired(required = true)
	public void setDao(IDaoStaffFI arg) {
		dao = arg;
	}


	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public Integer persistir(StaffFI instancia) throws Exception {
		return dao.crear(instancia);
	}


	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void editar(StaffFI instancia) throws Exception {
		dao.actualizar(instancia);		
	}


	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void eliminar(StaffFI instancia) throws Exception {
		dao.eliminar(instancia);		
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public StaffFI getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<StaffFI> getListado() throws Exception {
		return dao.recuperarTodo();
	}


	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<PersonaFisica> getListadoStaffPersonas() throws Exception {
		return dao.getListadoPersonas();
	}
	
	

}// fin de la clase
