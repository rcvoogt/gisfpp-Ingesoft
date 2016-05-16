package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVDlgAsignarPracticantes {
	
	private Isfpp item;
	private IServiciosIsfpp servIsfpp;
	private Object origen;
	
	@Init
	@NotifyChange("item")
	public void init() throws Exception{
		servIsfpp = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		item = (Isfpp) args.get("isfpp");
		origen = args.get("origen");
	}

	public Isfpp getItem() {
		return item;
	}
	
	@Command("seleccionar")
	public void seleccionarPracticante(){
		UtilGuiGisfpp.mostrarDialogoBox("vistas/persona/dlgLookupPersona.zul", null);
	}
	
	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("item")
	public void retornoSeleccion(@BindingParam("seleccion")PersonaFisica arg1){
		item.addPracticante(arg1);
	}
	
	@Command("guardar")
	public void guardar() throws Exception{
		try{
			if (item.getPracticantes() == null || item.getPracticantes().isEmpty()) {
				Messagebox.show("No se ha seleccionado ningún \"Practicante\" para asignar a la Isfpp.",
						"Gisfpp: Asignando Practicante", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			servIsfpp.editar(item);
			Clients.showNotification("Practicantes asignados a la Isfpp", Clients.NOTIFICATION_TYPE_INFO, null, 
					"top_right", 3500);
			if (origen!=null) {
				BindUtils.postNotifyChange(null, null, origen, "*");
			}
		}finally{
			cerrar();
		}
	}
	
	@Command("cerrar")
	public void cerrar(){
		Window thisDlg = (Window) Path.getComponent("/dlgAsignarPracticantes");
		thisDlg.detach();
	}

}
