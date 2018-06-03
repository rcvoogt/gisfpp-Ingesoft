package unpsjb.fipm.gisfpp.servicios.convocatoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.TipoConvocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;

@Service("servConvocable")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosConvocable implements IServiciosConvocable {

	@Autowired
	private IServiciosProyecto servProyecto;
	@Autowired
	private IServicioSubProyecto servSubProyecto;
	@Autowired
	private IServiciosIsfpp servIsfpp;

	
	@Override
	public Integer persistir(Convocable instancia) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editar(Convocable instancia) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminar(Convocable instancia) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Convocable getInstancia(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Convocable> getListado() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Convocable getInstancia(Convocable convocable) throws Exception {
		if (convocable.getTipoConvocatoria().equals(TipoConvocatoria.PROYECTO.toString())) {
			Proyecto p = (Proyecto) convocable;
			return servProyecto.getInstancia(p.getId());
		}
		if (convocable.getTipoConvocatoria().equals(TipoConvocatoria.SUBPROYECTO.toString())) {
			SubProyecto s = (SubProyecto) convocable;
			return servSubProyecto.getInstancia(s.getId());
		}
		if (convocable.getTipoConvocatoria().equals(TipoConvocatoria.ISFPP.toString())) {
			Isfpp i = (Isfpp) convocable;
			return servIsfpp.getInstancia(i.getId());
		}

		return null;
	}

	@Override
	public List<PersonaFisica> getMiembros(Convocable convocable) throws Exception {
		Convocable c = getInstancia(convocable);
		return c.getMiembros();
	}

}
