package unpsjb.fipm.gisfpp.controladores.integracion;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import unpsjb.fipm.gisfpp.entidades.cursada.Materia;
import unpsjb.fipm.gisfpp.entidades.xml.MateriaXML;
import unpsjb.fipm.gisfpp.entidades.xml.Materias;
import unpsjb.fipm.gisfpp.integracion.entidades.MateriaAdapter;
import unpsjb.fipm.gisfpp.servicios.cursada.IServiciosMateria;
import unpsjb.fipm.gisfpp.servicios.integracion.IServicioMateriaAdapter;

@Component
public class ControladorMaterias {
	@Autowired
	IServiciosMateria servMateria;
	@Autowired
	IServicioMateriaAdapter servMateriaAdapter;
	@Autowired
	RestImplementation restImplementation;
	private Materias materias;

	public void integrarMaterias() throws Exception {
		materias = getMaterias();
		persistirMaterias(materias);
	}	

	private void persistirMaterias(Materias materias) throws Exception {
		Materia materia;
		MateriaAdapter materiaAdapter;
		
		for(MateriaXML materiaXML: materias.getMaterias()) {
			materia = crearMateria(materiaXML);
			servMateria.actualizarOguardar(materia);
			materiaAdapter = crearMateriaAdapter(materiaXML,materia.getId());
			servMateriaAdapter.actualizarOguardar(materiaAdapter);
		}
	}	

	private MateriaAdapter crearMateriaAdapter(MateriaXML materiaXML, Integer integer) {
		MateriaAdapter materiaAdapter = new MateriaAdapter();
		materiaAdapter.setMateria(materiaXML.getCodigoMateria());
		materiaAdapter.setIdMateria(integer);
		return materiaAdapter;
	}



	private Materia crearMateria(MateriaXML materiaXML) {
		Materia materia = new Materia();
		materia.setNombre(materiaXML.getNombre());
		materia.setCodigoMateria(materiaXML.getCodigoMateria());
		return materia;
	}
	
	public Materias getMaterias() throws JsonParseException, JsonMappingException, IOException {
		HttpEntity<String> response = restImplementation.get(Rutas.SERVICIO_MATERIA, "application/xml");
		return adaptarMaterias(response.getBody());
	}

	private Materias adaptarMaterias(String xml) throws JsonParseException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		Materias value = xmlMapper.readValue(xml, Materias.class);
		return value;
	}

	public void setMaterias(Materias materias) {
		this.materias = materias;
	}

	
}
