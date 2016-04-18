package cn.accessbright.blade.core.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.icitic.hrms.core.text.TextFormatter;
import com.icitic.hrms.util.POITools;
import com.icitic.hrms.util.POITools.ValueHandler;
import com.icitic.hrms.util.Tools;

/**
 * ��ʽ��Excel<br>
 * ���dataΪһ��Map��ֻ�����һ��sheet�ĸ�ʽ����<br>
 * ���dataΪһ��List���Ϊÿһ��ListԪ�ؿ�����һ��sheetģ�棬����ʽ�������ɾ����һ��sheetģ�档<br>
 * 
 * @author ll
 * 
 */
public class ExcelFormatTemplate extends ExcelTemplateExporter {
	private Object data;

	/**
	 * ���캯��
	 * 
	 * @param path
	 * @param titleName
	 * @param headerNames
	 *            ��ͷ�����ƣ�Ĭ��ΪString����
	 * @param data
	 */
	public ExcelFormatTemplate(String path, String template, Object data) {
		super(path, template);
		this.data = data;
	}

	protected void handleExcel(POITools tools) {
		if (data instanceof Map) {
			handleSingleSheet(tools, tools.getFirstSheet(), (Map) data);
		} else if (data instanceof List) {
			List dataList = (List) data;
			if (!Tools.isEmpty(dataList)) {
				for (int i = 0; i < dataList.size(); i++) {
					Map sheetData = (Map) dataList.get(i);
					String sheetName = generateSheetName(sheetData, i);
					Sheet theSheet = tools.cloneSheet(0, sheetName);
					handleSingleSheet(tools, theSheet, sheetData);
				}
			}
			tools.deleteSheet(0);
		}
	}

	private void handleSingleSheet(POITools tools, Sheet sheet,final Map data) {
		tools.forEachCell(sheet, new ValueHandler() {
			public String handle(int rowIndex, int cellIndex, String value) {
				if (!Tools.isEmpty(value)) return TextFormatter.format(value, data);
				return value;
			}
		});
	}

	protected String generateSheetName(Map data, int sheetIndex) {
		return "";
	}
}