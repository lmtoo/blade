package cn.accessbright.blade.core.event;

import cn.accessbright.blade.core.utils.Strings;
import cn.accessbright.blade.core.utils.collections.Arrays;

import java.util.List;
import java.util.Map;

/**
 * 通知事件对象
 *
 * @author ll
 */
public abstract class NotifyEvent extends SystemEvent {
    private String titleTemplate = "您有一个“{0}”{1}";
    protected String[] ids;
    // 通知消息
    protected Object message;

    public NotifyEvent(Object source) {
        super(source);
    }

    public NotifyEvent(String[] ids, Object source, Object message) {
        this(source);
        this.message = message;
        this.ids = ids;
    }

    public NotifyEvent(String id, Object source, Object message) {
        this(new String[]{id}, source, message);
    }

    public NotifyEvent(List ids, Object source, Object message) {
        this((String[]) ids.toArray(new String[ids.size()]), source, message);
    }

    public String[] getIds() {
        return ids;
    }

    public Object getContent() {
        return message;
    }

    public boolean isValid() {
        if (source == null)
            return false;
        if (Arrays.isEmpty(ids))
            return false;
        for (int i = 0; i < ids.length; i++) {
            if (Strings.isEmpty(ids[i]))
                return false;
        }
        return true;
    }

    public String getTemplateName() {
        return "default";
    }

    protected String getTitleTemplate() {
        return titleTemplate + (isEndPoint() ? "。" : "，请至人力资源系统处理。");
    }

    public boolean isEndPoint() {
        return false;
    }

    public abstract String getJobId();

    public abstract Map getModleMap();

    public abstract String getTitle();
}