package unpsjb.fipm.gisfpp.controladores.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;

import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorMotorBpm;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVPnlMantMotorBPM {
	
	private GestorMotorBpm servGestorMotorBPM;
	private List<DefinicionProceso> listDefProcesos;
	private List<InstanciaProceso> listInstanciasProcesos;
			
	@Init
	public void init() throws Exception{
		servGestorMotorBPM = (GestorMotorBpm) SpringUtil.getBean("servGestorMotorBpm");
		listDefProcesos = servGestorMotorBPM.getDefinicionProcesos();
		listInstanciasProcesos = servGestorMotorBPM.getProcesosEnEjecucion();
	}

	public List<DefinicionProceso> getListDefProcesos() {
		return listDefProcesos;
	}
	
	public List<InstanciaProceso> getListInstanciasProcesos(){
		return listInstanciasProcesos;
	}
	
	@NotifyChange("*")
	@Command("activarDefProceso")
	public void activarDefProceso(@BindingParam("item") DefinicionProceso arg1){
		servGestorMotorBPM.activarDefinicionProceso(arg1.getIdDefinicion());
		arg1.setSuspendido(false);
		Clients.showNotification("Definición de proceso activada", Clients.NOTIFICATION_TYPE_INFO, null
				, "top_right", 3500);
	}
	
	@NotifyChange("*")
	@Command("suspenderDefProceso")
	public void suspenderDefProceso(@BindingParam("item") DefinicionProceso arg1){
		servGestorMotorBPM.suspenderDefinicionProceso(arg1.getIdDefinicion());
		arg1.setSuspendido(true);
		Clients.showNotification("Definición de proceso suspendida", Clients.NOTIFICATION_TYPE_INFO, null
				, "top_right", 3500);
	}
	
	@Command("eliminarDefProceso")
	public void eliminarDefProceso(@BindingParam("item") DefinicionProceso arg1) throws Exception{
		Messagebox.show("Desea realmente eliminar esta definición de proceso?\n"
				+ "Tenga en cuenta que esto eliminara también todas las derivaciones (Instancias de proceso, tareas de servicio, historial, etc.)"
				+ " basadas en esta definición.",	"Workflow: Eliminando Definicion de Proceso", new Button [] {Button.YES,  Button.NO}
			, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
				
				@Override
				public void onEvent(ClickEvent event) throws Exception {
					if (event.getName().equals(Messagebox.ON_YES)) {
						servGestorMotorBPM.eliminarDefinicionProceso(arg1.getIdDespliegue(), true);
						listDefProcesos.remove(arg1);
						BindUtils.postNotifyChange(null, null, getAutoReferencia(), "listDefProcesos");
					}
				}
			});
	}
	
	@Command("verDetalleInstancia")
	public void verDetalleInstancia(@BindingParam("item") InstanciaProceso arg1) throws Exception{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("item", arg1);
		UtilGuiGisfpp.mostrarDialogoBox("vistas/workflow/dlgDetalleInstancia.zul", args);
	}
	
	@Command("activarInstancia")
	@NotifyChange("*")
	public void activarInstancia(@BindingParam("item") InstanciaProceso arg1){
		servGestorMotorBPM.activarInstanciaProceso(arg1.getIdInstancia());
		arg1.setSuspendido(false);
		Clients.showNotification("Instancia de proceso activa", Clients.NOTIFICATION_TYPE_INFO, null, 
				"top_right", 3500);
	}
	
	@Command("suspenderInstancia")
	@NotifyChange("*")
	public void suspenderInstancia(@BindingParam("item") InstanciaProceso arg1) {
		servGestorMotorBPM.suspenderInstanciaProceso(arg1.getIdInstancia());
		arg1.setSuspendido(true);
		Clients.showNotification("Instancia de proceso suspendida", Clients.NOTIFICATION_TYPE_INFO, null, 
				"top_right", 3500);
	}
	
	@Command("eliminarInstancia")
	public void eliminarInstancia(@BindingParam("item") InstanciaProceso arg1) throws Exception{
		Messagebox.show("Desea realmente eliminar esta instancia de proceso?", "Workflow: Eliminando Instancia de Proceso"
				, new Button[]{Button.YES, Button.NO}, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
					
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servGestorMotorBPM.eliminarInstanciaProceso(arg1.getIdInstancia());
							listInstanciasProcesos.remove(arg1);
							BindUtils.postNotifyChange(null, null, getAutoReferencia(), "listInstanciasProcesos");
						}
						
					}
				});
	}	
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlMantMotorBPM");
	}
	
	private MVPnlMantMotorBPM getAutoReferencia(){
		return this;
	}
	
}
