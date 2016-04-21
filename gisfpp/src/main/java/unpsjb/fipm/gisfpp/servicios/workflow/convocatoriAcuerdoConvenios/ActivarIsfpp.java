package unpsjb.fipm.gisfpp.servicios.workflow.convocatoriAcuerdoConvenios;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;

public class ActivarIsfpp implements ExecutionListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		IServiciosIsfpp servIsfpp = MySpringUtil.getServicioIsfpp();
		Integer idIsfpp = Integer.valueOf(execution.getProcessBusinessKey());
		
		servIsfpp.activarIsfpp(idIsfpp);
		
	}

}
