package cn.accessbright.blade.core.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.icitic.hrms.core.util.FileExporter;
import com.icitic.hrms.util.POITools;

/**
 * 
 * @author ll
 *
 */
abstract class ExcelTemplateExporter extends FileExporter {
	
	private String template;

	/**
	 * ���캯��
	 * 
	 * @param path
	 * @param titleName
	 * @param headerNames
	 *            ��ͷ�����ƣ�Ĭ��ΪString����
	 * @param data
	 */
	public ExcelTemplateExporter(String path, String template) {
		super(path);
		this.template = template;
	}

	protected void handleGenerateFile(String filepath) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(template);
			POITools tools = POITools.load(inputStream);

			handleExcel(tools);

			log.info("--------------------д���ļ�" + filepath + "------------------------");
			tools.dispose(new FileOutputStream(filepath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.debug("--------------------�ر��ļ����쳣" + e.getMessage() + "------------------------");
				}
			}
		}
	}

	protected abstract void handleExcel(POITools tools);
}
