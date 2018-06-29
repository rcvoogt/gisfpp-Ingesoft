package unpsjb.fipm.gisfpp.dao.cursada;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import unpsjb.fipm.gisfpp.entidades.cursada.Cursada;
import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoCursada extends HibernateDaoSupport implements IDaoCursada {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	public Integer crear(Cursada instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getIdCursada();
		} catch (Exception e) {
			log.debug(this.getClass().getName(), e);
		}

		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getIdCursada();

	}

	@Override
	public void actualizar(Cursada instancia) throws DataAccessException {
		// TODO Auto-generated method stub
		getHibernateTemplate().merge(instancia);
	}

	@Override
	public void eliminar(Cursada instancia) throws DataAccessException {
		getHibernateTemplate().delete(instancia);
	}

	@Override
	public List<Cursada> recuperarTodo() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursada recuperarxId(Integer id) throws DataAccessException {

		String query = "select cursada " + "from Cursada cursada " + "where cursada.idCursada = ?";
		List<Cursada> result = (List<Cursada>) getHibernateTemplate().find(query, id);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);

	}

	@Override
	public int actualizarOguardar(Cursada instancia) throws Exception {
		getHibernateTemplate().saveOrUpdate(instancia);
		return instancia.getIdCursada();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean existe(String codigoComision) {
		String query = "select cursada " + 
				"from Cursada as cursada " + 
				"where cursada.codigo_comision = ?";
		List<Cursada> result;
		Cursada cursadaAux;
		try {
			result = (List<Cursada>) getHibernateTemplate().find(query, codigoComision);
			cursadaAux = result.get(0);
			return true;
		} catch (Exception e) {
		}
		return false;

	}

}
