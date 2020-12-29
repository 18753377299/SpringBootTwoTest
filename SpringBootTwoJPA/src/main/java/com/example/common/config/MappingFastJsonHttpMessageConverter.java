package com.example.common.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MappingFastJsonHttpMessageConverter extends AbstractHttpMessageConverter
{

    public SerializerFeature[] getSerializerFeature()
    {
        return serializerFeature;
    }

    public void setSerializerFeature(SerializerFeature serializerFeature[])
    {
        this.serializerFeature = (SerializerFeature[])serializerFeature.clone();
    }

    public MappingFastJsonHttpMessageConverter()
    {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    public boolean canRead(Class clazz, MediaType mediaType)
    {
        return true;
    }

    public boolean canWrite(Class clazz, MediaType mediaType)
    {
        return true;
    }

    protected boolean supports(Class clazz)
    {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
	protected Object readInternal(Class clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException{
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	Object  object = null;
    	try {    		
            int i;
            while((i = inputMessage.getBody().read()) != -1) 
                baos.write(i);
            /*if(List.class.isAssignableFrom(clazz)){
            	return JSON.parseArray(baos.toString(), clazz);
            };
            List o = JSON.parseArray(baos.toString(), clazz);
            if(o!=null){
             return  o.get(0);
            }
          
            
            return null;*/
//            object  = JSON.parseObject(baos.toString(),clazz);
            object= JSON.parseObject(new String(baos.toByteArray(), getContentTypeCharset(inputMessage.getHeaders().getContentType()).name()), clazz);
            System.out.println("{request}====>>"+object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return object;
    }

    protected void writeInternal(Object o, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException  {
//        String jsonString = JSON.toJSONString( o, serializerFeature);
    	try {
    		String jsonString = JSON.toJSONStringWithDateFormat(o,"yyyy-MM-dd HH:mm:ss", serializerFeature);
    		 if(jsonString.charAt(0) == '"') {
    	        	jsonString = jsonString.substring(0, jsonString.length() - 1).replaceFirst("\"", "").replaceAll("\\\\", "");
	         }
    		 System.out.println("{response}====>>"+jsonString);
	        OutputStream out = outputMessage.getBody();
	        out.write(jsonString.getBytes(DEFAULT_CHARSET.name()));
	        out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}		       
    }
    
    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        } else {
            return DEFAULT_CHARSET;
        }
    }
    
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private SerializerFeature serializerFeature[]= 
		{SerializerFeature.WriteMapNullValue,SerializerFeature.QuoteFieldNames,
    		   SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteNullStringAsEmpty,
    		   SerializerFeature.WriteNullListAsEmpty};
    
}
