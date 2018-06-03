package unpsjb.fipm.gisfpp.controladores.convocatoria;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.Operaciones;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.servicios.convocatoria.IServiciosConvocatoria;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

public class MVDlgFiltroConvocatoria {

	private List<Convocatoria> listSinFiltro;
	
	private Usuario originante;
	
	private Boolean vigente;
	private Boolean porVencer;
	private Boolean misConvocatorias;
	
	private IServiciosConvocatoria servConvocatoria;
    private IServicioUsuario servUsuario;
	
	
	
	@Init
	public void init() {
		HashMap<String, Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		listSinFiltro = (List<Convocatoria>) map.get("listSinFiltro");
		servConvocatoria = (IServiciosConvocatoria) SpringUtil.getBean("servConvocatoria");
		servUsuario = (IServicioUsuario) SpringUtil.getBean("servUsuario");
		recuperarArgUltFiltro();
	}

	@Command("filtrar")
	public void filtrar() {
		List<Convocatoria> resultado = listSinFiltro.stream().filter(getPredicadoFiltro()).collect(Collectors.toList());
		HashMap<String, Object> prm = new HashMap<>();
		prm.put("listConFiltro", resultado);
		BindUtils.postGlobalCommand(null, null, "retornoDlgFiltroConvocatoria", prm);
		guardarArgUltFiltro();
		cerrar();
	}

	private Predicate<Convocatoria> getPredicadoFiltro() {
		Predicate<Convocatoria> filtro = Item -> true;
		
		if(originante != null) {
			if(originante.getId() != 0 ) {
				filtro = filtro.and(item -> item.getUsuarioOriginante().equals(originante));
			}
		}
		
		if(vigente != null) {
			if(vigente) {
				filtro = filtro.and(item -> item.isVigente(new Date()).equals(Boolean.valueOf(true)));
			}else {
				filtro = filtro.and(item -> item.isVigente(new Date()).equals(Boolean.valueOf(false)));
			}
		}
		
		if(porVencer != null) {
			if(porVencer) {
				filtro = filtro.and(item -> item.isPorVencer().equals(Boolean.valueOf(true)));
			}else {
				filtro = filtro.and(item -> item.isPorVencer().equals(Boolean.valueOf(false)));
			}
		}
		
		if(misConvocatorias != null) {
			if(misConvocatorias) {
				try {
					originante = servUsuario.getUsuario(UtilSecurity.getNickName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				filtro = filtro.and(item -> item.getUsuarioOriginante().equals(originante));
			}
		}
		
		return filtro;
	}

	private void cerrar() {
		Window thisDlg = (Window) Path.getComponent("/dlgFiltroConvocatoria");
		thisDlg.detach();
	}

	/**
	 * Mediante este método se guarda en la session web los criterios
	 * (argumentos )de busqueda establecidos en el último filtro aplicado al
	 * listado de Proyectos.
	 */
	private void guardarArgUltFiltro() {
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("originante", originante);
		map.put("vigente", vigente);
		map.put("porVencer", porVencer);
		map.put("misConvocatorias", misConvocatorias);
		
		Sessions.getCurrent().setAttribute("argUltFiltroConvocatorias", map);
	}

	/**
	 * Recuperamos de la session web, los criterios (argumentos) si los hubiera,
	 * del último filtro aplicado al listado de Proyectos.
	 */
	@NotifyChange({ "subProyecto", "estado" })
	private void recuperarArgUltFiltro() {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("argUltFiltroConvocatorias");
		if (map != null) {
			
			originante = (Usuario) map.get("originante");
			vigente = (Boolean) map.get("vigente");
			porVencer = (Boolean) map.get("porVencer");
			misConvocatorias = (Boolean) map.get("misConvocatorias");
		}
	}

	
	
	public List<Usuario> getUsuariosOriginantes() {
		
		try {
			List<Usuario> result = servUsuario.getUsuariosAptos(Operaciones.CREAR_CONVOCATORIA.name());
			result.add(0,new Usuario(new Integer(0),"-- Filtro vacio --"));
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	public Boolean getMisConvocatorias() {
		return misConvocatorias;
	}
	public void setMisConvocatorias(Boolean misConvocatorias) {
		this.misConvocatorias = misConvocatorias;
	}
	
	public Boolean getVigente() {
		return vigente;
	}
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
	
	public Boolean getPorVencer() {
		return porVencer;
	}
	public void setPorVencer(Boolean porVencer) {
		this.porVencer = porVencer;
	}
	
	public Usuario getOriginante() {
		return originante;
	}
	public void setOriginante(Usuario originante) {
		this.originante = originante;
	}

	

}// fin de la clase
