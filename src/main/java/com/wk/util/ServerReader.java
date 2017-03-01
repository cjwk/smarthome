package com.wk.util;

import java.io.BufferedReader;

class ServerReader implements Runnable{
	private BufferedReader br;
	//Socket socket;
	public ServerReader(BufferedReader br ) {
		 this.br = br ;
		// this.socket = socket ;
	}
	@Override
	public void run() {
	try {
			
			while((br.readLine())!= null){
				 System.out.println(br.readLine()+"222222接受");
			}
			//socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
}

