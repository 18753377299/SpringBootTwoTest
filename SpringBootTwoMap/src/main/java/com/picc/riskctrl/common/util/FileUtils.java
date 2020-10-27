package com.picc.riskctrl.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*用于将台风内容从本地读取进行操作*/
public class FileUtils {
	/*将文本内容写到文件中*/
	public static void writeToFile(String result,String filePath) {
		File file = null;
        FileWriter fw = null;
        file = new File(filePath);
        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
         // 如果输出目标文件夹不存在，则创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fw = new FileWriter(file);
            fw.write(result);
            fw.flush();
            System.out.println("写数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	/*读取文本内容*/
	 public static String readFileContent(String  filePath){
		 File file = new File(filePath);
        StringBuilder result = new StringBuilder();
        try{
        	//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));       
            String s = null;
            while((s = br.readLine())!=null){
                //使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            System.out.println(result.toString());
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
