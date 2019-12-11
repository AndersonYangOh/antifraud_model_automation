package dataHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.*;

public class Scpclient {
    static private Scpclient instance;
    static private Scpclient remoteInstance;

    static synchronized public Scpclient getInstance(Boolean isLocal,String IP, int port,
                                                     String username, String passward) {
    	if(isLocal){
//        	if (instance == null) {
                instance = new Scpclient(IP, port, username, passward);
//            }
        	return instance;
        }else{
//        	if (remoteInstance == null) {
        		remoteInstance = new Scpclient(IP, port, username, passward);
//            }
        	return remoteInstance;
        }
    	
    }

    public Scpclient(String IP, int port, String username, String passward) {
        this.ip = IP;
        this.port = port;
        this.username = username;
        this.password = passward;
    }

    /**
     * 远程拷贝文件
     * @param remoteFile  远程源文件路径
     * @param localTargetDirectory 本地存放文件路径
     */
    public int getFile(String remoteFile, String localTargetDirectory) {
        Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
             Logs.println("authentication failed");
             return -1;
            }
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, localTargetDirectory);
            conn.close();
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }

  

   /**
     * 远程上传文件
     * @param localFile 本地文件路径
     * @param remoteTargetDirectory  远程存放文件路径
     */
    public void putFile(String localFile, String remoteTargetDirectory) {
        Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
            	Logs.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            client.put(localFile, remoteTargetDirectory);
            conn.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * 远程上传文件
     * @param localFile 本地文件路径
     * @param remoteTargetDirectory  远程存放文件路径
     */
    public void putFile(String[] localFiles, String remoteTargetDirectory,String mode) {
        Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
            	Logs.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            if((mode == null) || (mode.length() == 0)){
                mode = "0777";
            }
            client.put(localFiles, remoteTargetDirectory,mode);
            conn.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

  /**
     * 远程上传文件并对上传文件重命名
     * @param localFile 本地文件路径
      *@param remoteFileName远程文件名
     * @param remoteTargetDirectory  远程存放文件路径
     *@param mode 默认"0600"，length=4
     */
    public void putFile(String localFile, String remoteTargetDirectory,String mode) {
        Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
            	Logs.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            if((mode == null) || (mode.length() == 0)){
                mode = "0777";
            }
            client.put(localFile, remoteTargetDirectory, mode);

            //重命名
//            Session sess = conn.openSession();
//            String tmpPathName = remoteTargetDirectory + File.separator+ remoteFileName;
//            String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
//            sess.execCommand("mv " + remoteFileName + " " + newPathName);

            conn.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    

	public String exec(String cmds) {
		InputStream in = null;
		String result = "";
		
        Connection conn = new Connection(ip,port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username,
                    password);
            if (isAuthenticated == false) {
            	Logs.println("authentication failed");
            }
			Session session = conn.openSession(); // 打开一个会话
			session.execCommand(cmds);
			Logs.println(cmds);
			
			in = session.getStdout();
			result = this.processStdout(in,"utf-8");
			Logs.println(result.trim());
			
			in = session.getStderr();
			String error = this.processStdout(in,"utf-8");
			Logs.println(error.trim());
            conn.close();
        } catch (IOException e) {
            ;
        }
		
		return result.trim();
	}
	

	/**
	 * 解析流获取字符串信息
	 * 
	 * @param in
	 *            输入流对象
	 * @param charset
	 *            字符集
	 * @return
	 */
	public String processStdout(InputStream in, String charset) {
//		byte[] buf = new byte[1024];
//		StringBuffer sb = new StringBuffer();
//		try {
//			while (in.read(buf) != -1) {
//				sb.append(new String(buf, charset));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return sb.toString();
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		int i;  
		try {
			while ((i = in.read()) != -1) {  
			    baos.write(i);  
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return baos.toString(); 
	}

    private String ip;
    private int port;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}