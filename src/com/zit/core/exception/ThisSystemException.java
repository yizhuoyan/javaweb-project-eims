package com.zit.core.exception;

/**
 * 定义整个系统的异常，可通过type区别不同的异常类型。
 * @author ben
 *
 */
public class ThisSystemException extends RuntimeException {
	/**异常类型代号*/
	public final String code;
	/**默认代号*/
	private final static String DEFAULT_CODE="0000";
	/**默认异常信息*/
	private final static String DEFAULT_MESSAGE="系统异常，请联系管理员";

	
	/**
	 * @param code
	 */
	private  ThisSystemException(String code,String message,Throwable cause) {
		super(message,cause);
		this.code = code;
	}


	public static ThisSystemException create(String code,String message){
		return new ThisSystemException(code, message,null);
	}
	
	
	public static ThisSystemException create(String message,Throwable cause){
		return new ThisSystemException(DEFAULT_CODE, message,cause);
	}
	public static ThisSystemException create(Throwable cause){
		if(cause instanceof ThisSystemException){
			return (ThisSystemException)cause;
		}
		return new ThisSystemException(DEFAULT_CODE, DEFAULT_MESSAGE,cause);
	}
	public static ThisSystemException create(String message){
		return new ThisSystemException(DEFAULT_CODE, message, null);
	}
	public static ThisSystemException create(String code,String message, Throwable cause){
		return new ThisSystemException(code, message, cause);
	}





	/**
	 * @param message
	 * @param cause
	 */
	public ThisSystemException(String message, Throwable cause) {
		this(DEFAULT_CODE,message, cause);
		
	}


	/**
	 * @param message
	 */
	public ThisSystemException(String message) {
		this(DEFAULT_CODE,message,null);
		
	}


	
	
	
	
}
