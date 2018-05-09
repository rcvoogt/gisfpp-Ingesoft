package unpsjb.fipm.gisfpp.servicios.workflow.nuevaConvocatoria;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class InitNuevaConvocatoria implements ExecutionListener {

	private static final long serialVersionUID = -6353925914350074088L;
	
	private IServicioUsuario servUsuario;
	private IServiciosIsfpp servIsfpp;
	private IServicioSubProyecto servSP;
	private IServiciosConvocatoria servConvocatoria;
	private RuntimeService rtService;
	
	private Isfpp isfpp;
	private SubProyecto sp;
	private Proyecto proyecto;
	private Convocatoria convocatoria;
	
	private Convocado convocado;
	private StringBuilder mailsConvocados;
	private StringBuilder asignadosConvocados;
	private String mensaje;
	private Integer cantidadConvocados;
	private Collection convocados;
	private Date vencimiento;
	
	
	private String nombreSolicitante;
	private String usuarioSolicitante;
	private String mailSolicitante;
	private String tituloIsfpp;
	private String perteneceA;
	
	private Logger log;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			log = UtilGisfpp.getLogger();
			servIsfpp = MySpringUtil.getServicioIsfpp();
			servUsuario = MySpringUtil.getServicioUsuario();
			servSP = MySpringUtil.getServicioSubProyecto();
			servConvocatoria = MySpringUtil.getServicioConvocatoria();
			rtService = MySpringUtil.getRunTimeService();
						
			convocatoria = servConvocatoria.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
			isfpp = convocatoria.getIsfpp();
			sp = isfpp.getPerteneceA();
			proyecto = sp.getPerteneceA();
			
			recuperarDatosConvocador(execution);
			
			
			tituloIsfpp = isfpp.getTitulo();
			perteneceA = sp.getTitulo()+"/"+proyecto.getTitulo();
			recuperarmailsConvocados();
			
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("nombreSolicitante", nombreSolicitante);
			variables.put("mensaje",convocatoria.getMensaje());
			variables.put("cantidadConvocados",convocatoria.getConvocados().size());
			variables.put("convocados",convocatoria.getConvocados());
			
			//variables.put("convocado",new Convocado());
			variables.put("vencimiento",convocatoria.getFechaVencimiento());
			variables.put("convocador",usuarioSolicitante);
			
			
			//variables.put("convocadosonvocados",new String(asignadosConvocados));
			variables.put("asignadosConvocados",new String(asignadosConvocados));
			variables.put("mailsConvocados",new String(mailsConvocados));
			variables.put("Contador",new Integer(0));
			
			
			execution.setVariables(variables);
			rtService.addUserIdentityLink(execution.getProcessInstanceId(), usuarioSolicitante, IdentityLinkType.STARTER);
						
		} catch (Exception exc1) {
			log.error("Clase: "+this.getClass().getName()+"- Metodo: notify(execution)", exc1);
			throw exc1;
		}
		
	}
	
	private void recuperarConvocadosAsString() throws Exception{
		
	}
	
	private void recuperarmailsConvocados() throws Exception{
		mailsConvocados = new StringBuilder();
		asignadosConvocados = new StringBuilder();
		for (Iterator iterator = convocatoria.getConvocados().iterator(); iterator.hasNext();) {
			PersonaFisica convocado = ((Convocado) iterator.next()).getPersona();
			mailsConvocados.append(convocado.getEmail());
			asignadosConvocados.append(convocado.getUsuario().getNickname());
			if (iterator.hasNext()) {
				mailsConvocados.append(", ");
				asignadosConvocados.append(", ");
			}
		}
			
	}
	
	private void recuperarDatosConvocador(DelegateExecution exc) throws Exception{
		Usuario usuario;
		
	    usuarioSolicitante = (String) exc.getVariable("usuarioIniciador");
	    usuario = servUsuario.getUsuario(usuarioSolicitante);
	    nombreSolicitante = usuario.getPersona().getNombre();
	    mailSolicitante = usuario.getPersona().getEmail();		
	}
	
	

}//fin de la clase
