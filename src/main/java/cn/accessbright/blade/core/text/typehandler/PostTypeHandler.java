package cn.accessbright.blade.core.text.typehandler;

import cn.accessbright.blade.core.Tools;

public class PostTypeHandler extends DefaultTypeHandler implements DataTypeHandler {
	public Object handle(String param, Object value) {
		PostBO post = SysCacheTool.findPost((String) value);
		if (Tools.isEmpty(param)) {
			return post;
		}
		return Tools.getPropText(post, param);
	}
}
