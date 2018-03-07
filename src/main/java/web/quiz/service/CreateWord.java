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
		//创建配置实例 
		@SuppressWarnings("deprecation")
		Configuration configuration = new Configuration();
		//设置编码
	    configuration.setDefaultEncoding("utf-8");
	    //ftl模板文件统一放至 template 包下面
	    configuration.setClassForTemplateLoading(this.getClass(),"/");
	    
		try {
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
		    e.printStackTrace();
		}		 
		
        //定义输出文件
		File outFile = new File(filePath+fileName);
		//如果输出目标文件夹不存在，则创建
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
		//输出word文件
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
    
	//get和set方法
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