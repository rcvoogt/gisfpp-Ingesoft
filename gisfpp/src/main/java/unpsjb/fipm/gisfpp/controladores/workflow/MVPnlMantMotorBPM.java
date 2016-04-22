package unpsjb.fipm.gisfpp.controladores.workflow;

import java.util.List;

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
import unpsjb.fipm.gisfpp.entidades.workflow.DefinicionProceso;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorMotorBpm;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVPnlMantMotorBPM {
	
	private GestorMotorBpm servGestorMotorBPM;
	private List<DefinicionProceso> listDefProcesos;
	
	@Init
	public void init() throws Exception{
		servGestorMotorBPM = (GestorMotorBpm) SpringUtil.getBean("servGestorMotorBpm");
		listDefProcesos = servGestorMotorBPM.getDefinicionProcesos();
	}

	public List<DefinicionProceso> getListDefProcesos() {
		return listDefProcesos;
	}
	
	@NotifyChange("*")
	@Command("activarDefProceso")
	public void activarDefProceso(@BindingParam("item") DefinicionProceso arg1) throws Exception{
		servGestorMotorBPM.activarDefinicionProceso(arg1.getIdDefinicion());
		arg1.setSuspendido(false);
	}
	
	@NotifyChange("*")
	@Command("suspenderDefProceso")
	public void suspenderDefProceso(@BindingParam("item") DefinicionProceso arg1) throws Exception{
		servGestorMotorBPM.suspenderDefinicionProceso(arg1.getIdDefinicion());
		arg1.setSuspendido(true);
	}
	
	@Command("eliminarDefProceso")
	public void eliminarDefProceso(@BindingParam("item") DefinicionProceso arg1) throws Exception{
		Messagebox.show("Desea realmente eliminar esta definición de proceso?\n"
				+ "Tenga en cuenta que esto eliminara también todas las derivaciones (Instancias de proceso, tareas de servicio, historial, etc.)"
				+ " basadas en esta definición.",	"Gisfpp: Eliminando Definicion de Proceso", new Button [] {Button.YES,  Button.NO}
			, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
				
				@Override
				public void onEvent(ClickEvent event) throws Exception {
					if (event.getName().equals(Messagebox.ON_YES)) {
						servGestorMotorBPM.eliminarDefinicionProceso(arg1.getIdDespliegue(), true);
						listDefProcesos.remove(arg1);
						BindUtils.postNotifyChange(null, null, getAutoReferencia(), "listDefProcesos");
						Clients.showNotification("Definición de Proceso eliminada", Clients.NOTIFICATION_TYPE_INFO, null, 
								"top_right", 3500);
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
