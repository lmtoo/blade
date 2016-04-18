package cn.accessbright.blade.core.text;


import cn.accessbright.blade.core.excel.ExcelRow;

public class FormatDescription {
    private String key;
    private String type;
    private String param;
    private String description;

    public FormatDescription(String key) {
        this(key, null);
    }

    public FormatDescription(String key, String type) {
        this(key, type, null);
    }

    public FormatDescription(String key, String type, String param) {
        this(key, type, param, null);
    }

    public FormatDescription(String key, String type, String param, String description) {
        this.key = key;
        this.type = type;
        this.param = param;
        this.description = description;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getParam() {
        return param;
    }

    public String getDescription() {
        return description;
    }

    public String format(Object target) {
        return TextFormatter.getValue(target, getKey(), getType(), getParam());
    }

    private boolean isInteger() {
        return "integer".equals(type);
    }

    private boolean isDouble() {
        return "double".equals(type);
    }

    public void toExcellRow(Object target, ExcelRow row) {
        String value = TextFormatter.getValue(target, getKey(), getType(), getParam());
        if (isDouble()) {
            row.addNumber(value);
        } else if (isInteger()) {
            row.addInteger(value);
        } else {
            row.addString(value);
        }
    }
}