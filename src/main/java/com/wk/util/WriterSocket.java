package com.wk.util;

import java.io.BufferedReader;

import com.wk.controller.EchoSocket;

public class WriterSocket implements Runnable{
	BufferedReader in ;
	public WriterSocket(BufferedReader in ) {
		this.in = in ;
	}
	
	@Override
	public void run() {
		try {
			
			String temp ;
			while((temp = in.readLine())!= null){
				EchoSocket.responseMessage( temp);
				System.out.println("=====");
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
