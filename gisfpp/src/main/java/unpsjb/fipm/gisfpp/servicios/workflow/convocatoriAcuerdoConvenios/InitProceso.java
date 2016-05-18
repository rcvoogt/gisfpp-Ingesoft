 package unpsjb.fipm.gisfpp.servicios.workflow.convocatoriAcuerdoConvenios;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class InitProceso implements ExecutionListener{

	private static final long serialVersionUID = 1L;
	
	private IServicioUsuario servUsuario;
	private IServiciosIsfpp servIsfpp;
	private IServicioSubProyecto servSP;
	private Logger log;
		
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String usuarioTutorAcademico;
		String mailTutorAcademico;
		String tituloIsfpp;
		String perteneceA;
		
		
		try {
			log = UtilGisfpp.getLogger();
			servIsfpp = MySpringUtil.getServicioIsfpp();
			servUsuario = MySpringUtil.getServicioUsuario();
			servSP = MySpringUtil.getServicioSubProyecto();
			Isfpp isfpp = servIsfpp.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
			SubProyecto sp = servIsfpp.getPerteneceASP(isfpp.getId());
			Proyecto proyecto = servSP.getPerteneceA(sp.getId());
			usuarioTutorAcademico = getUsuarioTutorAcademico(isfpp);
			mailTutorAcademico = getMailTutorAcademico(isfpp);
			tituloIsfpp = isfpp.getTitulo();
			perteneceA = sp.getTitulo()+" / "+ proyecto.getTitulo();
						
			Map<String, Object> variablesDeProceso = new HashMap<String, Object>();
			variablesDeProceso.put("usuarioTutorAcademico", usuarioTutorAcademico);
			variablesDeProceso.put("mailTutorAcademico", mailTutorAcademico);
			variablesDeProceso.put("tituloIsfpp", tituloIsfpp);
			variablesDeProceso.put("perteneceA", perteneceA);
			variablesDeProceso.put("pathApp", UtilGisfpp.getProperty("app.path"));
			
			execution.setVariables(variablesDeProceso);
		} catch (Exception exc) {
			log.error("Exception Workflow: Excepcion lanzada al inicializar el proceso "
					+ "'Convocatoria, confección y firma de acuerdo y convenios'...", exc);
			throw exc;
		}
		
		
	}

	private String getUsuarioTutorAcademico(Isfpp isfpp) throws Exception{
		PersonaFisica personaTutor = isfpp.getTutorAcademico().getMiembro();
		Usuario usuarioTutor= servUsuario.getUsuario(personaTutor);
		
		return usuarioTutor.getNickname();
	}
	
	private String getMailTutorAcademico(Isfpp isfpp){
		PersonaFisica personaTutor = isfpp.getTutorAcademico().getMiembro();
		
		return personaTutor.getEmail();
	}
	
}
