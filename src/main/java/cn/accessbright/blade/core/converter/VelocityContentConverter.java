package cn.accessbright.blade.core.converter;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.util.Tools;

/**
 * Velocity����ת���������Խ�NofityEventת��Ϊhtml��������ʽ
 * 
 * @author ll
 * 
 */
public class VelocityContentConverter extends DefaultNotifyContentConverter implements Converter {
	private VelocityEngineFactory engineFactory = new VelocityEngineFactory();

	public VelocityContentConverter() {
		Properties prop = new Properties();
		prop.setProperty("input.encoding", "utf-8");
		engineFactory.setVelocityProperties(prop);
		engineFactory.setResourceLoaderPath("classpath:conf/template/");
		engineFactory.setResourceLoader(new PathMatchingResourcePatternResolver());
	}

	protected String templateKeyToFilename(String templateKey) {
		return templateKey + ".html";
	}
	
	protected Object handleNotifyContent(NotifyEvent event) {
		String templateName = templateKeyToFilename(event.getTemplateName());
		Map context = generateContext(event);
		String content = mergeTemplateToString(templateName, context);
		if (!Tools.isEmpty(content)) return content;
		return super.handleNotifyContent(event);
	}
	
	private String mergeTemplateToString(String templateName,Map context){
		try {
			VelocityEngine engine = engineFactory.createVelocityEngine();
			return VelocityEngineUtils.mergeTemplateIntoString(engine, templateName, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map generateContext(NotifyEvent event) {
		Map context = new LinkedHashMap();
		context.put("modle", event.getModleMap());
		context.put("title", event.getTitle());
		context.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getDate()));
		return context;
	}
}