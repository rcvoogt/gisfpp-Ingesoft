package unpsjb.fipm.gisfpp.servicios.workflow.convocatoriAcuerdoConvenios;

import java.util.Iterator;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class RegistrarMails implements ExecutionListener {
	
	private static final long serialVersionUID = 1L;
	IServiciosIsfpp servIsfpp;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			servIsfpp = MySpringUtil.getServicioIsfpp();
			Isfpp isfpp = servIsfpp.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
			String mails = getMailsPracticantesTutorExt(isfpp);
			
			execution.setVariable("mailsPartesInteresadas", mails);
		} catch (Exception exc) {
			Logger log = UtilGisfpp.getLogger();
			log.error("Exception Workflow: Convocatoria y Confeccion de Convenios - Clase: RegistrarMails", exc);
		}
		
	}
	
	private String getMailsPracticantesTutorExt(Isfpp isfpp){
		StringBuilder resultado = new StringBuilder();
		
		for (Iterator iterator = isfpp.getPracticantes().iterator(); iterator.hasNext();) {
			PersonaFisica persona = (PersonaFisica) iterator.next();
			resultado.append(persona.getEmail());
			if (iterator.hasNext()) {
				resultado.append(", ");
			}
		}
		if(isfpp.getTutorExterno()!= null){
			resultado.append(", " + isfpp.getTutorExterno().getMiembro().getEmail());
		}
		return new String(resultado);
	}

}
