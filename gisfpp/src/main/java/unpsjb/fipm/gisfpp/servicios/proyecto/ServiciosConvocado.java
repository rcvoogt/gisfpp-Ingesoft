package unpsjb.fipm.gisfpp.servicios.proyecto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.proyecto.IDaoConvocado;
import unpsjb.fipm.gisfpp.dao.proyecto.IDaoConvocatoria;
import unpsjb.fipm.gisfpp.dao.proyecto.IDaoIsfpp;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocado;
import unpsjb.fipm.gisfpp.entidades.proyecto.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.entidades.workflow.InstanciaProceso;
import unpsjb.fipm.gisfpp.servicios.ResultadoValidacion;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorMotorBpm;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.GisfppException;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servConvocado")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosConvocado implements IServiciosConvocado{
	@Autowired
	private IDaoConvocado dao;
	@Autowired
	private GestorWorkflow servGWkFl;
	@Autowired
	private GestorMotorBpm servMotorWf;
	
	@Autowired 
	private IServicioUsuario servUsuario;
	
	
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Convocado instancia) throws Exception {
		int idConvocatoria = dao.crear(instancia);
		servGWkFl.instanciarProceso("Convocatoria", "Crear", UtilSecurity.getNickName(), String.valueOf(idConvocatoria));
		return idConvocatoria;
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Convocado instancia) throws Exception {
		dao.actualizar(instancia);
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Convocado instancia) throws Exception {
		dao.eliminar(instancia);
		
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Convocado getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}
	
	@Override
	public Convocado getConvocado(String usuario, Convocatoria convocatoria) throws Exception {
		Usuario user = servUsuario.getUsuario(usuario);
		
		for (Convocado convocado : convocatoria.getConvocados()) {
			if(convocado.getPersona().getUsuario().getNickname() == usuario){
				return convocado;
			}
		}	
		
		return null;
	}
	@Override
	public PersonaFisica getPersona(Integer idConvocado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PersonaFisica getConvocatoria(Integer idConvocado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Convocado> getListado() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	}// fin de la clase
