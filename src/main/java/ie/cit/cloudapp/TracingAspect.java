package ie.cit.cloudapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TracingAspect {
	private static Log log = LogFactory.getLog(TracingAspect.class);
	
	//[Modifiers] ReturnType [ClassType] MethodName ([Arguments]) [throws Exception]
	
	@Before("execution(* ie.cit.cloudapp.JdbcTodoRepository.*(..))")
	public void traceMethod(JoinPoint point) {
		String className = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();
		log.trace("method invoked:"+className+"#"+methodName);
		}

}
