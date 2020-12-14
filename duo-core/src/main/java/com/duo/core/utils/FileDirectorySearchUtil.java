package com.duo.core.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FileDirectorySearchUtil {
/**  
* 查询指定目录下的所有文件  
* @param args  
*/    
	public static void main(String[] args) {
		FileDirectorySearchUtil star=new FileDirectorySearchUtil();
	//     String path="d:";    
	//     File war=new File(path);    
	//     int count=star.findTxtFileCount(war,"exe");    
	//     System.out.println("----: " + count);   
	
		File[] files = star.getFiles("E:/bzcx/xxx", "xxx.rar");
		for(int i=0;i<files.length;i++){
			System.out.println(files[i]);   
		}   
	}    
/**  
 * 创建查询指定目录下文件的方法  
 *   
 * @param allList  
 *            指定目录  
 * @param endName  
 *            指定以“”结尾的文件  
 * @return 得到的文件数目  
 */    
	public int findTxtFileCount(File allList,String endName){    
		int textCount=0;    
		// 创建fileArray名字的数组   
		File[] fileArray= allList.listFiles();    
		// 如果传进来一个以文件作为对象的allList 返回0   
		if(null==fileArray){    
			return 0;    
		}    
		// 偏历目录下的文件   
		for(int i=0;i<fileArray.length;i++){    
			// 如果是个目录   
			if(fileArray[i].isDirectory()){    
				// System.out.println("目录: "+fileArray[i].getAbsolutePath());   
				// 递归调用   
				textCount+=findTxtFileCount(fileArray[i].getAbsoluteFile(),endName);    
				// 如果是文件   
			}else if(fileArray[i].isFile()){    
				// 如果是以“”结尾的文件   
				if(fileArray[i].getName().endsWith(endName)){    
					// 展示文件   
					System.out.println("exe文件: "+fileArray[i].getAbsolutePath());    
					// 所有以“”结尾的文件相加   
					textCount++;    
				}    
			}    
		}    
		return textCount;    
	}    
/**  
 * 在本文件夹下查找  
 * @param s String 文件名  
 * @return File[] 找到的文件  
 */  
	public static File[] getFiles(String s){   
		return getFiles("./",s);   
	}   
/**  
 * 获取文件  
 * 可以根据正则表达式查找  
 * @param dir String 文件夹名称  
 * @param s String 查找文件名，可带*.?进行模糊查询  
 * @return File[] 找到的文件  
 */  
	public static File[] getFiles(String dir,String s) {   
		//开始的文件夹   
		File file = new File(dir);   
		s = s.replace('.', '#');   
		s = s.replaceAll("#", "\\\\.");   
		s = s.replace('*', '#');   
		s = s.replaceAll("#", ".*");   
		s = s.replace('?', '#');   
		s = s.replaceAll("#", ".?");   
		s = "^" + s + "$";   

		System.out.println(s);   
		Pattern p = Pattern.compile(s);   
		ArrayList list = filePattern(file, p);   

		File[] rtn = new File[list.size()];   
		list.toArray(rtn);   
		return rtn;
	}   

/**  
 * @param file File 起始文件夹  
 * @param p Pattern 匹配类型  
 * @return ArrayList 其文件夹下的文件夹  
 */  

	private static ArrayList filePattern(File file, Pattern p) {   
		if (file == null) {   
			return null;   
		}else if (file.isFile()) {   
			Matcher fMatcher = p.matcher(file.getName());   
			if (fMatcher.matches()) {   
				ArrayList list = new ArrayList();   
				list.add(file);   
				return list;   
			}   
		}else if (file.isDirectory()) {   
			File[] files = file.listFiles();   
			if (files != null && files.length > 0) {   
				ArrayList list = new ArrayList();   
				for (int i = 0; i < files.length; i++) {   
					ArrayList rlist = filePattern(files[i], p);   
					if (rlist != null) {
						list.addAll(rlist);   
					}   
				}   
				return list;
			}   
		}   
		return null;
	}   
  
}  

