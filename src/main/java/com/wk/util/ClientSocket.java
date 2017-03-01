package com.wk.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * 建立客户端，启用线程监听信息
 * @author jcwk
 *
 */
public class ClientSocket {
	private PrintWriter out ;
	private BufferedReader in ;
	private Socket socket;
	public PrintWriter getOut() {
		return out;
	}
	public BufferedReader getIn() {
		return in;
	}
	
	public void openSocket(String host , int port)throws Exception{
		try{
			socket = new Socket (host,port);	
			System.out.println(socket.getInetAddress());
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(),"UTF-8"));  
			out = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
			WriterSocket ws =new WriterSocket(in);
			new Thread(ws).start();
		}catch(UnknownHostException e){
			 System.out.println("未知的主机"+host+"异常！");
		}
		
	}
	//判断Socket是否链接
	public Boolean isServerClose(){ 
		   try{ 
		    socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信 
		    return false; 
		   }catch(Exception se){ 
		    return true; 
		   } 
		}
	
}
