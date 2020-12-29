package com.example.common.utils;


import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 *
 * @Descript TODO (利用freemark生成word及zip)
 * @author yeting
 * @date 2019年3月19日
 *
 */
@Slf4j
public class WordUtils {
	
//    public static log log = log.getLogger(WordUtils.class);

    /**
     * 生成word文件(全局可用)
     * @param dataMap word中需要展示的动态数据，用map集合来保存
     * @param templateName word模板名称，例如：test.ftl
     * @param fileFullPath 要生成的文件全路径
     */
    @SuppressWarnings("unchecked")
    public static void createWord(Map dataMap, String templateName, String fileFullPath) {
        log.info("【createWord】：==>方法进入");
        log.info("【fileFullPath】：==>" + fileFullPath);
        log.info("【templateName】：==>" + templateName);

        try {
            // 创建配置实例
            Configuration configuration = new Configuration();
            log.info("【创建配置实例】：==>");

            // 设置编码
            configuration.setDefaultEncoding("UTF-8");
            log.info("【设置编码】：==>");

            // 设置处理空值
            configuration.setClassicCompatible(true);

            // 设置错误控制器
//            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

//            String pathName = Text.class.getResource("/template").getFile();
//            File templateFile = new File(pathName);
//            log.info("【pathName】：==>" + pathName);
//            log.info("【templateFile】：==>" + templateFile.getName());
//            configuration.setDirectoryForTemplateLoading(templateFile);

            // 设置ftl模板文件加载方式
            configuration.setClassForTemplateLoading(WordUtils.class,"/template");

            //创建文件
            File file = new File(fileFullPath);
            // 如果输出目标文件夹不存在，则创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            // 生成文件
            template.process(dataMap, out);

            // 清空缓存
            out.flush();
            // 关闭流
            out.close();

        } catch (Exception e) {
            log.info("【生成word文件出错】：==>" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 生成zip文件,根据文件路径不带子文件夹(全局可用)
     * @param zipfullPath 压缩后的zip文件全路径
     * @param fileFullPaths 压缩前的文件全路径数组
     */
    public static void createZip(String zipfullPath,String[] fileFullPaths) {
        InputStream inputStream = null;
        ZipOutputStream zip = null;

        try {
            zip = new ZipOutputStream(new FileOutputStream(zipfullPath));
            zip.setEncoding("gbk");

            for(String fullPath:fileFullPaths) {
                log.info("【createZip:fullPath】：==>" + fullPath);

                if(StringUtils.isBlank(fullPath)) {
                    continue;
                }

                //创建文件
                File file = new File(fullPath);
                String fileName = file.getName();

                //读文件流
                inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                inputStream.close();

                //将读取的文件输出到zip中
                zip.putNextEntry(new ZipEntry(fileName));
                zip.write(buffer);
                zip.closeEntry();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 生成的zip文件带子文件夹(全局可用)
     * @param zipfullPath 压缩后的zip文件全路径
     * @param fileFullPath 压缩前的文件全路径
     * @param isKeepDirStr 是否保留原来的目录结构,true:保留目录结构; false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    public static void createZip(String zipfullPath,String fileFullPath,boolean isKeepDirStr) {
        InputStream inputStream = null;
        ZipOutputStream zip = null;

        try {
            zip = new ZipOutputStream(new FileOutputStream(zipfullPath));
            zip.setEncoding("gbk");

            File file = new File(fileFullPath);

            compressZip(inputStream,zip,file, file.getName(), isKeepDirStr);//递归压缩

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 递归压缩方法(仅限于此类中用于压缩zip文件)
     * @param inputStream 输入流
     * @param zip zip输出流
     * @param sourceFile 源文件
     * @param fileName 文件夹名或文件名
     * @param isKeepDirStr 是否保留原来的目录结构,true:保留目录结构; false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compressZip(InputStream inputStream,ZipOutputStream zip, File sourceFile, String fileName,boolean isKeepDirStr) throws Exception{
        log.info("【compressZip:sourceFile】：==>" + sourceFile.getPath());
        log.info("【compressZip:fileName】：==>" + fileName);

        if(sourceFile.isFile()){
            //读文件流
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            //将读取的文件输出到zip中
            zip.putNextEntry(new ZipEntry(fileName));
            zip.write(buffer);
            zip.closeEntry();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(isKeepDirStr){
                    zip.putNextEntry(new ZipEntry(fileName + "/"));//空文件夹的处理
                    zip.closeEntry();// 没有文件，不需要文件的copy
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构,注意：file.getName()前面需要带上父文件夹的名字加一斜杠,不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    if (isKeepDirStr) {
                        compressZip(inputStream,zip,file,  fileName + "/" + file.getName(),isKeepDirStr);
                    } else {
                        compressZip(inputStream,zip, file, file.getName(),isKeepDirStr);
                    }
                }
            }
        }
    }

    /**
     * 下载生成的文件(全局可用)
     * @param fullPath 全路径
     * @param response
     */
    public static void downLoadFile(String fullPath, HttpServletResponse response) {
        log.info("【downLoadFile:fullPath】：==>" + fullPath);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            //创建文件
            File file = new File(fullPath);
            String fileName = file.getName();

            //读文件流
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            //清空响应
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream; charset=utf-8");
            // response.setContentType("application/msword");
            response.setHeader("Content-Disposition","attachment; filename=" + new String(fileName.getBytes(), "ISO8859-1"));
            response.setHeader("Content-Length", "" + file.length());

            //写文件流
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 下载网络文件到本地(主要用于下载简历附件)
     * @param urlAddress 网络url地址,为空时直接返回
     * @param fileFullPath 文件全路径
     */
    public static void createFromUrl(String urlAddress,String fileFullPath) {
        log.info("【service:开始下载网络文件】:==> 网上文件地址：" + urlAddress + "文件保存路径:" + fileFullPath);

//        if(StringUtil.isNullOrEmpty(urlAddress)) {
//            return ;
//        }
        if(StringUtils.isBlank(urlAddress)) {
        	 return ;
        }

        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream =null;
        try {

            URL url = new URL(urlAddress);

            dataInputStream = new DataInputStream(url.openStream());//打开网络输入流

            //创建文件
            File file = new File(fileFullPath);
            // 如果输出目标文件夹不存在，则创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            fileOutputStream = new FileOutputStream(file);//建立一个新的文件

            byte[] buffer = new byte[1024];
            int length;

            while((length = dataInputStream.read(buffer))>0){//开始填充数据
                fileOutputStream.write(buffer,0,length);
            }

            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(dataInputStream!=null) {
                    dataInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(fileOutputStream!=null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从网上或本地获得图片的base64码(主要用于插入生成word中的图片)
     * @param urlAddress 网络路径,二选一,目前有问题
     * @param pathAddress 本地路径，二选一
     * @return 返回base64码或null
     */
    public static String getImageBase(String urlAddress,String pathAddress) {
        byte[] buffer = null;
        InputStream inputStream = null;
        String imageCodeBase64 = null;

        try {
            if(!StringUtils.isBlank(urlAddress)){
                URL url = new URL(urlAddress);
                inputStream = new DataInputStream(url.openStream());//打开网络输入流
                buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
            }else if(!StringUtils.isBlank(pathAddress)){
                inputStream = new BufferedInputStream(new FileInputStream(new File(pathAddress)));//读文件流
                buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
            }else {
                return null;
            }

            imageCodeBase64 = Base64.encodeBase64String(buffer);
//            System.out.println(imageCodeBase64);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageCodeBase64;
    }
    /**
     * 刪除文件
     * @param filePath 文件路径
     * @return 返回true或false
     */
    public static boolean deleteFile(String filePath) {
    	boolean result = false;
    	File file=new File(filePath);
    	if(file.exists()) {
    		file.delete();
    		result = true;
    	}
    	return result;
    }




}