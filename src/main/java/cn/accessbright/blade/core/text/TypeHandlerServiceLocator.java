package cn.accessbright.blade.core.text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TypeHandlerServiceLocator {
	private static TypeHandlerServiceLocator INSTANCE;

	private String defaultHandler = "string";
	private Map handlers = new HashMap();

	public DataTypeHandler getHandler(String type) {
		makeSureInited();
		String handlerType = handlers.containsKey(type) ? type : defaultHandler;
		return (DataTypeHandler) handlers.get(handlerType);
	}

	private void makeSureInited() {
		if (handlers.isEmpty()) {
			Properties prop = new Properties();
			InputStream propInput = null;
			try {
				propInput = this.getClass().getResourceAsStream("typeHandlers.properties");
				prop.load(propInput);
				Enumeration enumer = prop.propertyNames();
				while (enumer.hasMoreElements()) {
					String type = (String) enumer.nextElement();
					DataTypeHandler handler = (DataTypeHandler) Class.forName(prop.getProperty(type)).newInstance();
					handlers.put(type, handler);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (propInput != null)
					try {
						propInput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	public static TypeHandlerServiceLocator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TypeHandlerServiceLocator();
		}
		return INSTANCE;
	}
}