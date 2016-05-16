package unpsjb.fipm.gisfpp.servicios.workflow.generacionInformesFinales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

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
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
				
		servUsuario = MySpringUtil.getServicioUsuario();
		servIsfpp = MySpringUtil.getServicioIsfpp();
		servSP = MySpringUtil.getServicioSubProyecto();
		servStaffFi = MySpringUtil.getServicioStaffFi();
		
		Isfpp isfpp = servIsfpp.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
		SubProyecto sp = servIsfpp.getPerteneceASP(isfpp.getId());
		Proyecto proyecto = servSP.getPerteneceA(sp.getId());
		List<StaffFI> delegados = servStaffFi.getMiembroPorRol(ECargosStaffFi.DELEGADO);
				
		Map<String, Object> variablesDelProceso = new HashMap<String, Object>();
		variablesDelProceso.put("mailsTutores", getMailsTutores(isfpp));
		variablesDelProceso.put("mailsPracticantes", getMailsPracticantes(isfpp));
		variablesDelProceso.put("mailsDelegados", getMailsDelegados(delegados));
		variablesDelProceso.put("usuariosDelegados", getUsuariosDelegados(delegados));
		variablesDelProceso.put("tituloIsfpp", isfpp.getTitulo());
		variablesDelProceso.put("tituloSP", sp.getTitulo());
		variablesDelProceso.put("tituloProyecto", proyecto.getTitulo());
		variablesDelProceso.put("practicantes", getPracticantes(isfpp));
		variablesDelProceso.put("listaPracticantes", getListadoPracticantes(isfpp));
		variablesDelProceso.put("pathApp", UtilGisfpp.getProperty("app.path"));
		
		execution.setVariables(variablesDelProceso);
	}
	
	private String getMailsTutores(Isfpp isfpp){
		StringBuilder devolucion = new StringBuilder();
		String mailTutorAcademico = (isfpp.getTutorAcademico()!= null)? isfpp.getTutorAcademico().getMiembro().getEmail():null;
		String mailTutorExterno = (isfpp.getTutorExterno()!= null)? isfpp.getTutorExterno().getMiembro().getEmail():null;
		if (mailTutorAcademico!=null) {
			devolucion.append(mailTutorAcademico);
		}
		if(mailTutorExterno!=null){
			devolucion.append(", "+ mailTutorExterno);
		}
		return new String(devolucion);
	}
	
	private String getMailsPracticantes(Isfpp isfpp){
		StringBuilder devolucion = new StringBuilder();
		
		for (Iterator iterator = isfpp.getPracticantes().iterator(); iterator.hasNext();) {
			PersonaFisica practicante = (PersonaFisica) iterator.next();
			devolucion.append(practicante.getEmail());
			if (iterator.hasNext()) {
				devolucion.append(", ");
			}
		}
		
		return new String(devolucion);
	}
	
	private String getMailsDelegados(List<StaffFI> lista) throws Exception{
		StringBuilder devolucion = new StringBuilder();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			StaffFI staffFI = (StaffFI) iterator.next();
			devolucion.append(staffFI.getMiembro().getEmail());
			if (iterator.hasNext()) {
				devolucion.append(", ");
			}
		}
		return new String(devolucion);
	}
	
	private String getUsuariosDelegados(List<StaffFI> lista) throws Exception{
		StringBuilder devolucion = new StringBuilder();
		Usuario usuario;
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			StaffFI staffFI = (StaffFI) iterator.next();
			usuario = servUsuario.getUsuario(staffFI.getMiembro());
			devolucion.append(usuario.getNickname());
			if (iterator.hasNext()) {
				devolucion.append(", ");
			}
		}
		return new String(devolucion);
	}
	
	private String getPracticantes(Isfpp isfpp){
		StringBuilder devolucion = new StringBuilder();
		for (Iterator iterator = isfpp.getPracticantes().iterator(); iterator.hasNext();) {
			PersonaFisica persona = (PersonaFisica) iterator.next();
			devolucion.append(persona.getNombre());
			if (iterator.hasNext()) {
				devolucion.append(", ");
			}
		}
		return new String(devolucion);
	}
	
	private List<String> getListadoPracticantes(Isfpp isfpp) {
		List<String> devolucion = new ArrayList<String>();
		for (Iterator iterator = isfpp.getPracticantes().iterator(); iterator.hasNext();) {
			PersonaFisica persona = (PersonaFisica) iterator.next();
			devolucion.add(persona.getNombre()+" (DNI: "+persona.getDni()+")");
		}
		return devolucion;
	}

}
