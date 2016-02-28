package unpsjb.fipm.gisfpp.servicios.workflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class IsfppRechazada implements ExecutionListener {
	
	private static final long serialVersionUID = 1L;
	private IServiciosIsfpp servIsfpp;
	private Logger log = UtilGisfpp.getLogger();

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			Isfpp isfppRechazada = servIsfpp.getInstancia(Integer.valueOf(execution.getProcessBusinessKey()));
			isfppRechazada.setEstado(EEstadosIsfpp.RECHAZADA);
			servIsfpp.editar(isfppRechazada);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		
	}

	@Autowired(required=true)
	public void setServIsfpp(IServiciosIsfpp servIsfpp) {
		this.servIsfpp = servIsfpp;
	}
	
}
