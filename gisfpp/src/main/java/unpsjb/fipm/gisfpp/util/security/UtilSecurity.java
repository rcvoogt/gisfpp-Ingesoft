package unpsjb.fipm.gisfpp.util.security;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.security.core.context.SecurityContextHolder;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.Usuario;

public class UtilSecurity {
	
	/**
	 * Método utilizado para generar un usuario por defecto para la persona
	 * pasada como parámetro. Se utiliza el numero de documento como "nickname"
	 * o si la persona no tiene establecido un numero de doc. se genera un "nickname"
	 * con el primer nombre de la Persona mas un número aleatorio. La contraseña
	 * por defecto es "gisfpp".
	 * @param persona (PersonaFisica)
	 * @return usuario default (Usuario)
	 */
	public static Usuario generarUsuario(PersonaFisica persona){
		Usuario usuarioGenerado = new Usuario();
		if(persona!=null){
			if((persona.getDni()!=null) && (!persona.getDni().isEmpty())){
				usuarioGenerado.setNickname(persona.getDni());
			}else{
				String nicknameDefault ="";
				int aleatorio = (int)(Math.random()*1000);
				String nombre = (persona.getNombre() == null || persona.getNombre().isEmpty())?"gisfpp":persona.getNombre();
				StringTokenizer tokens = new StringTokenizer(nombre);
				nicknameDefault = tokens.nextToken().toLowerCase() + aleatorio;
				usuarioGenerado.setNickname(nicknameDefault);
			}
			usuarioGenerado.setPassword("gisfpp");
			return usuarioGenerado;
		}else{
			return null;
		}
	}
	
	/**
	 * Devuelve el objeto Persona que representa al Usuario actualmente conectado.
	 * @return entidad Persona: Persona
	 */
	public static Usuario getUsuarioConectado(){
		if(isLogueado()){
			DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return detalle.getUsuario();
		}else{
			return null;
		}
	}
	
	/**
	 * Devuelve Verdadero (true) si el usuario se ha
	 * autenticado e iniciado sesion exitosamente.
	 * @return Verdadero o Falso.
	 */
	public static boolean isLogueado(){
		String nameUser = SecurityContextHolder.getContext().getAuthentication().getName();
		if(nameUser.equals("anonymousUser")){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Devuelve el nickname (Nombre de Usuario) del usuario actualmente conectado.
	 * @return nickname (String)
	 */
	public static String getNickName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	/**
	 * Devuelve una lista con los roles que posee el usuario actualmente conectado.
	 * @return List<RolUsuario>
	 */
	public static List<RolUsuario> getRoles(){
		DetalleUsuario detalle = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return detalle.getRoles();
	}
	
}//fin de la clase
