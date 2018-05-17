package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.DaoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.OfertaActividad;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;
import unpsjb.fipm.gisfpp.util.GisfppException;

@Service("servProyecto")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosProyecto implements IServiciosProyecto {

	private DaoProyecto dao;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Proyecto instancia) throws Exception {
		try {
			dao.crear(instancia);
			return instancia.getId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Proyecto instancia) throws Exception {
		try {
			dao.actualizar(instancia);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Proyecto instancia) throws Exception {
		ResultadoValidacion resultado = ValidacionesProyecto.eliminarProyecto(instancia);
		if(resultado.isValido()){
			dao.eliminar(instancia);
		}else{
			throw new GisfppException(resultado.getMensaje());
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Proyecto getInstancia(Integer id) throws Exception {
		try {
			return dao.recuperarxId(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Proyecto> getListado() throws Exception {
		try {
			return dao.recuperarTodo();
		} catch (Exception e) {
			throw e;
		}
	}

	@Autowired(required = true)
	public void setDao(DaoProyecto dao) {
		this.dao = dao;
	}

	@Override
	public List<OfertaActividad> getAllOfertas() {
		try {
			return dao.getAllOfertas();
		} catch (Exception e) {
			throw e;
		}
	}

}// fin de la clase
