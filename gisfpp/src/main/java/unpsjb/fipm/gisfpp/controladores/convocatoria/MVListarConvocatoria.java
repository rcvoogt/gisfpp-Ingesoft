package unpsjb.fipm.gisfpp.controladores.convocatoria;

import java.util.HashMap;
import java.util.List;


import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListarConvocatoria {

	private List<Convocatoria> convocatorias;
	private List<Convocatoria> temp;
	private IServiciosConvocatoria servicio;
	private HashMap<String, Object> args;
	private Convocable item;
	private boolean listadoFiltrado = false;
	private String titulo;

	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange({ "modo", "titulo", "creando", "editando", "ver" ,"convocatorias"})
	public void init() throws Exception {
		servicio = (IServiciosConvocatoria) SpringUtil.getBean("servConvocatoria");
		args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsListarConvocatoria");
		titulo = "Listado de Convocatorias";
		if(args != null) {
			item = (Convocable) args.get("masterId");
			titulo += " a " + item.getTipoConvocatoria() + item.toString() ;
		}
		if(args == null && item == null) {
			
			convocatorias = servicio.getListado();
		}else {
			convocatorias = item.getConvocatorias();
		}
			
		
	}

	@Command("verConvocatoria")
	public void verConvocatoria(@BindingParam("conv") Convocatoria convActual) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("convocatoria", convActual.getId());
		map.put("modo", UtilGisfpp.MOD_VER);
		map.put("volverA", "/vistas/convocatoria/listadoConvocatoria.zul");
		UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListConvocatoria", "/vistas/convocatoria/verConvocatoriaIndependiente.zul", map);
	}
	
	@Command("dlgFiltro")
	@NotifyChange("convocatorias")
	public void dlgFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("listSinFiltro", convocatorias);
		Window dlg = (Window) Executions.createComponents("vistas/convocatoria/dlgFiltrosConvocatoria.zul", null, map);
		dlg.doModal();
	}
	@GlobalCommand("retornoDlgFiltroConvocatoria")
	@NotifyChange({ "convocatorias", "listadoFiltrado" })
	public void retornoDlgFiltroIsfpp(@BindingParam("listConFiltro") List<Convocatoria> arg1) {
		// si el listado no tiene un filtro aplicado, mantenemos una referencia
		// a dicho listado original, para luego recuperarlo
		if (!listadoFiltrado) {
			temp = convocatorias;
		}
		convocatorias = arg1;
		listadoFiltrado = true;
		//titulo = "Listado Filtrado";
	}
	
	@Command("quitarFiltro")
	@NotifyChange({"convocatorias","listadoFiltrado"})
	public void quitarFiltro() {
		convocatorias = temp;
		listadoFiltrado = false;
		// Limpiamos los argumentos de listadoFiltrado de la sesion.
		Sessions.getCurrent().setAttribute("argUltFiltroConvocatoria", null);
	}
	
	
	
	@Command("salir")
	public void salir (){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListConvocatoria");
	}
	
	public List<Convocatoria> getConvocatorias() {
		return convocatorias;
	}

	public void setConvocatorias(List<Convocatoria> convocatorias) {
		this.convocatorias = convocatorias;
	}
	
	public boolean isListadoFiltrado() {
		return listadoFiltrado;
	}

	public MVListarConvocatoria getAutoReferencia(){
		return this;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
}// fin de la clase
