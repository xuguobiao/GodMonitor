package com.kido.godmonitor.weaving.internal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class GodMonitor {

  private static final String POINTCUT_METHOD =
          "execution(@com.kido.godmonitor.weaving.GMonitor * *(..))"; // 通过GMonitor注解的方法

  private static final String POINTCUT_CONSTRUCTOR =
          "execution(@com.kido.godmonitor.weaving.GMonitor *.new(..))"; // 通过GMonitor注解的构造函数

  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotatedWithDebugTrace() {}

  @Pointcut(POINTCUT_CONSTRUCTOR)
  public void constructorAnnotatedDebugTrace() {}

  @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()") // 筛选出所有通过GMonitor注解的方法和构造函数
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    DebugLog.log(className, buildLogMessage(methodName, " before execution "));
    Object result = joinPoint.proceed(); // 注解所在的方法/构造函数的执行的地方
    DebugLog.log(className, buildLogMessage(methodName, " after execution "));

    return result;
  }

  /**
   * Create a log message.
   *
   * @param methodName A string with the method name.
   * @param info Extra info.
   * @return A string representing message.
   */
  private static String buildLogMessage(String methodName, String info) {
    StringBuilder message = new StringBuilder();
    message.append("GodMonitor --> ");
    message.append(methodName);
    message.append(" --> ");
    message.append("[");
    message.append(info);
    message.append("]");

    return message.toString();
  }
}
