package unpsjb.fipm.gisfpp.aspectos;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import unpsjb.fipm.gisfpp.util.GisfppBDException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

@Component
@Aspect
public class TratamientoExcepciones {
	
	private Logger log;
	
	@PostConstruct
	public void init(){
		log = UtilGisfpp.getLogger();
	}
	
	@AfterThrowing(pointcut="execution(* unpsjb.fipm.gisfpp.servicios..*.*(..))", throwing="exc")
	public void loggerException(JoinPoint jp, Exception exc){
		log.error("Clase: "+jp.getTarget().getClass()+" - Metodo: "+jp.getSignature(), exc);	
	}
	
	@AfterThrowing(pointcut="execution(* unpsjb.fipm.gisfpp.servicios..*.*(..))", throwing="exc")
	public void afterPersistirDAException(DataAccessException exc){
		throw new GisfppBDException (exc.getCause().getCause().getMessage());
	}

}
