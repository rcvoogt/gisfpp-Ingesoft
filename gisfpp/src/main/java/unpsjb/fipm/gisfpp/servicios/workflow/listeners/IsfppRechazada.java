package unpsjb.fipm.gisfpp.servicios.workflow.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.zkoss.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.SolicitudNuevaIsfpp;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class IsfppRechazada implements ExecutionListener {
	
	private static final long serialVersionUID = 8431893449601330763L;
	private IServiciosIsfpp servIsfpp;
	private Logger log = UtilGisfpp.getLogger();

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			servIsfpp = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
			Integer idIsfpp = Integer.valueOf(execution.getProcessBusinessKey());
			Isfpp isfppRechazada = servIsfpp.getInstancia(idIsfpp);
			String motivo  = (String) execution.getVariable(SolicitudNuevaIsfpp.VAR_MOTIVO_RECHAZO);
			isfppRechazada.setEstado(EEstadosIsfpp.RECHAZADA);
			isfppRechazada.setDetalle(motivo);
			servIsfpp.editar(isfppRechazada);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
		
	}

			
}
