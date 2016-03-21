package com.intelligence.activity.controller;

import java.io.DataOutputStream; 
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.FileWriter; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.InputStream;

import android.graphics.Bitmap;
import android.os.Environment;

import com.intelligence.activity.utils.WifiTools;

public class FileHelper { 
	//	private Context context; 
	/** SD卡是否存在**/ 
	private boolean hasSD = false; 
	/** SD卡的路径**/ 
	private String SDPATH; 
	/** 当前程序包的路径**/ 
	//	private String FILESPATH; 
	public FileHelper() { 
		//		this.context = context; 
		hasSD = Environment.getExternalStorageState().equals( 
				android.os.Environment.MEDIA_MOUNTED); 
		SDPATH = Environment.getExternalStorageDirectory().getPath();

		//		FILESPATH = this.context.getFilesDir().getPath(); 
	} 
	/** 
	 * 在SD卡上创建文件夹
	 * 
	 * @throws IOException 
	 */ 
	public File createSDFile(String fileName) throws IOException { 
		File file = new File(SDPATH + "/" + fileName); 
		if (!file.exists()) { 
			file.mkdirs(); 
		} 
		return file; 
	} 

	/**
	 * 删除文件夹
	 * @param file
	 */
	public void delf(){
		File file = new File(SDPATH + "/"+ WifiTools.filepath);
		deleteFile(file);
	}
	public void deleteFile(File file){
		if(file.isFile()){
			file.delete();
			return;
		}
		if(file.isDirectory()){
			File[] childFile = file.listFiles();
			if(childFile == null || childFile.length == 0){
				file.delete();
				return;
			}
			for(File f : childFile){
				deleteFile(f);
			}
			file.delete();
		}
	}

	/** 
	 * 删除SD卡上的文件 
	 * 
	 * @param fileName 
	 */ 
	public boolean deleteSDFile(String fileName) { 
		File file = new File(SDPATH + "/" + fileName); 
		if (file == null || !file.exists()) 
			return false; 
		if(file.isDirectory()){  
			File[] childFiles = file.listFiles();  

			for (int i = 0; i < childFiles.length; i++) { 
				//				System.out.println(childFiles[i]);
				//				File delfile = new File(childFiles[i]);
				childFiles[i].delete();

				//			            	deleteSDFile(childFiles[i]);  
			}  

		}  

		return file.delete(); 
	} 
	/** 
	 * 写入内容到SD卡中的txt文本中 
	 * str为内容 
	 */ 
	public void writeSDFile(String str,String fileName) 
	{ 
		try { 
			FileWriter fw = new FileWriter(SDPATH + "/" + fileName); 
			File f = new File(SDPATH + "/" + fileName); 
			fw.write(str); 
			FileOutputStream os = new FileOutputStream(f); 
			DataOutputStream out = new DataOutputStream(os); 
			//			out.writeShort(2); 
			//			out.writeUTF(""); 
			//			System.out.println(out); 
			fw.flush(); 
			fw.close(); 
			//			System.out.println(fw); 
		} catch (Exception e) { 
			e.getStackTrace();
		} 
	} 
	/** 
	 * 读取SD卡中文本文件 
	 * 
	 * @param fileName 
	 * @return 
	 */ 
	public String readSDFile(String fileName) { 
		StringBuffer sb = new StringBuffer(); 
		File file = new File(SDPATH + "//" + fileName); 
		try { 
			FileInputStream fis = new FileInputStream(file); 
			int c; 
			while ((c = fis.read()) != -1) { 
				sb.append((char) c); 
			} 
			fis.close(); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return sb.toString(); 
	} 
	//	public String getFILESPATH() { 
	//		return FILESPATH; 
	//	} 
	public String getSDPATH() { 
		return SDPATH; 
	} 
	public boolean hasSD() { 
		return hasSD; 
	} 


	public String writeSDImage(String fileName,Bitmap mBitmap){
		File f = new File(SDPATH + "//" + fileName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//		   DebugMessage.put("在保存图片时出错："+e.toString());
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SDPATH + "//" + fileName;
	}



	public String copyFile(String oldPath, String newPath) {   
		newPath = SDPATH + "//" + WifiTools.filepath+"/image/" + newPath;
		try {   
			int bytesum = 0;   
			int byteread = 0;   
			File oldfile = new File(oldPath);   
			if (oldfile.exists()) { //文件存在时   
				InputStream inStream = new FileInputStream(oldPath); //读入原文件   
				FileOutputStream fs = new FileOutputStream(newPath);   
				byte[] buffer = new byte[1444];   
				int length;   
				while ( (byteread = inStream.read(buffer)) != -1) {   
					bytesum += byteread; //字节数 文件大小   
					System.out.println(bytesum);   
					fs.write(buffer, 0, byteread);   
				}
				inStream.close();   
			}
		}   
		catch (Exception e) {   
			System.out.println("复制单个文件操作出错");   
			e.printStackTrace();   

		}   
		
		return newPath;

	}   

	/**  
	 * 复制整个文件夹内容  
	 * @param oldPath String 原文件路径 如：c:/fqf  
	 * @param newPath String 复制后路径 如：f:/fqf/ff  
	 * @return boolean  
	 */   
	public String copyFolder(String oldPath, String newPath) {   
		newPath = SDPATH + "//" +newPath;
		try {   
			(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹   
			File a=new File(oldPath);   
			String[] file=a.list();   
			File temp=null;   
			for (int i = 0; i < file.length; i++) {   
				if(oldPath.endsWith(File.separator)){   
					temp=new File(oldPath+file[i]);   
				}   
				else{   
					temp=new File(oldPath+File.separator+file[i]);   
				}   

				if(temp.isFile()){   
					FileInputStream input = new FileInputStream(temp);   
					FileOutputStream output = new FileOutputStream(newPath + "/" +   
							(temp.getName()).toString());   
					byte[] b = new byte[1024 * 5];   
					int len;   
					while ( (len = input.read(b)) != -1) {   
						output.write(b, 0, len);   
					}   
					output.flush();   
					output.close();   
					input.close();   
				}   
				if(temp.isDirectory()){//如果是子文件夹   
					copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);   
				}   
			}   
		}   
		catch (Exception e) {   
			System.out.println("复制整个文件夹内容操作出错");   
			e.printStackTrace();   

		}   
		return newPath;

	}  
} 