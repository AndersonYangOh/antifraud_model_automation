package dataHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logs {

	private static long PERIOD_TIME = 24 * 60 * 60 * 1000;
	public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 9);
        calendar.set(Calendar.SECOND, 0);
        //执行定时任务的时间
        Date date=calendar.getTime();

        Timer timer = new Timer();
        //任务在指定的时间开始进行重复的固定延迟执行
        TimerTask task = new TimerTask(){
        	public void run(){
        		Calendar c = Calendar.getInstance();
        		Date date = new Date();
        		c.setTime(date);
        		c.set(Calendar.DATE,c.get(Calendar.DATE)-1);
        		String dayBefore=new SimpleDateFormat("yyyyMMdd").format(c.getTime()); 
        		Config.basic_exp = dayBefore;
//        		Logs.println(Config.basic_exp);
        		
        		System.out.print("start!");
        		
        		//清理本地临时文件并执行命令,延时20分钟执行
        		Timer timer = new Timer();// 实例化Timer类
                timer.schedule(new TimerTask() {
                    public void run() {
                    	System.out.print("task!");
                        this.cancel();

                    }
                }, 2*1000);// 这里毫秒
                
                System.out.print("finish!");
        	}
        	
        };
        timer.schedule(task,date,5*1000);
	}

	public static void record(String record){
		Date cur = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(cur) + "    " + record);
		
		// 存储至日志系统
		/* do something */
	}
	
	public static void println(String record){
		Date cur = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(record.length() <= 1)
			return;
		
		Pattern p = Pattern.compile("\r|\n");
		Matcher m = p.matcher(record);
		record = m.replaceAll(" | ").trim();
		System.out.println(formatter.format(cur) + "    " + record);
		
		// 存储至日志系统
		/* do something */
	}
	
	

}
