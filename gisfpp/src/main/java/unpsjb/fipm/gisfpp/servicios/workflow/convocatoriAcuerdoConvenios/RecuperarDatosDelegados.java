package unpsjb.fipm.gisfpp.servicios.workflow.convocatoriAcuerdoConvenios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;

import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.servicios.staff.IServiciosStaffFI;
import unpsjb.fipm.gisfpp.util.MySpringUtil;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class RecuperarDatosDelegados implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		try {
			IServiciosStaffFI servStaffFi = MySpringUtil.getServicioStaffFi();
			List<StaffFI> staffFi = servStaffFi.getListado();
			
			String mails = UtilGisfpp.getMailsStaffFi(staffFi, ECargosStaffFi.DELEGADO);
			String usuarios = UtilGisfpp.getUsuariosStaffFi(staffFi, ECargosStaffFi.DELEGADO);
			
			Map<String, Object> variablesProceso = new HashMap<String, Object>();
			variablesProceso.put("mailsDelegados", mails);
			variablesProceso.put("usuariosDelegados", usuarios);
			
			execution.setVariables(variablesProceso);
		} catch (Exception exc) {
			Logger log = UtilGisfpp.getLogger();
			log.error("Exception Workflow: Excepcion lanzada al recuperar datos de delegados.", exc);
			throw exc;
		}
		
	}

}
