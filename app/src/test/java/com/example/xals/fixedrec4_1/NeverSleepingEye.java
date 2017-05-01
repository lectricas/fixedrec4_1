package com.example.xals.fixedrec4_1;

public class NeverSleepingEye implements java.lang.reflect.InvocationHandler {
    private Object obj;
 
	public NeverSleepingEye(Object f1){ obj = f1; }
 
	public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args)  
		throws Throwable {
		System.out.println("NeverSleepingEye invoke : " + method.getName());
		return method.invoke(obj, args) ;
	}
}