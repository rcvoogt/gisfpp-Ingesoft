package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.DaoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;
import unpsjb.fipm.gisfpp.util.GisfppException;

@Service("servProyecto")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosProyecto implements IServiciosProyecto {

	private DaoProyecto dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Proyecto instancia) throws Exception {
		try {
			dao.crear(instancia);
			return instancia.getId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Proyecto instancia) throws Exception {
		try {
			dao.actualizar(instancia);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Proyecto instancia) throws Exception {
		ResultadoValidacion resultado = ValidacionesProyecto.eliminarProyecto(instancia);
		if(resultado.isValido()){
			dao.eliminar(instancia);
		}else{
			throw new GisfppException(resultado.getMensaje());
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Proyecto getInstancia(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Proyecto> getListado() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(DaoProyecto dao) {
		this.dao = dao;
	}
	

	@Override
	public List<OfertaActividad> getAllOfertas() {
		try {
			return dao.getAllOfertas();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean cumpleVencimientoMeses(Proyecto proyecto, Integer venc) {
		//Fecha del proyecto en calendar
		Calendar fechaPiola = Calendar.getInstance();
		fechaPiola.setTime(proyecto.getFecha_fin());
		//La fecha de hoy
		Calendar fechaComparar = Calendar.getInstance();
		//Le agregamos 3 meses
		fechaComparar.add(Calendar.MONTH, venc);
		//Si la fecha de hoy + los meses solicitados sigue siendo antes del vencimiento del proyecto, este es Valido
		return fechaComparar.before(fechaPiola);
	}

	public List<Convocatoria> getConvocatorias(Integer idProyecto) throws Exception{
		return dao.getConvocatorias(idProyecto);
	}

	@Override
	public List<Proyecto> getProyectosActivos() throws Exception {
		List<Proyecto> proyectosActivos = dao.getProyectosActivos();
		return proyectosActivos;
	}

	/**
	 * Agrega un miembro al staff 
	 */
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void agregarMiembroStaff(PersonaFisica persona, Proyecto proyecto) {
		MiembroStaffProyecto miembro = convertir(persona,proyecto,ERolStaffProyecto.MIEMBRO_STAFF_PROYECTO);
		if(proyecto.getStaff().contains(miembro))
			throw new IllegalArgumentException("La persona:" + persona.getNombre() + " ya forma parte del staff");
		proyecto.getStaff().add(miembro);
		dao.actualizar(proyecto);
	}

	/**
	 * Quita un miembro del staff
	 */
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void quitarMiembroStaff(PersonaFisica persona, Proyecto proyecto) {
		MiembroStaffProyecto miembro = convertir(persona,proyecto,ERolStaffProyecto.MIEMBRO_STAFF_PROYECTO);
		if(proyecto.getStaff().remove(miembro)) {
			dao.actualizar(proyecto);
		}
	}
	
	/**
	 * Agrega varios miembros al staff
	 */
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void agregarMiembrosStaff(Set<PersonaFisica> practicantes, Proyecto proyecto) {
		Set<MiembroStaffProyecto> miembros = new HashSet<MiembroStaffProyecto>();
		for(PersonaFisica persona: practicantes){
			miembros.add(convertir(persona,proyecto,ERolStaffProyecto.MIEMBRO_STAFF_PROYECTO));
		}
		proyecto.getStaff().addAll(miembros);
		dao.actualizar(proyecto);
	}
	
	/**
	 * Convierte una persona fisica en miembro de staff
	 * @param persona
	 * @param proyecto
	 * @param rol
	 * @return
	 */
	private MiembroStaffProyecto convertir(PersonaFisica persona, Proyecto proyecto, ERolStaffProyecto rol) {
		MiembroStaffProyecto miembro = new MiembroStaffProyecto();
		miembro.setMiembro(persona);
		miembro.setProyecto(proyecto);
		miembro.setRol(rol);
		return miembro;
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public boolean isMiembroStaff(Integer id, PersonaFisica persona) throws Exception {
		Proyecto proyecto = this.getInstancia(id);
		MiembroStaffProyecto m = new MiembroStaffProyecto();
		m.setMiembro(persona);
		m.setProyecto(proyecto);
		return proyecto.getStaff().contains(m);
	}
	
}// fin de la clase
