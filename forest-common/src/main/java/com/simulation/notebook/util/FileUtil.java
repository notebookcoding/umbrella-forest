package com.simulation.notebook.util;

import com.simulation.notebook.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Objects;

/**
 * 文件工具类
 * @author cuigc
 * @since 20170713
 */
@Slf4j
public class FileUtil {

    /**
     * 文件大小进制
     */
    private static final int FILE_SIZE_HEX = 1024;

    /**
     * 返回{filePath}的文件，如果目录结构不存在，自动创建出来后，在返回此文件对象
     * @param filePath 文件路径
     * @return 返回文件对象
     */
    public static File getFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            File parentFolder = new File(file.getParent());
            if (!parentFolder.exists()) {
                if (!parentFolder.mkdirs()) {
                    throw new BusinessException("创建路径目录失败！");
                }
            }

            try {
                // 创建具体文件
                if (!file.createNewFile()) {
                    return null;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
        return file;
    }

    /**
     * 获得文件的后缀内容。如xxx.xlsx或xx.jpg
     * @param filename 文件名称
     * @return 返回文件的后缀内容xlsx或jpg ...
     */
    public static String getFileSuffix(String filename) {
        int idx = filename.lastIndexOf(".");
        if (idx == -1) {
            return null;
        }
        return filename.substring(idx + 1);
    }

    /**
     * 删除某个目录下的所有文件，同时删除
     * @param filePath 指定目录或文件
     * @return 删除结果boolean：true-删除成功；false-删除失败
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * 删除本地指定路径下的文件。{localFile}必须是指定的文件
     * @param file 本地文件对象
     * @return 删除结果boolean：true-删除成功；false-删除失败。有时返回true，但是文件未被删除，可能有流读取中
     */
    public static boolean deleteFile(File file) {
        if (file.isFile()) {
            return file.delete();
        } else {
            // 目录
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (!deleteFile(child)) {
                        return false;
                    }
                }
            }
            // 删除空目录
            return file.delete();
        }
    }

    /**
     * 将文件读流{in}内容用输出流{out}写到目的地址
     * @param in  输入流
     * @param out 输出流
     * @throws Exception 文件读写异常
     */
    public static void commonWrite(InputStream in, OutputStream out) throws Exception {
        // 创建一个缓冲区
        byte[] buffer = new byte[FILE_SIZE_HEX];
        // 判断输入流中的数据是否已经读完的标识
        int len;
        // 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
        while ((len = in.read(buffer)) > 0) {
            // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
            out.write(buffer, 0, len);
        }
        out.flush();
        // 关闭输入流
        in.close();
        // 关闭输出流
        out.close(); // 删除处理文件上传时生成的临时文件
    }

    /**
     * 将字节数转换成MB单位
     * @param size 文件大小。单位：Bit
     * @return 返回MB为单位的值
     */
    @SuppressWarnings("unused")
    public static String formatSize2MB(long size) {
        return (size / FILE_SIZE_HEX / FILE_SIZE_HEX) + "MB";
    }

    /**
     * 将字节数转换成KB单位
     * @param size 文件大小。单位：Bit
     * @return 返回KB为单位的值
     */
    @SuppressWarnings("unused")
    public static String formatSize2KB(long size) {
        return (size / FILE_SIZE_HEX) + "KB";
    }

    /**
     * 转换文件大小Bit值
     * @param size 文件大小。单位：Bit
     * @return 返回最大单位的单位值
     */
    @SuppressWarnings("unused")
    public static String formatSize(long size) {
        long tmp = size / FILE_SIZE_HEX;
        if (tmp > 0 && tmp < FILE_SIZE_HEX) {
            return tmp + "KB";
        } else if (tmp <= 0) {
            return size + "Bit";
        }
        tmp = tmp / FILE_SIZE_HEX;
        if (tmp < FILE_SIZE_HEX) {
            return tmp + "MB";
        }
        tmp = tmp / FILE_SIZE_HEX;
        if (tmp < FILE_SIZE_HEX) {
            return tmp + "GB";
        }
        tmp = tmp / FILE_SIZE_HEX;
        if (tmp < FILE_SIZE_HEX) {
            return tmp + "TB";
        }
        tmp = tmp / FILE_SIZE_HEX;
        if (tmp < FILE_SIZE_HEX) {
            return tmp + "PB";
        }
        return null;
    }

    /**
     * 判断文件是否坏损
     * @param path 文件全路径
     * @return 是否破损
     */
    @SuppressWarnings("unused")
    public static boolean verify(String path) {
        File file = new File(path);
        return verify(file);
    }

    /**
     * 判断文件是否坏损。方法实现来源于网络
     * @param file 文件
     * @return 是否破损
     */
    public static boolean verify(File file) {
        FileInputStream fis = null;
        byte[] bytes;
        try {
            // 要用到FileInputStream类的read()方法
            fis = new FileInputStream(file);
            // in.available()是文件的字节数
            bytes = new byte[fis.available()];
            // 把文件的字节一个一个地填到bytes数组中
            if (fis.read(bytes) <= 0) {
                return false;
            }
        } catch (IOException e) {
            log.error("判断文件是否坏损时异常：" + e.getMessage(), e);
            return false;
        } finally {
            if (Objects.nonNull(fis)) {
                try {
                    // 关闭流
                    fis.close();
                } catch (Exception e) {
                    // no operate
                }
            }
        }

        int length = bytes.length;
        int minLength = 2;
        if (length < minLength) {
            return false;
        }

        int b1 = bytes[length - 2];
        int b2 = bytes[length - 1];
        return b1 == -1 && b2 == -39;
    }
}
