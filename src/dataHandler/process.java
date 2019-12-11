package dataHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class process {
	
	private static String fileList[] = null; 

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
		process proc = new process();
		proc.schedule();
		
	}
	
	private static long PERIOD_TIME = 24 * 60 * 60 * 1000;
	public static long	COUNT = 0;
	void schedule(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //执行定时任务的时间
        Date date=calendar.getTime();

        Timer timer = new Timer();
        //为了避免若容器启动的时间晚于定时时间，在重启容器的时候会立刻执行该任务
        if (date.before(new Date())) {
            date.setTime(date.getTime()+PERIOD_TIME);
        }
        
        TimerTask task = new TimerTask(){
        	public void run(){
        		COUNT++;
        		Logs.println("The " + COUNT + ": start!");
        		process proc = new process();
        		proc.process();
        	}
        	
        };
        timer.schedule(task,date,PERIOD_TIME);
	}
	
	void process(){
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		c.set(Calendar.DATE,c.get(Calendar.DATE)-1);
		String dayBefore=new SimpleDateFormat("yyyyMMdd").format(c.getTime()); 
		Config.basic_exp = dayBefore;
		
		// 创建本地客户端，然后执行本地脚本运行
		Scpclient localclient = Scpclient.getInstance(true,Config.localHost, Config.localPort,Config.user, Config.pass);
		String cmds = Config.dir_script + "batch_transfer.sh " ;	//$1$2$3$4
		String paras = Config.basic_exp + " " + Config.dir_user + " " + Config.basic_data + " " + Config.dir_script;
		localclient.exec(cmds + paras);
		String result = localclient.exec("ls " + Config.dir_user+Config.basic_exp);
		if(result.length()>10){
			fileList = result.split("\\n"); 
		}else{
			fileList = new String[]{};
		}
		
		// 创建远程客户端，然后执行远程操作
		Scpclient remoteclient = Scpclient.getInstance(false,Config.remoteHost, Config.remotePort,Config.remoteUser, Config.remotePass);
		remoteclient.exec("mkdir " + Config.remoteDir_data+"tmp"+Config.basic_exp);
		for(int i=0;i<fileList.length;i++)
			fileList[i] = Config.dir_user + Config.basic_exp +"/"+ fileList[i];
		remoteclient.putFile(fileList, Config.remoteDir_data+"tmp"+Config.basic_exp, "0777");
		
		//清理本地临时文件并执行命令,延时20分钟执行
		Timer timer = new Timer();// 实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
            	Scpclient remoteclient = Scpclient.getInstance(false,Config.remoteHost, Config.remotePort,Config.remoteUser, Config.remotePass);
        		String cmds2 = Config.remoteDir_data +"tmp"+ Config.basic_exp + "/batch_import.sh " ;	//$1$2$3$4
        		String paras2 = Config.basic_exp + " " + Config.remoteDir_data + " " + Config.remoteDir_data +"tmp"+ Config.basic_exp + "/";
        		remoteclient.exec(cmds2 + paras2);
        		remoteclient.exec("chmod 775 " + Config.remoteDir_data+Config.basic_exp+"/import.sql");
        		remoteclient.exec("rm -rf " + Config.remoteDir_data +"tmp"+ Config.basic_exp);
        		remoteclient.exec("setsid /opt/lampp/bin/mysql -uunicom -pGdsaGdTd34$ -e \"source "+Config.remoteDir_data+Config.basic_exp+"/import.sql\"");
        		Scpclient localclient = Scpclient.getInstance(true,Config.localHost, Config.localPort,Config.user, Config.pass);
        		localclient.exec("rm -rf " + Config.dir_user+Config.basic_exp);
        		remoteclient.exec("rm -rf " + Config.remoteDir_data + Config.basic_exp);
        		Logs.println("The " + COUNT + ": progress finish!");
                this.cancel();
                
            }
        }, 600*1000);// 这里毫秒
	}

}
