package cn.accessbright.blade.core.excel;

import cn.accessbright.blade.core.FileExporter;
import cn.accessbright.blade.core.POITools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author ll
 *
 */
abstract class ExcelTemplateExporter extends FileExporter {

	private String template;

	/**
	 * 构造函数
	 *
	 * @param path
	 * @param template
	 *            列头的名称，默认为String数组
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

			log.info("--------------------写入文件" + filepath + "------------------------");
			tools.dispose(new FileOutputStream(filepath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.debug("--------------------关闭文件流异常" + e.getMessage() + "------------------------");
				}
			}
		}
	}

	protected abstract void handleExcel(POITools tools);
}
