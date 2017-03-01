package com.wk.util;

import java.io.PrintWriter;
import java.util.Scanner;
 class ServerWriter implements Runnable{
	private PrintWriter pw;
	public ServerWriter(PrintWriter pw ) {
		 this.pw = pw ;
	}
	@Override
	public void run() {
	try {
			Scanner s = new  Scanner(System.in);
			
			while(true){
				String temp  = s.nextLine();
				 pw.println(temp+"111111111111发送");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
}
