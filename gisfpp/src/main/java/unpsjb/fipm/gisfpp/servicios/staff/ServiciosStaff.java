package unpsjb.fipm.gisfpp.servicios.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.staff.IDaoStaffFI;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioPF;

@Service("servStaffFI")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosStaff implements IServiciosStaffFI {

	private IDaoStaffFI dao;
	private IServicioPF servPF;
	
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

	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<StaffFI> getMiembroPorRol(ECargosStaffFi rol) throws Exception {
		return dao.getMiembroPorRol(rol);
	}


	@Override
	@Transactional
	public StaffFI getMiembro(PersonaFisica personaFisica) throws Exception{
		List<StaffFI> miembros = getListado();
		for(StaffFI miembro: miembros) {
			if(miembro.getMiembro().equals(personaFisica))
				return miembro;
		}
		return null;
	}


	@Override
	@Transactional
	public int actualizarOguardar(StaffFI staff) throws DataAccessException, Exception {
		if(existe(staff)) {
			dao.actualizar(staff);
			return staff.getId();
		}
		dao.crear(staff);
		return staff.getId();
	}

	@Override
	@Transactional
	public boolean existe(StaffFI staff) throws Exception {
		List<PersonaFisica> personas = servPF.getxIdentificador(TIdentificador.LEGAJO, staff.getMiembro().getLegajo());
		
		if(personas.contains(staff.getMiembro()) && staff.getId() != null)
			return true;
		return false;
	}

	@Autowired(required = true)
	public void setServPF(IServicioPF servPF) {
		this.servPF = servPF;
	}
	
	

}// fin de la clase
