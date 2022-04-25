package com.simulation.notebook.util;


import com.simulation.notebook.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Desc: 压缩包工具类
 * @author Administrator
 */
public class ZipUtil {
    private static Logger log = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 把文件集合打成zip压缩包
     * @param srcFiles 压缩文件集合
     * @param zipFile  zip文件名
     * @throws RuntimeException 异常
     */
    public static synchronized void zip(List<File> srcFiles, File zipFile) throws RuntimeException {
        if (zipFile == null) {
            return;
        }
        if (!zipFile.getName().endsWith(".zip")) {
            return;
        }
        ZipOutputStream zos = null;
        try {
            OutputStream out = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[2048];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                in.close();
                zos.closeEntry();
            }
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    log.error("ZipUtil toZip close exception" + e);
                }
            }
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("zipFile error from ZipUtils", e);
        }
    }

    // 解压zip文件到指定目录下，同时返回根目录
    public static synchronized void unzip(String zipFile, String location) {
        try {
            File localFile = new File(location);
            if (!localFile.isDirectory()) {
                localFile.mkdirs();
            }

            // 开始解压
            ZipEntry entry = null;
            String entryName = null;
            File fileInZip = null;
            int count = 0, bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            ZipFile zip = new ZipFile(zipFile);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
            // 循环对压缩包里的每一个文件进行解压
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                entryName = entry.getName();
                if (entryName.endsWith(".ini")){
                    continue;
                }

                fileInZip = new File(location + File.separator + entryName);
                if (entry.isDirectory()) {
                    fileInZip.mkdirs();
                } else {
                    // 写入文件
                    bos = new BufferedOutputStream(new FileOutputStream(fileInZip));
                    bis = new BufferedInputStream(zip.getInputStream(entry));
                    while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                        bos.write(buffer, 0, count);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("解压zip文件时异常！");
        }
    }

    /**
     * 压缩文件 --- 压缩文件夹下的文件
     * @param sourceFilePath 源文件路径
     * @param zipFilePath    压缩后文件存储路径
     * @param zipFilename    压缩文件名
     */

    public static void compressToZip(String sourceFilePath, String zipFilePath, String zipFilename) {
        File sourceFile = new File(sourceFilePath);
        File zipPath = new File(zipFilePath);
        if (!zipPath.exists()) {
            zipPath.mkdirs();
        }
        File zipFile = new File(zipPath + File.separator + zipFilename);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            writeZip(sourceFile, "", zos);
            //文件压缩完成后，删除被压缩文件
            boolean flag = deleteDir(sourceFile);
            log.info("删除被压缩文件[" + sourceFile + "]标志：{}", flag);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 遍历所有文件，压缩
     * @param file       源文件目录
     * @param parentPath 压缩文件目录
     * @param zos        文件流
     */
    public static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.isDirectory()) {
            //目录
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            //文件
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                //指定zip文件夹
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
    }

    /**
     * 删除文件夹
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //删除空文件夹
        return dir.delete();
    }
}
