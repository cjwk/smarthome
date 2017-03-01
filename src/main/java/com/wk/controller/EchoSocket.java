package com.wk.controller;

import com.wk.util.ClientSocket;
import com.wk.util.SocketToSocket;
import com.wk.util.UDPSocketServer;

public class EchoSocket {
	private static final int PORT = 8625;

	public void open() {
		ClientSocket cs = new ClientSocket();
		try {
			UDPSocketServer udp = new UDPSocketServer();//udp广播获取目标服务器ip
			String id = udp.getHost();
			cs.openSocket(id, PORT); //建立链接
			new SocketToSocket(id);//建立自己的服务器进行转发
		} catch (Exception e) {
		}
	}

	public static void responseMessage(String temp) {

	}

}
