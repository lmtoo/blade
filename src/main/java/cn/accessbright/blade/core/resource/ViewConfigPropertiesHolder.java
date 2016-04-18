package cn.accessbright.blade.core.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.icitic.hrms.core.text.FormatDescriptions;
import com.icitic.hrms.core.util.ListableProperties;

public class ViewConfigPropertiesHolder {
	private static ViewConfigPropertiesHolder INSTANCE;

	private Map clazzProperties = new HashMap();
	private String location;

	public ViewConfigPropertiesHolder(String location) {
		this.location = location;
	}

	public FormatDescriptions getViewDescriptions(Class clazz) {
		makeSureInited(clazz);
		return (FormatDescriptions) clazzProperties.get(clazz);
	}

	private void makeSureInited(Class clazz) {
		if (!clazzProperties.containsKey(clazz)) {
			Properties prop = new ListableProperties();
			InputStream propInput = null;
			try {
				propInput = clazz.getResourceAsStream(clazz.getSimpleName() + "_" + location);
				prop.load(propInput);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				clazzProperties.put(clazz, splitAndSort(prop));
				if (propInput != null)
					try {
						propInput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	private FormatDescriptions splitAndSort(Properties prop) {
		FormatDescriptions desctiptions = new FormatDescriptions();
		Iterator iter = prop.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String[] value = prop.getProperty(key).split(",");
			int length = value.length;
			String description = length >= 1 ? value[0] : null;
			String type = length >= 2 ? value[1] : null;
			String param = length >= 3 ? value[2] : null;
			desctiptions.add(key, type, param, description);
		}
		return desctiptions;
	}

	public static ViewConfigPropertiesHolder getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ViewConfigPropertiesHolder("view.properties");
		}
		return INSTANCE;
	}
}