package unpsjb.fipm.gisfpp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.zkoss.spring.SpringUtil;

import unpsjb.fipm.gisfpp.entidades.persona.Usuario;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.staff.ECargosStaffFi;
import unpsjb.fipm.gisfpp.entidades.staff.StaffFI;
import unpsjb.fipm.gisfpp.entidades.workflow.EstadosTarea;
import unpsjb.fipm.gisfpp.entidades.workflow.InfoTarea;
import unpsjb.fipm.gisfpp.servicios.persona.IServicioUsuario;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;

/**
 * @author Jose Devia
 *
 */
public class UtilGisfpp {

	// Constantes utilizadas en el sistema
	public static final String MOD_NUEVO = "NUEVO";
	public static final String MOD_EDICION = "EDICION";
	public static final String MOD_VER = "VER";
	public static final long MILISEGUNDOSXDIA = 86400000;
	public static final int MILISEGUNDOSXHORA = 3600000;
	public static final int MILISEGUNDOSXMINUTO=60000;
	private static Logger logger;

	public static String getMensajeValidations(ConstraintViolationException cve) {
		StringBuffer acumulados = new StringBuffer("");
		int contador = 0;
		for (ConstraintViolation violation : cve.getConstraintViolations()) {
			contador = contador + 1;
			acumulados.append(contador + ") " + violation.getMessage() + "\n");
		}

		return new String(acumulados);
	}
	public static Logger getLogger() {
		if (logger == null) {
			logger = LoggerFactory.getLogger("logger_gisfpp");
		}
		return logger;
	}
	
	/**
	 * Devuelve el periodo de tiempo pasado como parámetro en milisegundos, en un formato de dias, horas y minutos.
	 * @param periodo en milisegundos (Integer)
	 * @return periodo representado en días, horas y minutos como String.
	 */
	public static String formatearTiempo(Long periodo){
		int dias, horas, minutos;
		int restoDias, restoHoras;
		
		if(periodo!=null){
			dias = (int) (periodo/MILISEGUNDOSXDIA);
			restoDias = (int) (periodo%MILISEGUNDOSXDIA);
			
			horas = restoDias/MILISEGUNDOSXHORA;
			restoHoras = restoDias%MILISEGUNDOSXHORA;
			
			minutos = restoHoras/MILISEGUNDOSXMINUTO;
			
			return (dias + " días, " + horas + " horas, " + minutos+" minutos.");
		}
		else{
			return "";
		}
	}
	
	public static String traducirPrioridad(int prioridad){
		String resultado=null;
		switch (prioridad) {
		case 50:{
			 resultado = "Alta";
			break;
		}	
		case 25:{
			resultado ="Media";
			break;
		}
		case 1:{
			resultado ="Baja";
			break;
		}
		default:
			resultado="Indefinida";
			break;
		}
		return resultado;
	}

	public static String getTituloIsfpp(String idIsfpp){
		Isfpp isfpp;
		if (idIsfpp==null || idIsfpp.isEmpty()) {
			return "";
		}
		try {
			IServiciosIsfpp serv = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
			isfpp = serv.getInstancia(Integer.valueOf(idIsfpp));
		} catch (Exception exc1) {
			throw new GisfppException("Se ha generado un error al intentar recuperar el título de la Isfpp con id: "+idIsfpp, exc1);
		}
		if(isfpp!=null){
			return isfpp.getTitulo();
		}
		return "";
	}
	
	public static String getStatusVencimiento(InfoTarea tarea){
		
		if(tarea.getEstado() == EstadosTarea.REALIZADA){
			return "gris";
		}
		if(tarea.getFecha_vencimiento()==null){
			return "verde";
		}
		int diasRestaVencimiento = (int) ((tarea.getFecha_vencimiento().getTime() - System.currentTimeMillis())/MILISEGUNDOSXDIA);
		int diasVencimientoTarea = (int) ((tarea.getFecha_vencimiento().getTime() - tarea.getFecha_inicio().getTime())/MILISEGUNDOSXDIA);
		
		if( (diasRestaVencimiento < 0) || (diasRestaVencimiento <= Math.round(diasVencimientoTarea * 0.1))){
			return "rojo";
		}
		if(diasRestaVencimiento <= Math.round(diasVencimientoTarea * 0.3)){
			return "amarillo";
		}else{
			return "verde";
		}
	}
	
	public static boolean isFechaPasada(Date fecha){
		return (fecha.before(new Date()));
	}
	
	public static String getProperty(String property) throws IOException{
		Properties properties = new Properties();
		InputStream stream =null;
		try {
			stream = UtilGisfpp.class.getResourceAsStream("/gisfpp.properties");
			properties.load(stream);
			return properties.getProperty(property);
		} catch (IOException exc) {
			getLogger().error("Clase: UtilGisfpp - Metodo: getProperty(String)" , exc);
			throw exc;
		}finally{
			if (stream!=null) {
				stream.close();
			}
		}
	}
	
	/**
	 * Devuelve una unica cadena (String) producto de la concatenación (separadas por una barra invertida "/") 
	 * de las cadenas contenidas en la lista (List<>) de cadenas pasada como parámetro a la función.  
	 * @param listaCadenas (List<String>)
	 * @return cadena: concatenación de cadenas separadas por "/" (String)
	 */
	public static String convertirEnCadena(List<String> listaCadenas) {
		Assert.notNull(listaCadenas, "El parámetro de la función \"convertirEnCadena\" es NULL");
		StringBuilder devolucion = new StringBuilder();
		for (Iterator iterator = listaCadenas.iterator(); iterator
				.hasNext();) {
			String cadena = (String) iterator.next();
			devolucion.append(cadena);
			if (iterator.hasNext()) {
				devolucion.append(" / ");
			}
		}
		return new String(devolucion);
	}
	
	public static String getMailsStaffFi(List<StaffFI> lista, ECargosStaffFi rol){
		StringBuilder resultadoDevuelto = new StringBuilder();
		Assert.notNull(lista, "La lista pasada como parámetro al método \"getMailsStaffFi(List<StaffFI>, ECargosStaffFi)\""
				+ " es NULL.");
		for (StaffFI miembroStaffFI : lista) {
			if (miembroStaffFI.getRol() == rol) {
				resultadoDevuelto.append(miembroStaffFI.getMiembro().getEmail()+",");
			}
		}
		if (resultadoDevuelto.length() > 0) {
			//quito la coma agregada al final de la cadena
			resultadoDevuelto.deleteCharAt(resultadoDevuelto.length()-1);
		}
		return new String(resultadoDevuelto);
	}
	
	public static String getUsuariosStaffFi(List<StaffFI> lista, ECargosStaffFi rol) throws Exception{
		StringBuilder resultadoDevuelto = new StringBuilder();
		IServicioUsuario servUsuario = MySpringUtil.getServicioUsuario();
		Usuario usuario;
		for (StaffFI miembroStaffFI : lista) {
			if (miembroStaffFI.getRol() == rol) {
				usuario = servUsuario.getUsuario(miembroStaffFI.getMiembro());
				resultadoDevuelto.append(usuario.getNickname()+",");
			}
		}
		if (resultadoDevuelto.length() > 0) {
			//quito la coma agregada al final de la cadena
			resultadoDevuelto.deleteCharAt(resultadoDevuelto.length()-1);
		}
		return new String(resultadoDevuelto);
	}
	
}// fin de la clase
