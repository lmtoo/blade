package cn.accessbright.blade.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 文件导出器
 *
 * @author ll
 *
 */
public abstract class FileExporter implements Exportable {
	protected static Logger log = LoggerFactory.getLogger(FileExporter.class);

	// 生成的文件存放目录
	private String path;

	// 是否覆盖已经存在的文件
	private boolean override = false;

	public FileExporter(String path) {
		this.path = path;
	}

	public FileExporter(String path, boolean override) {
		this(path);
		this.override = override;
	}

	protected String fileExtension() {
		return "xls";
	}

	/**
	 * 获取要生成的文件名，默认为一个随机的Excel文件
	 *
	 * @return
	 */
	protected String fileName() {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date()) + new Random().nextInt(10000) + "." + fileExtension();
	}

	public String export() {
		String filename = "";
		try {
			filename = fileName();
			String file = path + File.separator + filename;
			// 如果文件已存在，则不再生成
			if (!new File(file).exists() || override) {
				log.info("--------------------生成的文件名" + filename + "------------------------");
				handleGenerateFile(file);
			}
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("--------------------异常" + e.getMessage() + "------------------------");
			return "";
		}
	}

	protected abstract void handleGenerateFile(String filepath) throws Exception;
}