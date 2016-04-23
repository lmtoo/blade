package cn.accessbright.blade.core.mail;

import cn.accessbright.blade.core.event.NotifyEvent;
import cn.accessbright.blade.core.utils.Strings;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lile_ on 2016/4/23.
 */
public class VelocityMailEventListener extends MailEventListener {
    private VelocityEngineFactory engineFactory = new VelocityEngineFactory();

    public VelocityMailEventListener() {
        Properties prop = new Properties();
        prop.setProperty("input.encoding", "utf-8");
        engineFactory.setVelocityProperties(prop);
        engineFactory.setResourceLoaderPath("classpath:conf/template/");
        engineFactory.setResourceLoader(new PathMatchingResourcePatternResolver());
    }


    @Override
    protected String generateContent(NotifyEvent event) {
        return handleNotifyContent(event);
    }

    protected String templateKeyToFilename(String templateKey) {
        return templateKey + ".html";
    }

    protected String handleNotifyContent(NotifyEvent event) {
        String templateName = templateKeyToFilename(event.getTemplateName());
        Map context = generateContext(event);
        String content = mergeTemplateToString(templateName, context);
        if (!Strings.isEmpty(content)) return content;
        return event.getTitle();
    }

    private String mergeTemplateToString(String templateName, Map context) {
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
