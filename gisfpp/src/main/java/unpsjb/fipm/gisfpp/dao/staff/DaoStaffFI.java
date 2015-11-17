package unpsjb.fipm.gisfpp.dao.staff;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;

public class DaoStaffFI extends HibernateDaoSupport implements IDaoStaffFI {

	@Override
	public Integer crear(StaffFI instancia) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getId();
	}

	@Override
	public void actualizar(StaffFI instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	@Override
	public void eliminar(StaffFI instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<StaffFI> recuperarTodo() throws DataAccessException {
		return getHibernateTemplate().loadAll(StaffFI.class);
	}

	@Override
	public StaffFI recuperarxId(Integer id) throws DataAccessException {
		return getHibernateTemplate().get(StaffFI.class, id);
	}

}
