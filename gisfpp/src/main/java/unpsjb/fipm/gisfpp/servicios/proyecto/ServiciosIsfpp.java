package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.IDaoIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servIsfpp")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosIsfpp implements IServiciosIsfpp {

	private IDaoIsfpp dao;
	private GestorWorkflow servGWkFl;

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Isfpp instancia) throws Exception {
		int idIsfpp = dao.crear(instancia);
		servGWkFl.instanciarProceso("Isfpp", "Crear", UtilSecurity.getNickName(), String.valueOf(idIsfpp));
		return idIsfpp;
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Isfpp instancia) throws Exception {
		dao.actualizar(instancia);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Isfpp instancia) throws Exception {
		ResultadoValidacion resultado = ValidacionesProyecto.eliminarIsfpp(instancia);
		if(resultado.isValido()){
			dao.eliminar(instancia);
		}else{
			throw new GisfppException(resultado.getMensaje());
		}
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public Isfpp getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Isfpp> getListado() throws Exception {
		return dao.recuperarTodo();
	}

	@Override
	@Transactional(value="gisfpp", readOnly = true)
	public List<Isfpp> getIsfpps(SubProyecto sp) throws Exception {
		return dao.getIsfpps(sp);
	}

	@Override
	@Transactional(value="gisfpp", readOnly=true)
	public SubProyecto getPerteneceA(Isfpp instancia) throws Exception {
		return dao.getPerteneceA(instancia);
	}
	
	@Autowired(required = true)
	protected void setDao(IDaoIsfpp dao) {
		this.dao = dao;
	}

	@Autowired(required=true)
	public void setServGWkFl(GestorWorkflow servGWkFl) {
		this.servGWkFl = servGWkFl;
	}
	
}// fin de la clase
