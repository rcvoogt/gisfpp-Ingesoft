package unpsjb.fipm.gisfpp.servicios.workflow.suspencionIsfpp;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class InitProceso implements ExecutionListener {
	
	private static final long serialVersionUID = 1L;
	
	private IServiciosIsfpp servIsfpp;
	private Logger log;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			servIsfpp = MySpringUtil.getServicioIsfpp();
			Integer idIsfpp = Integer.valueOf(execution.getProcessBusinessKey());
			Isfpp isfpp = servIsfpp.getInstancia(idIsfpp);
			SubProyecto sp = servIsfpp.getPerteneceASP(idIsfpp);
			Proyecto proyecto = servIsfpp.getPerteneceAProyecto(idIsfpp);
			
			Map<String, Object> variablesProceso = new HashMap<String, Object>();
			variablesProceso.put("mailTutorAcademico", isfpp.getTutorAcademico().getMiembro().getEmail());
			variablesProceso.put("mailResponsableProyecto", proyecto.getResponsables().get(0).getEmail());
			variablesProceso.put("tituloIsfpp", isfpp.getTitulo());
			variablesProceso.put("tituloSP", sp.getTitulo());
			variablesProceso.put("tituloProyecto", proyecto.getTitulo());
			
			execution.setVariables(variablesProceso);
		} catch (Exception exc) {
			log = UtilGisfpp.getLogger();
			log.error("Clase: "+this.getClass().getName(), exc);
		}
		
	}

}
