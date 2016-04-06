package unpsjb.fipm.gisfpp.servicios.workflow.solicitudNuevaIsfpp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERolStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class IsfppAprobada implements ExecutionListener {

	private static final long serialVersionUID = 7829415302817324108L;
	private IServicioUsuario servUsuario;
	private IServiciosIsfpp servIsfpp;
	private GestorWorkflow servGWorkflow;
	private Isfpp isfpp;
	private PersonaFisica persona;
	private MiembroStaffIsfpp miembroStaff;
	private Logger log;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception, GisfppWorkflowException {
		try {
			servUsuario = MySpringUtil.getServicioUsuario();
			servIsfpp = MySpringUtil.getServicioIsfpp();
			servGWorkflow = MySpringUtil.getServicioGestorWkFl();
			log = UtilGisfpp.getLogger();
			Integer idIsfpp = Integer.valueOf(execution.getProcessBusinessKey());
			isfpp = servIsfpp.getInstancia(idIsfpp);
			
			String usuarioSolicitante = servGWorkflow.getIniciadorProceso(execution.getProcessInstanceId());
			persona = servUsuario.getUsuario(usuarioSolicitante).getPersona();
			
			miembroStaff = new MiembroStaffIsfpp(isfpp, persona, ERolStaffIsfpp.TUTOR_ACADEMICO);
			isfpp.addMiembroStaff(miembroStaff);
			
			servIsfpp.editar(isfpp);
			
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw new GisfppWorkflowException("Solicitud Nueva Isfpp: Se ha generado un error al intentar asignar al solicitante de la Isfpp"
					+ " como \"Tutor Acedémico\" de la misma." , e);
		}
		
	}

	
}
