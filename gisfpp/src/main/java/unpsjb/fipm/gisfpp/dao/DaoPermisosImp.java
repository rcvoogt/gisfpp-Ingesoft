package unpsjb.fipm.gisfpp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.Permiso;
import unpsjb.fipm.gisfpp.entidades.Roles;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoPermisosImp extends HibernateDaoSupport implements DaoPermisos {
	
	private Logger log;
	
	@PostConstruct
	public void init(){
		log = UtilGisfpp.getLogger();
	}
	
	@Override
	public Integer crear(Permiso instancia) throws DataAccessException {
		try {
			getHibernateTemplate().saveOrUpdate(instancia);
			return instancia.getId();
		} catch (DataAccessException exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: Integer crear(Permiso)", exc1);
			throw exc1;
		}
	}

	@Override
	public void actualizar(Permiso instancia) throws DataAccessException {
		try {
			getHibernateTemplate().saveOrUpdate(instancia);
		} catch (DataAccessException exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: void actualizar(Permiso)", exc1);
			throw exc1;
		}
	}

	@Override
	public void eliminar(Permiso instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (DataAccessException exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: void eliminar(Permiso)", exc1);
			throw exc1;
		}

	}

	@Override
	public List<Permiso> recuperarTodo() throws DataAccessException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Permiso.class);
		try {
			return (List<Permiso>) getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: List<Permiso> recuperarTodo()", exc1);
			throw exc1;
		}
	}

	@Override
	public Permiso recuperarxId(Integer id) throws DataAccessException {
		try {
			return getHibernateTemplate().get(Permiso.class, id);
		} catch (DataAccessException exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: Permiso recuperarxId(Integer)", exc1);
			throw exc1;
		}
	}

	@Override
	public List<Operaciones> getOperacionesxRol(Roles rol) throws Exception {
		String query = "select p.operacion from Permiso as p where p.rol = ?";
		List<Operaciones> operaciones = new ArrayList<Operaciones>();
		try {
			operaciones = (List<Operaciones>) getHibernateTemplate().find(query, rol);
			return operaciones;
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: List<Operaciones> getOpercionesxRol(Roles rol)", exc1);
			throw exc1;
		}
	}
	
	@Override
	public List<Permiso> getPermisosxRol(Roles rol) throws Exception {
		String query = "select p from Permiso as p where p.rol = ?";
		List<Permiso> permisos;
		try {
			permisos = (List<Permiso>) getHibernateTemplate().find(query, rol);
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+" - Metodo: List<Permiso> getPermisosxRol(Roles rol)", exc1);
			throw exc1;
		}
		if(permisos == null){
			permisos = new ArrayList<Permiso>();
		}
		return permisos;
	}
	
}
