package unpsjb.fipm.gisfpp.controladores.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.BindSelectboxRenderer;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.Permiso;
import unpsjb.fipm.gisfpp.entidades.Roles;
import unpsjb.fipm.gisfpp.servicios.IServiciosPermisos;

public class MVDlgPermisos {

	private IServiciosPermisos servPermisos;
	private List<Permiso> permisos;
	private MVDlgPermisos refDlgPermisos;//autoreferencia de esta clase utilizada en el método revocar permisos para poder
	//actualizar la vista.
	
	@Init
	public void init(){
		servPermisos = (IServiciosPermisos) SpringUtil.getBean("servicioPermisos");
		refDlgPermisos = this;
	}
	
	public List<Roles> getRoles(){
		return Arrays.asList(Roles.values());
	}
	
	public List<Permiso> getPermisos() {
		return permisos;
	}
	
	@NotifyChange("permisos")
	@Command("cambioRol")
	public void cambioRol(@BindingParam("item") Roles arg1) throws Exception{
		permisos = servPermisos.getPermisosxRol(arg1);
	}
	
	@Command("concederPermiso")
	public void concederPermiso(@BindingParam("rol") Roles arg1){
		if (arg1==null) {
			Messagebox.show("Seleccione el rol al cual le quiere conceder el permiso.", "Concediendo permiso"
					, Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("rol", arg1);
		parametros.put("permisos", permisos);
		Window dlg= (Window) Executions.createComponents("vistas/config/dlgNuevoPermiso.zul", null, parametros);
		dlg.doModal();
	}
	
	@NotifyChange("permisos")
	@GlobalCommand("retornoNuevoPermiso")
	public void retornoNuevoPermiso(@BindingParam("permiso") Permiso arg1) throws Exception{
		servPermisos.persistir(arg1);
		Clients.alert("Nuevo permiso concedido.", "Nuevo Permiso", Messagebox.INFORMATION);
		permisos.add(arg1);
	}
	
	@Command("revocarPermiso")
	public void revocarPermiso(@BindingParam("item") Permiso arg1) throws Exception{
		if(arg1==null){
			Messagebox.show("Seleccione la operación del permiso a revocar.", "Rovocando Permiso", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	
		Messagebox.show("Desea realmente revocar este Permiso ?", "Revocando Permiso", new Button [] {Button.YES, Button.NO},
				Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {

					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.ON_YES.equals(event.getName())) {
							servPermisos.eliminar(arg1);
							Clients.alert("Permiso revocado", "Revocando Permiso", Messagebox.INFORMATION);
							permisos.remove(arg1);
							BindUtils.postNotifyChange(null, null, refDlgPermisos, "permisos");
						}
					}
				});
	}
	
		
}
