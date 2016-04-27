package unpsjb.fipm.gisfpp.servicios.workflow.convocatoriAcuerdoConvenios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class InitProceso implements ExecutionListener{

	private static final long serialVersionUID = 1L;
	
	private IServicioUsuario servUsuario;
	private IServiciosIsfpp servIsfpp;
	private IServicioSubProyecto servSP;
	private IServiciosStaffFI servStaffFi;
	private Logger log;
		
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String usuarioTutorAcademico;
		String mailTutorAcademico;
		String tituloIsfpp;
		String perteneceA;
		String mailsCoordinadores;
		String usuariosCoordinadores;
		String usuariosDelegados;
		String mailsDelegados;
		
		try {
			log = UtilGisfpp.getLogger();
			servIsfpp = MySpringUtil.getServicioIsfpp();
			servUsuario = MySpringUtil.getServicioUsuario();
			servSP = MySpringUtil.getServicioSubProyecto();
			servStaffFi = MySpringUtil.getServicioStaffFi();
			Isfpp isfpp = servIsfpp.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
			SubProyecto sp = servIsfpp.getPerteneceA(isfpp);
			Proyecto proyecto = servSP.getPerteneceA(sp);
			List<StaffFI> miembrosStaffFi = servStaffFi.getListado();
			
			usuarioTutorAcademico = getUsuarioTutorAcademico(isfpp);
			mailTutorAcademico = getMailTutorAcademico(isfpp);
			tituloIsfpp = isfpp.getTitulo();
			perteneceA = sp.getTitulo()+" / "+ proyecto.getTitulo();
			mailsCoordinadores = getMailsStaffFi(miembrosStaffFi, ECargosStaffFi.COORDINADOR);
			mailsDelegados = getMailsStaffFi(miembrosStaffFi, ECargosStaffFi.DELEGADO);
			usuariosCoordinadores = getUsuariosStaffFi(miembrosStaffFi, ECargosStaffFi.COORDINADOR);
			usuariosDelegados = getUsuariosStaffFi(miembrosStaffFi, ECargosStaffFi.DELEGADO);
			
			Map<String, Object> variablesDeProceso = new HashMap<String, Object>();
			variablesDeProceso.put("usuarioTutorAcademico", usuarioTutorAcademico);
			variablesDeProceso.put("mailTutorAcademico", mailTutorAcademico);
			variablesDeProceso.put("tituloIsfpp", tituloIsfpp);
			variablesDeProceso.put("perteneceA", perteneceA);
			variablesDeProceso.put("mailsCoordinadores", mailsCoordinadores);
			variablesDeProceso.put("usuariosCoordinadores", usuariosCoordinadores);
			variablesDeProceso.put("mailsDelegados", mailsDelegados);
			variablesDeProceso.put("usuariosDelegados", usuariosDelegados);
			
			execution.setVariables(variablesDeProceso);
		} catch (Exception exc) {
			log.error("Excepcion lanzada al inicializar el proceso "
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
	
	private String getMailsStaffFi(List<StaffFI> lista, ECargosStaffFi rol){
		StringBuilder resultadoDevuelto = new StringBuilder();
		for (StaffFI miembroStaffFI : lista) {
			if (miembroStaffFI.getRol() == rol) {
				resultadoDevuelto.append(miembroStaffFI.getMiembro().getEmail()+",");
			}
		}
		if (resultadoDevuelto.length() > 0) {
			//quito la coma agregada al final de la cadena
			resultadoDevuelto.deleteCharAt(resultadoDevuelto.length()-1);
		}
		return new String(resultadoDevuelto);
	}
	
	private String getUsuariosStaffFi(List<StaffFI> lista, ECargosStaffFi rol) throws Exception{
		StringBuilder resultadoDevuelto = new StringBuilder();
		Usuario usuario;
		for (StaffFI miembroStaffFI : lista) {
			if (miembroStaffFI.getRol() == rol) {
				usuario = servUsuario.getUsuario(miembroStaffFI.getMiembro());
				resultadoDevuelto.append(usuario.getNickname()+",");
			}
		}
		if (resultadoDevuelto.length() > 0) {
			//quito la coma agregada al final de la cadena
			resultadoDevuelto.deleteCharAt(resultadoDevuelto.length()-1);
		}
		return new String(resultadoDevuelto);
	}
	
}
