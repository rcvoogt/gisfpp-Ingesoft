package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;

public interface IDaoSubProyecto extends DaoGenerico<SubProyecto, Integer> {

	public List<SubProyecto> listadoSubProyectos(Proyecto proyecto) throws DataAccessException;
	
	public List<SubProyecto> listadoOfertasActividades()  throws DataAccessException;

}
