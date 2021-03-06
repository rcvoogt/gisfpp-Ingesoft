package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.Persona;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoProyectoImpl extends HibernateDaoSupport implements DaoProyecto {

	private Logger log = UtilGisfpp.getLogger();

	public Integer crear(Proyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public void actualizar(Proyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().saveOrUpdate(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	public void eliminar(Proyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> recuperarTodo() throws DataAccessException {
		String query = "select p from Proyecto as p left join fetch p.subProyectos";
		List<Proyecto> resultado;
		try {
			resultado = (List<Proyecto>) getHibernateTemplate().find(query, null);
			if (resultado!= null && !resultado.isEmpty()) {
				Set<Proyecto> sinDuplicados = new HashSet<Proyecto>(resultado);
				resultado.clear();
				resultado.addAll(sinDuplicados);
				return resultado;
			}
			return new ArrayList<Proyecto>();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public Proyecto recuperarxId(Integer id) throws DataAccessException {
		String query = "select p from Proyecto as p left join fetch p.subProyectos left join fetch p.demandantes"
				+ " left join fetch p.staff where p.id=?";
		Proyecto p;
		List<Proyecto> result;
		try {
			result = (List<Proyecto>) getHibernateTemplate().find(query, id);
			p = result.get(0);
			if ((result.isEmpty()) || (result == null)) {
				return null;
			} else {
				//getHibernateTemplate().initialize(result.get(0).getStaff());
				for (Persona demandante : result.get(0).getDemandantes()) {
					getHibernateTemplate().initialize(demandante.getIdentificadores());
				}
				for(MiembroStaffProyecto miembroStaff: result.get(0).getStaff()){
					PersonaFisica persona= miembroStaff.getMiembro();
					getHibernateTemplate().initialize(persona.getId());
					getHibernateTemplate().initialize(persona.getIdentificadores());
					getHibernateTemplate().initialize(persona.getDatosDeContacto());

				}
				getHibernateTemplate().initialize(p.getConvocatorias());
				return p;
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	public List<OfertaActividad> getAllOfertas() {
		String query = "select p from Proyecto as p left join fetch p.subProyectos where p.estado = ?";
		List<Proyecto> resultado;
		List<OfertaActividad> resultado2 = new ArrayList<OfertaActividad>();
		try {
			resultado = (List<Proyecto>) getHibernateTemplate().find(query,EstadoProyecto.ACTIVO);
			if (resultado!= null && !resultado.isEmpty()) {
				//Eliminamos duplicados
				Set<Proyecto> sinDuplicados = new HashSet<Proyecto>(resultado);
				resultado.clear();
				resultado.addAll(sinDuplicados);
				//Reparamos los casos de proyectos con varios subproyectos)
				for(Proyecto aux : resultado) {
					if(aux.getSubProyectos().size() != 0) {
						for(SubProyecto sAux : aux.getSubProyectos()) {
							//Aca validar que el subProyecto siga vigente
							resultado2.add(new OfertaActividad(aux,sAux));
						}
					}else{
						resultado2.add(new OfertaActividad(aux,null));
					}
				}
				return resultado2;
			}
			return new ArrayList<OfertaActividad>();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
	}
		}

	@Override
	public List<Convocatoria> getConvocatorias(Integer idProyecto) throws Exception {
			String query ="select c from Proyecto as p join p.convocatorias as p where p.id = ?";
			List<Convocatoria> resultado;
			try {
				resultado = (List<Convocatoria>) getHibernateTemplate().find(query, idProyecto);
			} catch (Exception exc) {
				log.error("Clase: "+ this.getClass().getName() +"- Metodo: List<Convocatoria> getConvocatoria(Integer idProyecto)", exc);
				throw exc;
			}
			if (resultado!=null) {
				for (Convocatoria convocatoria : resultado) {
					getHibernateTemplate().initialize(convocatoria.getConvocados());
					
				}
				return resultado;
			}
			return new ArrayList<Convocatoria>();
	}

	/**
	 * Consulta usando la clase Query de hibernate
	 */
	@Override
	@Transactional
	public List<Proyecto> getProyectosActivos() {
		String hql =  "select p "
					+ "from Proyecto p "
					+ "where p.estado = :estado ";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("estado", EstadoProyecto.ACTIVO);
		List<Proyecto> proyectosActivos = query.list();
		for(Proyecto p: proyectosActivos) {
			getHibernateTemplate().initialize(p.getStaff());

		}
		return proyectosActivos;
	}	
	
	
}// fin de la clase
