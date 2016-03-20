package unpsjb.fipm.gisfpp.servicios.workflow;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
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
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servSolicitudNuevaIsfpp")
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SolicitudNuevaIsfpp extends ProcesoGisfppImpl {
	
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
	public static final String VAR_CONTINUAR ="continuar";
	public static final String ID_DEF_PROCESO = "solicitudNuevaIsfpp";
	
			
	@Override
	public String instanciarProceso(Object entidad) throws GisfppWorkflowException {
		try {
			Usuario usuarioConectado = UtilSecurity.getUsuarioConectado();
			Isfpp isfpp = (Isfpp) entidad;
			//Usuario usuarioResponsableProyecto = servUsuario.getUsuario(isfpp.getPerteneceA().getPerteneceA().getResponsable());
			String perteneceA = isfpp.getPerteneceA().getTitulo() + " / (" +isfpp.getPerteneceA().getPerteneceA().getCodigo()+ ") "
					+ isfpp.getPerteneceA().getPerteneceA().getTitulo();
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put(VAR_NOMBRE_SOLICITANTE, usuarioConectado.getPersona().getNombre());
			variables.put(VAR_USUARIO_SOLICITANTE, usuarioConectado.getNickname());
			//variables.put(VAR_NOMBRE_RESPONSABLE, usuarioResponsableProyecto.getPersona().getNombre());
			//variables.put(VAR_USUARIO_RESPONSABLE, usuarioResponsableProyecto.getNickname());
			variables.put(VAR_MAIL_SOLICITANTE, usuarioConectado.getPersona().getEmail());
			//variables.put(VAR_MAIL_RESPONSABLE, usuarioResponsableProyecto.getPersona().getEmail());
			variables.put(VAR_PERTENECE_A, perteneceA);
			variables.put(VAR_TITULO_ISFPP, isfpp.getTitulo());
			
			ProcessInstance instancia= servRunTime.startProcessInstanceByKey(ID_DEF_PROCESO, isfpp.getId().toString(), variables);
			return instancia.getProcessInstanceId();
			
		}catch(ActivitiObjectNotFoundException exc1){
			throw new GisfppWorkflowException("La definición del proceso que se quiere instanciar no existe.\n");
		}
		catch (Exception exc2) {
			 log.error(this.getClass().getName(), exc2);
			 throw new GisfppWorkflowException("Se ha producido un error al intentar generar una instancia del proceso "
				 		+ "\"Solicitud Nueva Isfpp\". \nMensaje: " +exc2.getLocalizedMessage());
			 
		}
	
	}
	
	@Override
	public String instanciarProceso(String keyBusiness,
			Map<String, Object> variables) throws GisfppWorkflowException {
		try {
			ProcessInstance instancia = servRunTime.startProcessInstanceByKey(ID_DEF_PROCESO, keyBusiness, variables);
			return instancia.getProcessInstanceId();
		}catch(ActivitiObjectNotFoundException exc1){
			throw new GisfppWorkflowException("La definición del proceso que se quiere instanciar no existe.");
		}
		catch (Exception exc2) {
			 log.error(this.getClass().getName(), exc2);
			 throw new GisfppWorkflowException("Se ha producido un error al intentar generar una instancia del proceso "
				 		+ "\"Solicitud Nueva Isfpp\". \nMensaje: "+ exc2.getLocalizedMessage());
		}
	}

	@Autowired(required=true)
	protected void setServUsuario(IServicioUsuario servUsuario){
		this.servUsuario = servUsuario;
	}
	
	@Autowired(required=true)
	protected void setServRunTime(RuntimeService servRT){
		this.servRunTime = servRT;
	}
	
	@Autowired(required=true)
	protected void setServTask(TaskService servTask) {
		this.servTask = servTask;
	}
	
}//fin de la clase
