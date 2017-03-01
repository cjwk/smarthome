package com.wk.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPSocketServer implements Runnable{
	private String ServerIP;
	public String getServerIP() {
		return ServerIP;
	}
	@Override
	public void run() {
		ServerSocket ss;
		try {
			ss = new ServerSocket(8625);
			Socket s = ss.accept();
			ServerIP = s.getInetAddress().toString();
			
			System.out.println("ServerIP:"+ServerIP);
			s.close();
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
