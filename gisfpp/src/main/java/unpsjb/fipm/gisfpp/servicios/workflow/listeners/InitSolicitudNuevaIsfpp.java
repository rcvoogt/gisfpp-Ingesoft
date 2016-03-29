package unpsjb.fipm.gisfpp.servicios.workflow.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.zkoss.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class InitSolicitudNuevaIsfpp implements ExecutionListener {

	private static final long serialVersionUID = -6353925914350074088L;
	
	private IServicioUsuario servUsuario;
	private IServiciosIsfpp servIsfpp;
	private IServicioSubProyecto servSP;
	private RuntimeService rtService;
	
	private Isfpp isfpp;
	private SubProyecto sp;
	private Proyecto proyecto;
	
	private String nombreSolicitante;
	private String usuarioSolicitante;
	private String mailSolicitante;
	private StringBuilder mailsResponsablesProyecto;
	private StringBuilder usuariosResponsablesProyecto;
	private String tituloIsfpp;
	private String perteneceA;
	
	private Logger log;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			log = UtilGisfpp.getLogger();
			servIsfpp = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
			servUsuario = (IServicioUsuario) SpringUtil.getBean("servUsuario");
			servSP = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
			rtService = (RuntimeService) SpringUtil.getBean("runtimeService");
						
			isfpp = servIsfpp.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
			sp = servIsfpp.getPerteneceA(isfpp);
			proyecto = servSP.getPerteneceA(sp);
			
			recuperarDatosSolicitante(execution);
			recuperarDatosResponsables();
			
			tituloIsfpp = isfpp.getTitulo();
			perteneceA = sp.getTitulo()+"/"+proyecto.getTitulo();
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("nombreSolicitante", nombreSolicitante);
			variables.put("mailSolicitante", mailSolicitante);
			variables.put("usuariosResponsablesProyecto", new String(usuariosResponsablesProyecto));
			variables.put("mailsResponsablesProyecto", new String(mailsResponsablesProyecto));
			variables.put("tituloIsfpp", tituloIsfpp);
			variables.put("perteneceA", perteneceA);
			
			execution.setVariables(variables);
			rtService.addUserIdentityLink(execution.getProcessInstanceId(), usuarioSolicitante, IdentityLinkType.STARTER);
						
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: notify(execution)", exc1);
			throw exc1;
		}
		
	}
	
	private void recuperarDatosSolicitante(DelegateExecution exc) throws Exception{
		Usuario usuario;
		
	    usuarioSolicitante = (String) exc.getVariable("usuarioSolicitante");
	    usuario = servUsuario.getUsuario(usuarioSolicitante);
	    nombreSolicitante = usuario.getPersona().getNombre();
	    mailSolicitante = usuario.getPersona().getEmail();		
	}
	
	private void recuperarDatosResponsables() throws Exception{
		usuariosResponsablesProyecto = new StringBuilder();
		mailsResponsablesProyecto = new StringBuilder();
		
		for (Iterator iterator = proyecto.getResponsables().iterator(); iterator.hasNext();) {
			PersonaFisica responsable = (PersonaFisica) iterator.next();
			Usuario usuario = servUsuario.getUsuario(responsable);
			usuariosResponsablesProyecto.append(usuario.getNickname());
			mailsResponsablesProyecto.append(responsable.getEmail());
			if (iterator.hasNext()) {
				usuariosResponsablesProyecto.append(", ");
				mailsResponsablesProyecto.append(", ");
			}
		}
			
	}

}//fin de la clase
