package com.wk.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSocketServer {
	private static final String IPADDRESS = "255.255.255.255";
	private static final Integer PORE = 8888;
	private static final String STRSEND ="{\"sign\":\"d94fbf208f0863bc22edde4abbb9a36b\",\"json\":\"{\\\"method\\\":\\\"SearchHost\\\",\\\"param\\\":\\\"\\\"}\"}";
	
	public String  getHost() {

		try {
			TCPSocketServer tcpss = new TCPSocketServer(); 
			new Thread(tcpss).start();
			InetAddress adds = InetAddress.getByName(IPADDRESS);
		//	while(tcpss.getServerIP()==null){
				DatagramSocket ds = new DatagramSocket();
				DatagramPacket dp = 	new DatagramPacket(STRSEND.getBytes(),
						STRSEND.getBytes().length, adds, PORE);
				
				for(int x = 0 ; x < 6 ; x++){
					ds.send(dp);
					Thread.sleep(50);
				}
				
				ds.close();
				
				Thread.sleep(1000);
					// TODO Auto-generated catch block
		//	}
			
			
			System.out.println(tcpss.getServerIP()+"===============");
			System.out.println(tcpss.toString()+	"===============");
			return tcpss.getServerIP().substring(1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}

}
