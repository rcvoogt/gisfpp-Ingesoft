package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;

public class DaoProyectoImpl extends HibernateDaoSupport implements DaoProyecto {

	public Integer crear(Proyecto instancia) throws DataAccessException {
		getHibernateTemplate().save(instancia);
		return instancia.getId();
	}

	public void actualizar(Proyecto instancia) throws DataAccessException {
		getHibernateTemplate().update(instancia);
	}

	public void eliminar(Proyecto instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	public List<Proyecto> recuperarTodo() throws DataAccessException {
		List<Proyecto> proyectos = getHibernateTemplate().loadAll(Proyecto.class);
		return proyectos;
	}

	public Proyecto recuperarxId(Integer id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> filtrarxTitulo(String titulo) throws DataAccessException {
		String query = "select p from Proyecto p where p.titulo like :filtro";
		List<Proyecto> resultado = (List<Proyecto>) getHibernateTemplate().findByNamedParam(query, "filtro",
				"%" + titulo + "%");
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> filtrarxCodigo(String codigo) throws DataAccessException {
		String query = "select p from Proyecto p where p.codigo like :filtro";
		List<Proyecto> resultado = (List<Proyecto>) getHibernateTemplate().findByNamedParam(query, "filtro",
				"%" + codigo + "%");
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> filtrarxResolucion(String resolucion) throws DataAccessException {
		String query = "select p from Proyecto p where p.resolucion like :filtro";
		List<Proyecto> resultado = (List<Proyecto>) getHibernateTemplate().findByNamedParam(query, "filtro",
				"%" + resolucion + "%");
		return resultado;
	}

}
