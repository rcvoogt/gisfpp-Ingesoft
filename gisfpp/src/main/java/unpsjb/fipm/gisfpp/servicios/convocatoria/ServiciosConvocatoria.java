package unpsjb.fipm.gisfpp.servicios.convocatoria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.convocatoria.IDaoConvocatoria;
import unpsjb.fipm.gisfpp.dao.proyecto.IDaoIsfpp;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.ERespuestaConvocado;
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
	@Autowired
	private GestorWorkflow servGWkFl;
	@Autowired
	private GestorMotorBpm servMotorWf;
	
	
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Integer persistir(Convocatoria instancia) throws Exception {
		int idConvocatoria = dao.crear(instancia);
		//servGWkFl.instanciarProceso("Convocatoria", "Crear", instancia.getUsuarioOriginante().getNickname(), String.valueOf(idConvocatoria));
		return idConvocatoria;
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void editar(Convocatoria instancia) throws Exception {
		dao.actualizar(instancia);
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public void eliminar(Convocatoria instancia) throws Exception {
		dao.eliminar(instancia);
		
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Convocatoria getInstancia(Integer id) throws Exception {
		return dao.recuperarxId(id);
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public List<Convocatoria> getListado() throws Exception {
		return dao.recuperarTodo();
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public Isfpp getIsfpp(Integer idConvocatoria) throws Exception {
		return dao.getIsfpp(idConvocatoria);
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public List<Convocado> getConvocados(Integer idConvocatoria) throws Exception {
		System.out.println("test");
		return dao.getConvocados(idConvocatoria);
	}
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public int getCantidadConvocados(Integer idConvocatoria) throws Exception {
		return dao.getCantidadConvocados(idConvocatoria);
	}
	
	@Override
	@Transactional(value="gisfpp", readOnly = false)
	public List<Convocado> getConvocadosAceptadores(Integer idConvocatoria) throws Exception {
		List<Convocado> aux =dao.getConvocados(idConvocatoria); 
		List<Convocado> aceptadores = new ArrayList<Convocado>();
		for(Convocado c : aux){
			if(c.getRespuesta().equals(ERespuestaConvocado.ACEPTADA))
				aceptadores.add(c);	
		}
			
		return aceptadores;
	}
	@Override
	public void asignar(Convocado personaAcepto) {
		
	}
	@Override
	public boolean isAsignado(PersonaFisica persona, Convocable convocable) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	}// fin de la clase
