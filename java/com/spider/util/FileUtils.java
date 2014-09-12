package com.spider.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.spider.util.EncoderUtil;

public class FileUtils {

	/**
	 * 写字节数组
	 * @param b
	 * @param string
	 * @return
	 */
	public static String writeByte(byte[] b, String string){
		
		string = EncoderUtil.encode(string);
		
		int p = string.lastIndexOf('/');
		String folder = string.substring(0, p);
		//String filename = string.substring(p+1);
		
		//System.out.println("路径："+string);
		File f = new File(folder);
		f.mkdirs();
		
		File file = new File(string);
		
		OutputStream os = null;
		
		try {
			os = new FileOutputStream(file);
			os.write(b);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return string;
	}
	
	
	
	/**
	 * 写字符串文件
	 * @param content
	 * @param path
	 * @return
	 */
	public static String writeByString(String content,String path){
		
		File file = new File(path); 
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (os == null)  return null;
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)); //一层一层装饰
		
		try {
			
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return path;
	}

}
