package dataHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Config {

	// 测试环境
//	public static String user = "yucheng";
//	public static String pass = "123456";
//	public static String localHost = "192.168.199.136";
//	public static int localPort = 22;
//	public static String dir_project = "/opt/task/";
//	public static String dir_script = dir_project;
//	
//	public static String remoteUser = "yucheng";
//	public static String remotePass = "123456";
//	public static String remoteHost = "192.168.199.137";
//	public static int remotePort = 22;
//	public static String remoteDir_data = "/opt/data/";
	
	public static String user = "yucheng";
	public static String pass = "Yuc7#china";
	public static String localHost = "10.162.241.144";
	public static int localPort = 22;
	public static String dir_project = "/opt/task/";
	public static String dir_script = dir_project;
	
	public static String remoteUser = "yucheng";
	public static String remotePass = "Taoy10#china";
	public static String remoteHost = "10.236.2.182";
	public static int remotePort = 22;
	public static String remoteDir_data = "/opt/data/";	
	
	
	public static String jdbc = null;
	public static String dir_user = "/home/bills/";	//"/home/bills/";
	public static String basic_data = "data";	//"data";
	public static String basic_exp = null;	//"20191001";	//根据日期动态变化
	public static String dir_data = dir_user+basic_data+"/";	//dir_user+basic_data+"/";
	

	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new Config().initConfit();
	}
	
	
//	public static void initConfit(){
//		String contents = readToString("config.ini");
//		
//		String[]lines = contents.split("\r\n");
//		for(int i=0;i < lines.length;i++){
//			String[]temp = lines[i].trim().split("=");
//			if(temp.length==2&&temp[0].equals("jdbc")){
//				jdbc = temp[1];
//				System.out.println(jdbc);
//			}
//			if(temp.length==2&&temp[0].equals("user")){
//				user = temp[1];
//				System.out.println(user);
//			}
//			if(temp.length==2&&temp[0].equals("pass")){
//				pass = temp[1];
//				System.out.println(pass);
//			}
//			if(temp.length==2&&temp[0].equals("model")){
//				model = temp[1];
//				System.out.println(model);
//			}
//		}
//	}
//	
//	public static String readToString(String fileName) {  
//        String encoding = "GBk";  
//        File file = new File(fileName);  
//        Long filelength = file.length();  
//        byte[] filecontent = new byte[filelength.intValue()];  
//        try {  
//            FileInputStream in = new FileInputStream(file);  
//            in.read(filecontent);  
//            in.close();  
//        } catch (FileNotFoundException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//        try {  
//            return new String(filecontent, encoding);  
//        } catch (UnsupportedEncodingException e) {  
//            System.err.println("The OS does not support " + encoding);  
//            e.printStackTrace();  
//            return null;  
//        }  
//    }
	

}
