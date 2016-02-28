package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servSolicitudNuevaIsfpp")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SolicitudNuevaIsfpp implements ProcesoGisfpp {
	
	public static final String VAR_NOMBRE_SOLICITANTE = "nombreSolicitante";
	public static final String VAR_USUARIO_SOLICITANTE = "usuarioSolicitante";
	public static final String VAR_NOMBRE_RESPONSABLE = "nombreResponsableProy";
	public static final String VAR_USUARIO_RESPONSABLE = "usuarioResponsable";
	public static final String VAR_PERTENECE_A ="perteneceA";
	public static final String VAR_MAIL_SOLICITANTE ="mailSolicitante";
	public static final String VAR_MAIL_RESPONSABLE = "mailRespProyecto";
	public static final String VAR_TITULO_ISFPP ="tituloIsfpp";
	public static final String VAR_ISFPP_APROBADA ="isfppAprobada";
	public static final String VAR_MOTIVO_RECHAZO ="motivoRechazo";
	public static final String ID_DEF_PROCESO = "solicitudNuevaIsfpp";
	
	private Usuario usuarioConectado;
		
	private IServicioUsuario servUsuario;
	private RuntimeService runTimeServ;
	
	private Logger log;
	
	public SolicitudNuevaIsfpp() {
		usuarioConectado = UtilSecurity.getUsuarioConectado();
		log = UtilGisfpp.getLogger();
	}

	@Override
	public String instanciarProceso(Object entidad) throws GisfppException {
		try {
			Isfpp isfpp = (Isfpp) entidad;
			Usuario usuarioResponsableProyecto = servUsuario.getUsuario(isfpp.getPerteneceA().getPerteneceA().getResponsable());
			String perteneceA = isfpp.getPerteneceA().getTitulo() + " / (" +isfpp.getPerteneceA().getPerteneceA().getCodigo()+ ") "
					+ isfpp.getPerteneceA().getPerteneceA().getTitulo();
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put(VAR_NOMBRE_SOLICITANTE, usuarioConectado.getPersona().getNombre());
			variables.put(VAR_USUARIO_SOLICITANTE, usuarioConectado.getNickname());
			variables.put(VAR_NOMBRE_RESPONSABLE, usuarioResponsableProyecto.getPersona().getNombre());
			variables.put(VAR_USUARIO_RESPONSABLE, usuarioResponsableProyecto.getNickname());
			variables.put(VAR_MAIL_SOLICITANTE, usuarioConectado.getPersona().getEmail());
			variables.put(VAR_MAIL_RESPONSABLE, usuarioResponsableProyecto.getPersona().getEmail());
			variables.put(VAR_PERTENECE_A, perteneceA);
			variables.put(VAR_TITULO_ISFPP, isfpp.getTitulo());
			
			ProcessInstance instancia= runTimeServ.startProcessInstanceByKey(ID_DEF_PROCESO, isfpp.getId().toString(), variables);
			return instancia.getProcessInstanceId();
			
		} catch (Exception e) {
			 log.error(this.getClass().getName(), e.getCause());
			 throw new GisfppException("Se ha producido un error al intentar generar una instancia del proceso "
			 		+ "\"Solicitud Nueva Isfpp\". ", e.getCause());
		}
	
	}

	@Override
	public String instanciarProceso(String keyBusiness,
			Map<String, Object> variables) throws GisfppException {
		try {
			ProcessInstance instancia = runTimeServ.startProcessInstanceByKey(ID_DEF_PROCESO, keyBusiness, variables);
			return instancia.getProcessInstanceId();
		} catch (Exception e) {
			 log.error(this.getClass().getName(), e.getCause());
			 throw new GisfppException("Se ha producido un error al intentar generar una instancia del proceso "
			 		+ "\"Solicitud Nueva Isfpp\". ", e.getCause());
		}
	}

	@Override
	public String getIdInstancia(String keyBusiness) {
		ProcessInstanceQuery query = runTimeServ.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness, ID_DEF_PROCESO).singleResult();
		return instancia.getProcessInstanceId();
	}

	@Override
	public Map<String, Object> getVariables(String keyBusiness) {
		ProcessInstanceQuery query = runTimeServ.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness, ID_DEF_PROCESO).singleResult();
		return instancia.getProcessVariables();
	}

	@Override
	public Object getVariable(String keyBusiness, String nombreVariable) {
		ProcessInstanceQuery query = runTimeServ.createProcessInstanceQuery();
		ProcessInstance instancia = query.processInstanceBusinessKey(keyBusiness, ID_DEF_PROCESO).singleResult();
		return runTimeServ.getVariable(instancia.getId(), nombreVariable);
	}

	@Autowired(required=true)
	public void setServUsuario(IServicioUsuario servUsuario) {
		this.servUsuario = servUsuario;
	}

	@Autowired(required=true)
	public void setRunTimeServ(RuntimeService runTimeServ) {
		this.runTimeServ = runTimeServ;
	}
		
}//fin de la clase
