package cn.zzl.boot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DecimalFormat;
/**
 * 99
 * @author zzl
 *
 */
public class SocketClient {

	public static void main(String[] args) {
		
		Socket socket = new Socket();
		InputStream inputStream = null;
		try {
			socket.connect(new InetSocketAddress("127.0.0.1", 9999));
			System.out.println("客户端连接了，请求下载数据。");
			
			inputStream = socket.getInputStream();
			byte[] bf = new byte[100];//接收服务端每次传输的数据，每次100kb
			int len = -1 ;
			File file = new File("C:\\Users\\zzl\\Desktop\\git.exe");
			OutputStream out = new FileOutputStream(file);
			
			DecimalFormat df=new DecimalFormat("0.00");
			int length = inputStream.available();
			System.out.println(length);
			int aa = 0 ;
			while(( len = inputStream.read(bf)) != -1) {
				aa = aa + len;
				out.write(bf, 0, len);
				//System.out.println("文件传输中..." + df.format((float)(aa)/length) + "%");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(inputStream != null)inputStream.close();
				if(socket != null)socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
