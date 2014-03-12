/**
 * Jul 28, 2013
 * ThisSystemUtil.java
 * 
 */
package com.zit.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.zit.waeims.vo.UserVO;

/**
 * 工具类
 * @author 易君
 *
 */
public class ThisSystemUtil
{
	
   /**
    * 判断字符串是否为null或空字符串或全是空白字符（空格换行等）。
    * 如果传入多个字符串，只要其中有一个为none，则返回true
    * 
    *@author 易君
    * @param strs
    * @return
    */
   public static final boolean isNone(String... strs){
      for (String str : strs)if(str==null||str.trim().length()==0)return true;
      return false;
   }
   
   public static void main(String...args ){
	   try{
		   Method[] methods=UserVO.class.getDeclaredMethods();
		   for (Method method : methods) {
			   if(method.getName().startsWith("set")){
				   //System.out.println();
			   }else{
				   System.out.println("statement.setString(1, userVO."+method.getName()+"());");
			   }
		   }
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   
	   }
   }
   
}
