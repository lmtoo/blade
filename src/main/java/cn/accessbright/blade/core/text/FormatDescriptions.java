package cn.accessbright.blade.core.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cn.accessbright.blade.core.utils.collections.Collections;
import cn.accessbright.blade.core.utils.collections.ObjectMapper;

public class FormatDescriptions {
	private List descriptions = new ArrayList();

	public void add(FormatDescription description) {
		descriptions.add(description);
	}

	public void add(String key) {
		add(key, null);
	}

	public void add(String key, String type) {
		add(key, type, null);
	}

	public void add(String key, String type, String param) {
		add(key, type, param, null);
	}

	public void add(String key, String type, String param, String description) {
		add(new FormatDescription(key, type, param, description));
	}

	public Iterator descIterator() {
		return Collections.map(descriptions, new ObjectMapper() {
			public Object map(Object target) {
				return ((FormatDescription) target).getDescription();
			}
		}).iterator();
	}

	public Iterator formatDescIterator() {
		return descriptions.iterator();
	}

	public Iterator propValueIterator(final Object target) {
		return Collections.map(descriptions, new ObjectMapper() {
			public Object map(Object fmtDesc) {
				return ((FormatDescription) fmtDesc).format(target);
			}
		}).iterator();
	}

	public List descs() {
		return Collections.map(descriptions, new ObjectMapper() {
			public Object map(Object target) {
				return ((FormatDescription) target).getDescription();
			}
		});
	}

	public List formatDescs() {
		return descriptions;
	}

	public List propValues(final Object target) {
		return Collections.map(descriptions, new ObjectMapper() {
			public Object map(Object fmtDesc) {
				return ((FormatDescription) fmtDesc).format(target);
			}
		});
	}
}