package unpsjb.fipm.gisfpp.servicios.convocatoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.TipoConvocatoria;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServicioSubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;

@Service("servConvocable")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosConvocable implements IServiciosConvocable {

	@Autowired
	private IServiciosProyecto servProyecto;
	@Autowired
	private IServicioSubProyecto servSubProyecto;
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
	public Convocable recuperarConvocable(Integer id, TipoConvocatoria tipoConvocable) throws Exception {
		if(tipoConvocable.equals(TipoConvocatoria.PROYECTO)) {
			return servProyecto.getInstancia(id);
		}
		if(tipoConvocable.equals(TipoConvocatoria.SUBPROYECTO)) {
			return servSubProyecto.getInstancia(id);
		}
		return null;
	}


}
