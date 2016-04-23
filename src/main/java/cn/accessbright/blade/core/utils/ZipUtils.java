package cn.accessbright.blade.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class ZipUtils {
    protected static Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 使用源文件的名称(包括扩展名)作为entry的名称，批量压缩文件成zip格式
     *
     * @param files
     * @param outputPath
     * @return
     */
    public static final String zip(String[] files, String outputPath) {
        Map entryMap = new HashMap();
        for (int i = 0; i < files.length; i++) {
            String path = files[i];
            String fileName = StringUtils.getFilename(path);
            entryMap.put(fileName, path);
        }
        return zip("", entryMap, outputPath, false);
    }

    /**
     * 批量压缩文件成zip格式，生成的zip文件随机生成一个名字，<br>
     * 源文件使用绝对路径，entry的名称是否附加上源文件的扩展名由useSourceExtension来指定
     *
     * @param files              源文件：<br>
     *                           key表示zip文件entry的名称<br>
     *                           value表示源文件的绝对路径
     * @param outputPath         输出zip文件的目录
     * @param useSourceExtension 是否向entry名称中附加源文件的扩展名
     * @return 生成的zip文件名称
     */
    public static final String zip(Map files, String outputPath, boolean useSourceExtension) {
        return zip("", files, outputPath, useSourceExtension);
    }

    /**
     * 批量压缩文件成zip格式，源文件使用相对于inputPath的路径，生成的zip文件随机生成一个名字<br>
     * entry的名称是否附加上源文件的扩展名由useSourceExtension来指定
     *
     * @param inputPath          源文件的文件夹目录
     * @param files              源文件：<br>
     *                           key表示zip文件entry的名称<br>
     *                           value表示源文件的相对于inputPath的路径
     * @param outputPath         输出zip文件的目录
     * @param useSourceExtension 是否向entry名称中附加源文件的扩展名
     * @return 生成的zip文件名称
     */
    public static final String zip(String inputPath, Map files, String outputPath, boolean useSourceExtension) {
        String zipFileName = Dates.current("yyyyMMddHHmmss") + new Random().nextInt(10000);
        return zip(inputPath, files, outputPath, zipFileName, useSourceExtension);
    }

    /**
     * 批量压缩文件成zip格式<br>
     * entry的名称是否附加上源文件的扩展名由useSourceExtension来指定
     *
     * @param inputPath          源文件的文件夹目录
     * @param files              源文件：<br>
     *                           key表示zip文件entry的名称<br>
     *                           value表示源文件的相对于inputPath的路径
     * @param outputPath         输出zip文件的目录
     * @param zipFileName        输出zip文件的名称，不带后缀
     * @param useSourceExtension entry名称是否使用源文件的扩展名
     * @return 生成的zip文件名称
     */
    public static final String zip(String inputPath, Map files, String outputPath, String zipFileName, boolean useSourceExtension) {
        try {
            String relativeFileName = zipFileName + ".zip";
            String file = outputPath + File.separator + relativeFileName;
            // 如果文件已存在，则不再生成
            if (!new File(file).exists()) {
                log.info("--------------------生成的文件名" + zipFileName + "------------------------");

                ZipOutputStream output = new ZipOutputStream(new FileOutputStream(file));

                Iterator iter = files.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) iter.next();

                    String filePath = inputPath + File.separator + (String) mapEntry.getValue();
                    String sourceExtension = StringUtils.getFilenameExtension(filePath);

                    String providedEntryName = (String) mapEntry.getKey();
                    String finalEntryName = (StringUtils.hasText(sourceExtension) && useSourceExtension) ? (providedEntryName + "." + sourceExtension) : providedEntryName;

                    byte[] bytes = FileCopyUtils.copyToByteArray(new File(filePath));
                    output.putNextEntry(new ZipEntry(finalEntryName));
                    output.write(bytes);
                    output.closeEntry();
                }

                output.flush();
                output.close();
            }
            return relativeFileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("--------------------异常" + e.getMessage() + "------------------------");
            return "";
        }
    }
}