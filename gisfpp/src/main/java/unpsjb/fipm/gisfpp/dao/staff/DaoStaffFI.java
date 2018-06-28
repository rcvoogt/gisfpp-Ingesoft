package unpsjb.fipm.gisfpp.dao.staff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoStaffFI extends HibernateDaoSupport implements IDaoStaffFI {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(StaffFI instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| org.hibernate.exception.ConstraintViolationException cve) {
			throw cve;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void actualizar(StaffFI instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| org.hibernate.exception.ConstraintViolationException cve) {
			throw cve;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public void eliminar(StaffFI instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (DataIntegrityViolationException | ConstraintViolationException
				| org.hibernate.exception.ConstraintViolationException cve) {
			throw cve;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public List<StaffFI> recuperarTodo() throws DataAccessException {
		String query = "select stf from StaffFI as stf left join fetch stf.miembro as p left join fetch p.identificadores";
		try {
			List<StaffFI> result = (List<StaffFI>) getHibernateTemplate().find(query, null);
			// Se quitan los elementos duplicados que genera la consulta
			Set<StaffFI> listaSinDuplicados = new LinkedHashSet<StaffFI>(result);
			result.clear();
			result.addAll(listaSinDuplicados);
			for (StaffFI staff : result) {
				getHibernateTemplate().initialize(staff.getMiembro().getDatosDeContacto());
			}
			return result;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public StaffFI recuperarxId(Integer id) throws DataAccessException {
		String query = "select stf from StaffFI as stf left join fetch stf.miembro p left join fetch p.identificadores where stf.id=?";
		List<StaffFI> result;
		try {
			result = (List<StaffFI>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	@Override
	public List<PersonaFisica> getListadoPersonas() throws Exception {
		String query = "select pf from PersonaFisica pf, StaffFI stf left join fetch pf.identificadores where stf.miembro.id = pf.id";
		List<PersonaFisica> result;
		try {
			result = (List<PersonaFisica>) getHibernateTemplate().find(query, null);
			Set<PersonaFisica> listaSinDuplicados = new HashSet<>(result);
			result.clear();
			result.addAll(listaSinDuplicados);
			return result;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public List<StaffFI> getMiembroPorRol(ECargosStaffFi rol) throws Exception {
		String query = "select staffFi from StaffFI as staffFi where staffFi.rol = ?";
		List<StaffFI> resultado;
		try {
			resultado = (List<StaffFI>) getHibernateTemplate().find(query, rol);
		} catch (Exception exc1) {
			log.error("Clase: " + this.getClass().getName()
					+ "- Metodo: List<StaffFI> getMiembroPorRol(ECargosStaffFi rol)", exc1);
			throw exc1;
		}
		if (resultado != null && !resultado.isEmpty()) {
			for (StaffFI staffFi : resultado) {
				getHibernateTemplate().initialize(staffFi.getMiembro().getDatosDeContacto());
				getHibernateTemplate().initialize(staffFi.getMiembro().getIdentificadores());
			}
			return resultado;
		}
		return new ArrayList<StaffFI>();
	}

	@Override
	public void actualizarOguardar(StaffFI instancia) {
		getHibernateTemplate().saveOrUpdate(instancia);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean existe(String legajo) {
		String query = "select staff " 
					 + "from StaffFI as staff " 
					 + "where staff.miembro.legajo = ?";
		List<StaffFI> result;
		StaffFI staff;
		try {
			result = (List<StaffFI>) getHibernateTemplate().find(query, legajo);
			staff = result.get(0);
			if(staff != null)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

}// fin de la clase
