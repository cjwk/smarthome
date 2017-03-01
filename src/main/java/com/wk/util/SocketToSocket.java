package com.wk.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class SocketToSocket {
	private static final Integer CLIENT_PORT = 8625;
	private static final Integer SERVERS_PORT = 8622;
	private String host;
	private BufferedReader clientIn;
	private PrintWriter clientOut;
	private ServerSocket serverSocket;
	private ArrayList<Socket> socketList = new ArrayList<Socket>();
	private Socket socket;

	/**
	 * 
	 * @param host 目标客户端ip
	 * @throws IOException
	 */
	public SocketToSocket(String host) throws IOException {
		this.host = host;
		this.serverSocket = new ServerSocket(SERVERS_PORT);
		openSocket();
		server();
	}

	/**
	 * 与home建立链接
	 */
	public void openSocket() {
		try {
			socket = new Socket(host, CLIENT_PORT);
			System.out.println("服务器链接成功！！");
			clientIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF-8"));
			clientOut = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
			new Thread(new ClientReaderMsg()).start();

		} catch (IOException e) {
			openSocket();
			e.printStackTrace();
			System.out.println("找不到" + host + "！！");
		}

	}

	/**
	 * 把从服务器读到的msg发送给home
	 * 
	 * @param msg
	 */
	public void clientWriterMsg(String msg) {

		clientOut.write(msg);
		clientOut.write('\n');
		clientOut.flush();
	}

	/**
	 * 从目标Socket服务器读取信息
	 * 
	 * @param serversOut
	 */
	public class ClientReaderMsg implements Runnable {
		@Override
		public void run() {
			String msg = null;
			try {
				while ((msg = clientIn.readLine()) != null) {
					System.out.println(msg + "返回");
					Iterator<Socket> iter = socketList.iterator();
					while (iter.hasNext()) {
						Socket s = iter.next();
						OutputStream os = s.getOutputStream();
						PrintWriter pw = new PrintWriter(os);
						pw.write(msg);
						pw.write('\n');
						pw.flush();
					}

				}
			} catch (IOException e) {
				openSocket();
				System.out.println("服务器未知错误，正在尝试重新链接...");
			}
		}
	}

	// ----------------------------------------------------------

	/**
	 * 创建自己的Socket服务端 循环等待客户端链接
	 */
	public void server() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket s = serverSocket.accept();
						socketList.add(s);
						new Thread(new ServersThread(s)).start();
					} catch (IOException e) {
					}
				}			
			}
		}).start(); 
		
	}

	public class ServersThread implements Runnable {
		private Socket s;

		public ServersThread(Socket s) {
			this.s = s;
		}

		@Override
		public void run() {
			InputStream is = null; // 将字节流转换为字符流
			InputStreamReader isr = null;
			BufferedReader br = null; // 为输入流添加缓冲
			try {
				// 获取输入流，读取客户端信息
				is = s.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				new Thread(new ReadThread(br)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public class ExceptionThread implements Runnable{
		private PrintWriter pw;
		public ExceptionThread(PrintWriter pw) {
			this.pw = pw;
		}
		@Override
		public void run() {
			String msg ;
		}
		
	}
	
	public class ReadThread implements Runnable {
		BufferedReader br = null;

		public ReadThread(BufferedReader br) {
			this.br = br;
		}

		@Override
		public void run() {
			try {
				String msg;
				while ((msg = br.readLine()) != null) {
					clientWriterMsg(msg);
					System.out.println(msg);
				}
				System.out.println("-----" + msg);
			} catch (IOException e) {
				
			}
		}
	}
}
