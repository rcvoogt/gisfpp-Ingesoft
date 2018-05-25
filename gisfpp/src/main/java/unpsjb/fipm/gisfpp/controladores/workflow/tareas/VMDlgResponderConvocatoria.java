package unpsjb.fipm.gisfpp.controladores.workflow.tareas;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.convocatoria.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocado;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorTareas;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflowImp;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.MySpringUtil;

public class VMDlgResponderConvocatoria {
	
	private GestorTareas servGTareas;
	private InfoTarea tarea;
	private String titulo;
	private String asignado;
	private GestorWorkflow servGWorkflow;
	private IServiciosConvocatoria servConvocatoria;	
	private IServiciosConvocado servConvocado;
	private Convocatoria convocatoria;
	private ProcessInstance instancia;
	@Init
	@NotifyChange("tarea")
	public void init() throws GisfppWorkflowException, Exception{
		@SuppressWarnings("unchecked")
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		tarea = (InfoTarea) args.get("tarea");
		asignado = tarea.getAsignado();
		tarea.getIdInstanciaProceso();
		servGWorkflow = MySpringUtil.getServicioGestorWkFl();
		servConvocatoria = MySpringUtil.getServicioConvocatoria();
		servConvocado = MySpringUtil.getServicioConvocado();
		servGTareas = (GestorTareas) SpringUtil.getBean("servGestionTareas");
		String idProceso = tarea.getIdInstanciaProceso();
		instancia = servGWorkflow.getProcessInstance(idProceso);
		
		String business = instancia.getBusinessKey();
		Integer integ = Integer.parseInt(business);
		System.out.println("id:"+ integ);
		convocatoria = servConvocatoria.getInstancia( integ);
		System.out.println("convocatoria:" + convocatoria.getId());
		
		
	}

	public InfoTarea getTarea() {
		return tarea;
	}

	@SuppressWarnings("static-access")
	@Command("completarRespuesta")
	public void completarRespuesta(@BindingParam("respuesta") boolean arg1) throws GisfppWorkflowException, InterruptedException{
		Map<String, Object> args = new HashMap<String, Object>();
		
		try {
			guardarRespuesta(arg1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		servGTareas.tratarTarea(tarea, args);
		
		Thread.currentThread().sleep(3000);
		
		//Refrescamos las listas de tareas y procesos en la vista de la bandeja de actividades.
		BindUtils.postGlobalCommand(null, null, "refrescarTareasAsignadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarTareasRealizadas", null);
		BindUtils.postGlobalCommand(null, null, "refrescarProcesosActivos", null);
		BindUtils.postGlobalCommand(null, null, "refrescarProcesosFinalizados", null);
		cerrar();
	}
	
	@Command("cancelar")
	public void cancelarTratamientoTarea(){
		cerrar();
	}
	
	private void guardarRespuesta(boolean respuesta) throws Exception {
		Convocado convocado = servConvocado.getConvocado(asignado, convocatoria);
		System.out.println("convocatoria: " + convocatoria.getId() +" asignado:"+ asignado + " respuesta:" + respuesta);
		
		if(respuesta) {
			convocado.setRespuesta(ERespuestaConvocado.ACEPTADA);
		}else {
			convocado.setRespuesta(ERespuestaConvocado.RECHAZADA);
		}
		servConvocado.editar(convocado);
		
		
		
	}
	
	private void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgResponderConvocatoria");
		dlg.detach();
	}
	
	public String getTitulo() throws Exception {
		return this.convocatoria.getConvocable().getTitulo();
	}
}
