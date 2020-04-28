package cn.zzl.boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 第三次修改
 * @author zzl
 *
 */
public class ServerThread {
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void start() throws Exception {
		
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress(9999));
		//开启服务
		while(true) {
			Socket accept = server.accept();
			executorService.execute(new ServerThreadTask(accept));
		}
	}
	
	public static class ServerThreadTask implements Runnable {
		private Socket accept; 
		public ServerThreadTask() {
			
		}
		public ServerThreadTask(Socket accept ) {
			this.accept = accept;
		};
		
		@Override
		public void run() {
			OutputStream out  = null;
			try {
				
				
				File file = new File("D:\\tools\\Git-2.19.2-64-bit.exe");
				Long length = file.length();
				
				InputStream fileIn = new FileInputStream(file);
				byte[] fileBuf = new byte[100];
				int len = -1 ;
				out  = accept.getOutputStream();
				//将文件长度发送给客户端，用于技术下载进度百分比
				
				System.out.println("开始发送数据..."+length);
				System.out.println();
				
				DecimalFormat df=new DecimalFormat("0.00");
				int aa = 0 ;
				while(( len = fileIn.read(fileBuf)) != -1) {
					out.write(fileBuf,0,len);
					aa = aa + len;
					System.out.println("文件传输中..." + df.format((float)(aa)/length) + "%");
					
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(out != null)out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("开启服务");
					start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
