package unpsjb.fipm.gisfpp.servicios.workflow.solicitudNuevaIsfpp;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import unpsjb.fipm.gisfpp.util.MySpringUtil;

public class FinalizacionProceso implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		//RuntimeService rtService = MySpringUtil.getRunTimeService();
		
		//rtService.startProcessInstanceByKey("prcConvocatoriaYConvenios", execution.getProcessBusinessKey());
		
	}

}
