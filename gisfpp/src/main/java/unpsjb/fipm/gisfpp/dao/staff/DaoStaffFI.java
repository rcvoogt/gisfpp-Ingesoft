package unpsjb.fipm.gisfpp.dao.staff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoStaffFI extends HibernateDaoSupport implements IDaoStaffFI {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	@Transactional(readOnly = false)
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
	@Transactional(readOnly = false)
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
	@Transactional(readOnly = false)
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
	@Transactional(readOnly = true)
	public List<StaffFI> recuperarTodo() throws DataAccessException {
		String query = "select stf from StaffFI as stf left join fetch stf.miembro as p left join fetch p.identificadores";
		try {
			List<StaffFI> result = (List<StaffFI>) getHibernateTemplate().find(query, null);
			//Se quitan los elementos duplicados que genera la consulta
			Set<StaffFI> listaSinDuplicados = new LinkedHashSet<StaffFI>(result);
			result.clear();
			result.addAll(listaSinDuplicados);
			for (StaffFI staff : result) {
				getHibernateTemplate().initialize(staff.getMiembro().getDatosDeContacto());
			}
			return  result;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
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

}// fin de la clase
