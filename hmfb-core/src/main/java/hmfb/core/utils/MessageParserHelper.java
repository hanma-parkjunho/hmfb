package hmfb.core.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import hmfb.core.annotation.FixedString;
import hmfb.core.constants.MessageFieldType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageParserHelper {
    private static final Logger CLOG = LoggerFactory.getLogger("CLOGGER");
    
    public static String getMessage(final Object parseObject) {
        if (CLOG.isDebugEnabled()) {
            CLOG.debug("Start..!");
        }

        final StringBuilder messageStr = new StringBuilder();
        Arrays.stream(parseObject.getClass().getDeclaredFields())
              .filter(field -> Objects.nonNull(AnnotationUtils.findAnnotation(field, FixedString.class)))
              .sorted(FixedString.FIELD_COMPARATOR)
              .forEach(field -> {
                  try {
                      final FixedString fixedString = field.getAnnotation(FixedString.class);
                      if ("Y".contentEquals( fixedString.grpYn())) {
                          if (field.getType() == java.util.List.class) {
                              final Object value = Optional.ofNullable(new PropertyDescriptor(field.getName(), parseObject.getClass()).getReadMethod()
                                       .invoke(parseObject)).orElse("");
                              List<?> list = (List<?>)value;
                              for (Object o : list) {
                                  messageStr.append(getMessage(o));
                              }
                              return;
                          } else {
                              /*Class<?> cls1 = Class.forName(fixedString.grpName());
                              Object obj = cls1.newInstance();*/
                              final Object value = Optional.ofNullable(new PropertyDescriptor(field.getName(), parseObject.getClass()).getReadMethod()
                                       .invoke(parseObject)).orElse("");
                              messageStr.append(getMessage(value));
                              return;
                          }
                      }
                      final Object value = Optional.ofNullable(new PropertyDescriptor(field.getName(), parseObject.getClass()).getReadMethod()
                                           .invoke(parseObject)).orElse("");
                      final int strLength = fixedString.value();
                      
                      final byte[] data = value.toString().getBytes("euc-kr");
                      String valueString = new String(data, "euc-kr");

                      if (fixedString.encType() == 1) {
                          // enc 양방향
                      } else if (fixedString.encType() == 2) {
                          // enc 단방향
                      }
                      
                      final String cutFieldValue = cuteStringByByteLength(valueString, strLength);
                      messageStr.append(fixedString.type().fill(cutFieldValue, strLength));
                      
                      
                      
                 } catch (IllegalAccessException | InvocationTargetException | IntrospectionException | UnsupportedEncodingException e) {
                	e.printStackTrace();
                 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                  
            });
        
        return messageStr.toString();

    }
    
    public static String parseMessage(String message, Object parseObject) {
    	final AtomicInteger fieldIndex = new AtomicInteger();
    	
    	Arrays.stream(parseObject.getClass().getDeclaredFields())
    	      .filter(field -> Objects.nonNull(AnnotationUtils.findAnnotation(field, FixedString.class)))
    	      .sorted(FixedString.FIELD_COMPARATOR)
    	      .forEach(field -> {
    	    	  try {
    	    		  final FixedString fixedString = field.getAnnotation(FixedString.class);
    	    		  if ("Y".equals(fixedString.grpYn())) {
    	    			  if ("N".contentEquals(fixedString.grpCnt())) {
    	    				  int curOrder = fixedString.order();
    	    				  int loopCnt = 0;
    	    				  for(Field field1 : parseObject.getClass().getDeclaredFields()) {
    	    					  if(!field1.getName().equals("serialVersionID")) {
    	    						  FixedString fixedString1 = field.getAnnotation(FixedString.class);
    	    						  if (fixedString1.order() == (curOrder -1)) {
    	    							  // TODO : 이부분 사무실에서 확인 필요..!
    	    							  Object cnt = new PropertyDescriptor(field1.getName(), parseObject.getClass()).getReadMethod().invoke(parseObject);
    	    							  break;
    	    						  }
    	    					  }
    	    				  }
    	    				  List<Object> listObj = getListObj(fieldIndex, message, fixedString.grpName(), loopCnt);
    	    				  new PropertyDescriptor(field.getName(), parseObject.getClass()).getWriteMethod().invoke(parseObject, listObj);
    	    				  return;
    	    			  } else {
    	    				  Object obj = getObj(fieldIndex,message,fixedString.grpName());
    	    				  new PropertyDescriptor(field.getName(), parseObject.getClass()).getWriteMethod().invoke(parseObject, obj);
    	    				  return;
    	    			  }
    	    		  }
    	    		  
    	    		  final byte[] data = message.getBytes("euc-kr");
    	    		  final int strLength = fixedString.value();
    	    		  final byte[] fieldData = Arrays.copyOfRange(data, fieldIndex.get(), fieldIndex.get() + strLength);
    	    		  String value = new String(fieldData, "euc-kr").trim();
    	    		  
    	    		  if (fixedString.encType() == 1) {
    	    			  // 양방향 암호화
    	    		  }
    	    		  
    	    		  if (MessageFieldType.ALPHABET == fixedString.type()) {
    	    			  value = value.trim();
    	    		  } else {
    	    			  if (StringUtils.hasLength(value)) {
    	    				  value = Long.toString(Long.parseLong(value));
    	    			  }
    	    		  }
    	    		  
    	    		  new PropertyDescriptor(field.getName(), parseObject.getClass()).getWriteMethod().invoke(parseObject, value);
    	    		  fieldIndex.set(fieldIndex.get() + strLength);    	    		  
    	    		  
    	    	  } catch (Exception e) {
    	              e.printStackTrace();
    	    	  }
    	      });
    	if (message.length() <= fieldIndex.get()) {
    		return "";
    	}
    	
    	return message.substring(fieldIndex.get());
    }
    
    private static Object getObj(AtomicInteger fieldIndex, String message, String grpName) throws Exception {
		try {
            Class<?> cls1 = Class.forName(grpName);
            Object obj = cls1.newInstance();

            Arrays.stream(obj.getClass().getDeclaredFields())
            .filter(field -> Objects.nonNull(AnnotationUtils.findAnnotation(field, FixedString.class)))
            .sorted(FixedString.FIELD_COMPARATOR)
            .forEach(field -> {
                try {
                    final FixedString fixedString = field.getAnnotation(FixedString.class);
                    final byte[] data = message.getBytes("euc-kr");
                    final int strLength = fixedString.value();
                    final byte[] fieldData = Arrays.copyOfRange(data, fieldIndex.get(), fieldIndex.get() + strLength);
                    String value = new String(fieldData, "euc-kr").trim();

                    if (MessageFieldType.ALPHABET == fixedString.type()) {
                        value = value.trim();
                    } else {
                        if (StringUtils.hasLength(value)) {
                            value = Long.toString(Long.parseLong(value));
                        }
                    }
                    if (fixedString.encType() == 1) {
                        // 복호화
                    }
                    new PropertyDescriptor(field.getName(), obj.getClass()).getWriteMethod().invoke(obj,value);
                    fieldIndex.set(fieldIndex.get() + strLength);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return obj;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private static List<Object> getListObj(AtomicInteger fieldIndex, String message, String grpName, int cnt) throws Exception {
            List<Object> rtnObj = new ArrayList<Object>();
    		try {
            for (int i = 0; i < cnt; i++) {
            Class<?> cls1 = Class.forName(grpName);
            Object obj = cls1.newInstance();

            Arrays.stream(obj.getClass().getDeclaredFields())
            .filter(field -> Objects.nonNull(AnnotationUtils.findAnnotation(field, FixedString.class)))
            .sorted(FixedString.FIELD_COMPARATOR)
            .forEach(field -> {
                try {
                    final FixedString fixedString = field.getAnnotation(FixedString.class);
                    final byte[] data = message.getBytes("euc-kr");
                    final int strLength = fixedString.value();
                    final byte[] fieldData = Arrays.copyOfRange(data, fieldIndex.get(), fieldIndex.get() + strLength);
                    String value = new String(fieldData, "euc-kr").trim();

                    if (MessageFieldType.ALPHABET == fixedString.type()) {
                        value = value.trim();
                    } else {
                        if (StringUtils.hasLength(value)) {
                            value = Long.toString(Long.parseLong(value));
                        }
                    }
                    if (fixedString.encType() == 1) {
                        // 복호화
                    }
                    new PropertyDescriptor(field.getName(), obj.getClass()).getWriteMethod().invoke(obj,value);
                    fieldIndex.set(fieldIndex.get() + strLength);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            rtnObj.add(obj);
            }

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return rtnObj;
    }
    
    
    private static String cuteStringByByteLength(String s, int byteLimit) throws UnsupportedEncodingException {
    	String rtnStr = s;
    	int n = Math.min(byteLimit - 1, s.length() -1);
    	while (s.getBytes("euc-kr").length > byteLimit) {
    		rtnStr = s.substring(0, n--);
    	}
    	
    	return rtnStr;
    }
    
    public static String fillAfterSpace(String s, int byteLimit) {
        final StringBuilder builder = new StringBuilder();
        builder.append(s);
        int stringLen;
        try {
            stringLen = s.getBytes("euc-kr").length;
            for (int i = 0; i < byteLimit - stringLen; i++) {
                builder.append(" ");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    	return builder.toString();
    }
    
    public static String fillBeforeZeroSpace(String s, int byteLimit) {
        final StringBuilder builder = new StringBuilder();
        final int stringLen = s.getBytes().length;
        for (int i = 0; i < byteLimit - stringLen; i++) {
            builder.append(0);
        }
        builder.append(s);
    	return builder.toString();
    }
    
    
}
