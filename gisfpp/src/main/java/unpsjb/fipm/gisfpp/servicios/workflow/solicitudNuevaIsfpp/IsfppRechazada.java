package unpsjb.fipm.gisfpp.servicios.workflow.solicitudNuevaIsfpp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class IsfppRechazada implements ExecutionListener {
	
	private static final long serialVersionUID = 1L;
	private IServiciosIsfpp servIsfpp;
	private Logger log = UtilGisfpp.getLogger();

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			servIsfpp = MySpringUtil.getServicioIsfpp();
			Integer idIsfpp = Integer.valueOf(execution.getProcessBusinessKey());
			Isfpp isfppRechazada = servIsfpp.getInstancia(idIsfpp);
			String motivo  = (String) execution.getVariable("motivoRechazo");
			isfppRechazada.setDetalle(motivo);
			servIsfpp.editar(isfppRechazada);
			servIsfpp.rechazarIsfpp(idIsfpp);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		
	}
	
}
