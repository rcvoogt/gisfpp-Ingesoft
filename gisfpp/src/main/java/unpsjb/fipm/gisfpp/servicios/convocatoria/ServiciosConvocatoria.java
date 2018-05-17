package unpsjb.fipm.gisfpp.servicios.convocatoria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.dao.convocatoria.IDaoConvocatoria;
import unpsjb.fipm.gisfpp.dao.proyecto.DaoProyecto;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocable;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.Convocatoria;
import unpsjb.fipm.gisfpp.entidades.convocatoria.ERespuestaConvocado;
import unpsjb.fipm.gisfpp.entidades.convocatoria.TipoConvocatoria;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosProyecto;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorMotorBpm;
import unpsjb.fipm.gisfpp.servicios.workflow.GestorWorkflow;
import unpsjb.fipm.gisfpp.util.security.UtilSecurity;

@Service("servConvocatoria")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiciosConvocatoria implements IServiciosConvocatoria {
	@Autowired
	private IServiciosProyecto servProyecto;
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
		//servGWkFl.instanciarProceso("Convocatoria", "Crear", UtilSecurity.getNickName(), String.valueOf(idConvocatoria));
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
	@Transactional(value="gisfpp", readOnly = false)
	public void asignar(Convocado personaAcepto) throws Exception {
		Convocable convocable = personaAcepto.getConvocatoria().getConvocable();
		if(convocable.getTipoConvocatoria().equals(TipoConvocatoria.PROYECTO.toString())) {
			servProyecto.agregarMiembroStaff(personaAcepto.getPersona(),(Proyecto) convocable);			
		}
	}
	@Override
	public boolean isAsignado(PersonaFisica persona, Convocable convocable) throws Exception {
		if(convocable.getTipoConvocatoria().equals(TipoConvocatoria.PROYECTO.toString())) {
			MiembroStaffProyecto miembro = new MiembroStaffProyecto();
			miembro.setMiembro(persona);
			Proyecto proyecto = (Proyecto) convocable;	
			return proyecto.getStaff().contains(miembro);
		}
		return false;
	}
	@Override
	public void asignar(Set<Convocado> practicantes, Convocable convocable) throws Exception {
		Proyecto proyecto = (Proyecto) convocable;
		Proyecto p = servProyecto.getInstancia(proyecto.getId());
		if(convocable.getTipoConvocatoria().equals(TipoConvocatoria.PROYECTO.toString())) {
			servProyecto.agregarMiembrosStaff(convertir(practicantes),p);			
		}	
	}
	private Set<PersonaFisica> convertir(Set<Convocado> practicantes) {
		Set<PersonaFisica> personas = new HashSet<PersonaFisica>();
		for(Convocado practicante: practicantes) {
			personas.add(practicante.getPersona());
		}
		return personas;
	}
	

	
	
	}// fin de la clase
