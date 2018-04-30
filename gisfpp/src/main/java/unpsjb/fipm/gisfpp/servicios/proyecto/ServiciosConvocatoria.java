package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.IDaoConvocatoria;
import unpsjb.fipm.gisfpp.dao.proyecto.IDaoIsfpp;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorMotorBpm;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servConvocatoria")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosConvocatoria implements IServiciosConvocatoria {
	@Autowired
	private IDaoConvocatoria dao;
	
	
	@Override
	public Integer persistir(Convocatoria instancia) throws Exception {
		int idConvocatoria = dao.crear(instancia);
		
		return idConvocatoria;
	}
	@Override
	public void editar(Convocatoria instancia) throws Exception {
		dao.actualizar(instancia);
	}
	@Override
	public void eliminar(Convocatoria instancia) throws Exception {
		dao.eliminar(instancia);
		
	}
	@Override
	public Convocatoria getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}
	@Override
	public List<Convocatoria> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	@Override
	public Isfpp getIsfpp(Integer idConvocatoria) throws Exception {
		return dao.getIsfpp(idConvocatoria);
	}
	@Override
	public List<Convocado> getConvocados(Integer idConvocatoria) throws Exception {
		System.out.println("test");
		return dao.getConvocados(idConvocatoria);
	}
	@Override
	public int getCantidadConvocados(Integer idConvocatoria) throws Exception {
		return dao.getCantidadConvocados(idConvocatoria);
	}

	}// fin de la clase
