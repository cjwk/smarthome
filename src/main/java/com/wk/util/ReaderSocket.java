package com.wk.util;

import java.io.PrintWriter;
import java.util.Scanner;

public class ReaderSocket implements Runnable {
	private PrintWriter out;
	private Scanner scan ;
	
	//PrintWriter out ;
	public ReaderSocket(PrintWriter out) {
		this.out = out ;
	}
	@Override
	public void run() {
		try {

			
			while(true){
				String str = scan.nextLine();
				out.write(str);
				out.write('\n');
				out.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
