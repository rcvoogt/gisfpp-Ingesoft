package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;
import unpsjb.fipm.gisfpp.util.UtilGuiGisfpp;

public class MVListOfertasActividades {

	private IServicioSubProyecto servSubProyecto;
	private IServiciosProyecto servProyecto;
	private List<OfertaActividad> items;
	private String tituloPanel;
	private Logger log;
	
	@Init
	@NotifyChange({"items", "tituloPanel"})
	public void init() throws Exception{
		servSubProyecto = (IServicioSubProyecto) SpringUtil.getBean("servSubProyecto");
		servProyecto = (IServiciosProyecto) SpringUtil.getBean("servProyecto");
		items = servProyecto.getAllOfertas();
		tituloPanel ="Listado de Ofertas de Actividades.";
		log = UtilGisfpp.getLogger();	
	}

	public List<OfertaActividad> getItems() {
		return items;
	}

	public String getTituloPanel() {
		return tituloPanel;
	}
	
	@Command("salir")
	public void salir(){
		UtilGuiGisfpp.quitarPnlCentral("/panelCentro/pnlListOfertaActividades");
	}
	
	@Command("tratarSP")
	public void tratarSP(@BindingParam("item") OfertaActividad arg1,
			@BindingParam("modo") String arg2) throws Exception {
		if(arg1.getSubProyecto() != null) {
			try {
				Proyecto perteneceA = servProyecto.getInstancia(arg1.getSubProyecto().getPerteneceA().getId());
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("perteneceA", perteneceA);
				args.put("idItem", arg1.getSubProyecto().getId());
				args.put("modo", arg2);
				args.put("volverA", "/vistas/proyecto/listaOfertaActividades.zul");
				UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListOfertaActividades", "/vistas/proyecto/crudSubProyecto.zul", args);
			} catch (Exception e) {
				log.error(this.getClass().getName(), e);
				throw e;
			}
		}else {
			try {
				//Proyecto perteneceA = servProyecto.getInstancia(arg1.getSubProyecto().getPerteneceA().getId());
				Map<String, Object> args = new HashMap<String, Object>();
				//args.put("perteneceA", perteneceA);
				args.put("idItem", arg1.getProyecto().getId());
				args.put("modo", arg2);
				args.put("volverA", "/vistas/proyecto/listaOfertaActividades.zul");
				UtilGuiGisfpp.loadPnlCentral("/panelCentro/pnlListOfertaActividades", "/vistas/proyecto/crudProyecto.zul", args);
			} catch (Exception e) {
				log.error(this.getClass().getName(), e);
				throw e;
			}
		}
	}
	
}
