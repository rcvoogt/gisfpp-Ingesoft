package unpsjb.fipm.gisfpp.controladores.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.impl.BindSelectboxRenderer;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.Permiso;
import unpsjb.fipm.gisfpp.entidades.Roles;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVDlgNuevoPermiso {
	
	private Roles rol;
	private String titulo;
	private List<Operaciones> operaciones;
	
	@Init
	public void init(){
		Map<String, Object> parametros = (Map<String, Object>) Executions.getCurrent().getArg();
		rol = (Roles) parametros.get("rol");
		titulo = "Nuevo permiso para rol: "+rol.name();
		List<Permiso> permisos = (List<Permiso>) parametros.get("permisos");
		List<Operaciones> operacionesYaConcedidas = new ArrayList<Operaciones>();
		for (Permiso permiso : permisos) {
			operacionesYaConcedidas.add(permiso.getOperacion());
		}
		
		operaciones = new ArrayList<Operaciones>(Arrays.asList(Operaciones.values())); 
		operaciones.removeAll(operacionesYaConcedidas);		
	}
	
	public List<Operaciones> getOperaciones(){
		return operaciones;
	}

	public String getTitulo() {
		return titulo;
	}
	
	@Command("aceptar")
	public void aceptar(@BindingParam("operacion") Operaciones arg1){
		if(arg1 ==null){
			Messagebox.show("Seleccione una operación.", "Concediendo permiso", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		Permiso permiso = new Permiso();
		permiso.setRol(rol);
		permiso.setOperacion(arg1);
		permiso.setDescripcion(arg1.getTitulo());
		permiso.setFecha_creacion(new Date());
		permiso.setUsuario_creador(UtilSecurity.getNickName());
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("permiso", permiso);
		BindUtils.postGlobalCommand(null, null, "retornoNuevoPermiso", args);
		
		cerrar();
	}
	
	@Command("cerrar")
	public void cerrar(){
		Window dlg = (Window) Path.getComponent("/dlgNuevoPermiso");
		dlg.detach();
	}
	
}
