package unpsjb.fipm.gisfpp.dao.staff;

import java.util.List;

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
		String query = "from StaffFi as stf left join fetch stf.miembro";
		try {
			return (List<StaffFI>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public StaffFI recuperarxId(Integer id) throws DataAccessException {
		String query = "from StaffFi stf left join fetch stf.miembro where stf.id=?";
		List<StaffFI> resultado;
		try {
			resultado = (List<StaffFI>) getHibernateTemplate().find(query, id);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		if (resultado == null || resultado.isEmpty()) {
			return null;
		}
		return resultado.get(0);
	}

}// fin de la clase
