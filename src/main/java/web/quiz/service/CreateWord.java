package web.quiz.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template; 
import freemarker.template.TemplateException;


public class CreateWord {	
	private String templateName;
	private String fileName;
	private String filePath;
	private Map<String, Object> date; 
	
	public CreateWord(String templateName, String filePath, String fileName, Map<String, Object> date){
		this.templateName = templateName;
		this.filePath = filePath;
		this.fileName = fileName;
		this.date = date;
		createDoc();
	}

	public void createDoc(){		
		Template template = null;
		//��������ʵ�� 
		@SuppressWarnings("deprecation")
		Configuration configuration = new Configuration();
		//���ñ���
	    configuration.setDefaultEncoding("utf-8");
	    //ftlģ���ļ�ͳһ���� template ������
	    configuration.setClassForTemplateLoading(this.getClass(),"/");
	    
		try {
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
		    e.printStackTrace();
		}		 
		
        //��������ļ�
		File outFile = new File(filePath+fileName);
		//������Ŀ���ļ��в����ڣ��򴴽�
        if (!outFile.getParentFile().exists()   ){
            outFile.getParentFile().mkdirs();
        }
		Writer out = null;
		try {
		    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}		
		//���word�ļ�
		try {
			template.process(date, out);
	        out.flush();
	        out.close();
	    } catch (TemplateException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
	//get��set����
	public String getTemplateName() {
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Map<String, Object> getDate() {
		return date;
	}
	
	public void setDate(Map<String, Object> date) {
		this.date = date;
	}
			
}