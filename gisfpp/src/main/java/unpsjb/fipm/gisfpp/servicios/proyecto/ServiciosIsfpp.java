package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.IDaoIsfpp;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorMotorBpm;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servIsfpp")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosIsfpp implements IServiciosIsfpp {

	private IDaoIsfpp dao;
	private GestorWorkflow servGWkFl;
	private GestorMotorBpm servMotorWf;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Isfpp instancia) throws Exception {
		int idIsfpp = dao.crear(instancia);
		servGWkFl.instanciarProceso("Isfpp", "Crear", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
		return idIsfpp;
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Isfpp instancia) throws Exception {
		dao.actualizar(instancia);
		servGWkFl.instanciarProceso("ISfpp", "Editar", UtilSecurity.getNickName(), String.valueOf(instancia.getId()));
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Isfpp instancia) throws Exception {
		ResultadoValidacion resultado = ValidacionesProyecto.eliminarIsfpp(instancia);
		if(resultado.isValido()){
			dao.eliminar(instancia);
			servGWkFl.instanciarProceso("Isfpp", "Eliminar", UtilSecurity.getNickName(), String.valueOf(instancia.getId()));
			//Reactivamos las intancias de procesos en ejecuci�n que han sido previamente suspendidas
			//asociadas a la Isfpp.
			List<InstanciaProceso> instanciasEnEjecucion = servGWkFl.getInstanciasProcesos(String.valueOf(instancia.getId()));
			for (InstanciaProceso wkfl : instanciasEnEjecucion) {
				servMotorWf.eliminarInstanciaProceso(wkfl.getIdInstancia());
			}
		}else{
			throw new GisfppException(resultado.getMensaje());
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Isfpp getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Isfpp> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception {
		return dao.getIsfpps(sp);
	}

	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public SubProyecto getPerteneceASP(Integer idIsfpp) throws Exception {
		return dao.getPerteneceA(idIsfpp);
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public Proyecto getPerteneceAProyecto(Integer idIsfpp) throws Exception {
		return dao.getPerteneceAProyecto(idIsfpp);
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public List<PersonaFisica> getPracticantes(Integer idIsfpp)
			throws Exception {
		return dao.getPracticantes(idIsfpp);
	}

	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public int getCantidadPracticantes(Integer idIsfpp) throws Exception {
		return dao.getCantidadPracticantes(idIsfpp);
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void activarIsfpp(Integer idIsfpp) throws Exception {
		Isfpp isfpp = getInstancia(idIsfpp);
		
		if(isfpp.getEstado()!= EEstadosIsfpp.GENERADA){
			throw new GisfppException("Solo se puede Activar una Isfpp en estado \"Generada\".");
		}
		dao.actualizarEstado(idIsfpp, EEstadosIsfpp.ACTIVA);
		servGWkFl.instanciarProceso("Isfpp", "Activar", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void reActivarISfpp(Integer idIsfpp) throws Exception {
		Isfpp isfpp = getInstancia(idIsfpp);
				
		if (isfpp.getEstado()!= EEstadosIsfpp.SUSPENDIDA) {
			throw new GisfppException("Solo se puede Reactivar una Isfpp en estado \"Suspendida\".");
		}
		dao.actualizarEstado(idIsfpp, EEstadosIsfpp.ACTIVA);
		servGWkFl.instanciarProceso("Isfpp", "Reactivar", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
		
		//Reactivamos las intancias de procesos en ejecuci�n que han sido previamente suspendidas
		//asociadas a la Isfpp.
		List<InstanciaProceso> instanciasEnEjecucion = servGWkFl.getInstanciasProcesos(String.valueOf(idIsfpp));
		for (InstanciaProceso instancia : instanciasEnEjecucion) {
			servMotorWf.activarInstanciaProceso(instancia.getIdInstancia());
		}
		
	}

	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void rechazarIsfpp(Integer idIsfpp) throws Exception {
		Isfpp isfpp = getInstancia(idIsfpp);
		
		if(isfpp.getEstado()!= EEstadosIsfpp.GENERADA){
			throw new GisfppException("Solo se puede Rechazar una Isfpp si se encuentra en estado \"Generada\".");
		}
		dao.actualizarEstado(idIsfpp, EEstadosIsfpp.RECHAZADA);
		servGWkFl.instanciarProceso("Isfpp", "Rechazar", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
	}

	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void suspenderIsfpp(Integer idIsfpp) throws Exception {
		Isfpp isfpp = getInstancia(idIsfpp);
				
		if (isfpp.getEstado()!= EEstadosIsfpp.ACTIVA) {
			throw new GisfppException("Solo se puede Suspender una Isfpp en estado \"Activa\".");
		}
		dao.actualizarEstado(idIsfpp, EEstadosIsfpp.SUSPENDIDA);
		servGWkFl.instanciarProceso("Isfpp", "Suspender", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
		
		List<InstanciaProceso> instanciasEnEjecucion = servGWkFl.getInstanciasProcesos(String.valueOf(idIsfpp));
		for (InstanciaProceso instancia : instanciasEnEjecucion) {
			servMotorWf.suspenderInstanciaProceso(instancia.getIdInstancia());
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void cancelarIsfpp(Integer idIsfpp) throws Exception {
		Isfpp isfpp = getInstancia(idIsfpp);
		
		if (isfpp.getEstado() != EEstadosIsfpp.ACTIVA) {
			throw new GisfppException("Solo se puede Cancelar una Isfpp si se encuentra en estado \"Activa\".");
		}
		dao.actualizarEstado(idIsfpp, EEstadosIsfpp.CANCELADA);
		servGWkFl.instanciarProceso("Isfpp", "Cancelar", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
		
		List<InstanciaProceso> instanciasEnEjecucion = servGWkFl.getInstanciasProcesos(String.valueOf(idIsfpp));
		for (InstanciaProceso instancia : instanciasEnEjecucion) {
			servMotorWf.eliminarInstanciaProceso(instancia.getIdInstancia());
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly=false)
	public void concluirIsfpp(Integer idIsfpp) throws Exception {
		Isfpp isfpp = getInstancia(idIsfpp);
		
		if (isfpp.getEstado() != EEstadosIsfpp.ACTIVA) {
			throw new GisfppException("Solo se puede Concluir una Isfpp si se encuentra en estado \"Activa\".");
		}
		dao.actualizarEstado(idIsfpp, EEstadosIsfpp.CONCLUIDA);
		servGWkFl.instanciarProceso("Isfpp", "Concluir", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
	}
	
		
	@Autowired(required = true)
	protected void setDao(IDaoIsfpp dao) {
		this.dao = dao;
	}

	@Autowired(required=true)
	public void setServGWkFl(GestorWorkflow servGWkFl) {
		this.servGWkFl = servGWkFl;
	}

	@Autowired(required=true)
	public void setServMotorWf(GestorMotorBpm servMotorWf) {
		this.servMotorWf = servMotorWf;
	}

	@Override
	public Convocatoria getUltimaConvocatoria(Integer idIsfpp) throws Exception {
		/*
		 * TODO: Implementar buscar la ultima convocatoria activa
		 */
		return null;
	}

	@Override
	public Isfpp getInstancia(Date createTime) {
		List<Isfpp> isfpps = this.dao.recuperarTodo();
		for(Isfpp isfpp: isfpps) {
			if(isfpp.getInicio().equals(createTime)) {
				return isfpp;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public List<MiembroStaffIsfpp> getMiembros(Isfpp isfpp) throws Exception,NullPointerException {
		List<MiembroStaffIsfpp> miembros = this.dao.getMiembros(isfpp);
		return miembros;
	}

	@Override
	public MiembroStaffIsfpp getResponsableIsfpp(Isfpp isfpp) throws Exception, NullPointerException {
		List<MiembroStaffIsfpp> miembros = getMiembros(isfpp);
		for(MiembroStaffIsfpp miembro: miembros) {
			if(miembro.getRol().equals(ERolStaffIsfpp.TUTOR_ACADEMICO)) {
				return miembro;
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Convocatoria> getConvocatorias(Integer idIsfpp) throws Exception{
		return dao.getConvocatorias(idIsfpp);
	}
	
	

}// fin de la clase
