package com.tedu.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端主类
 * @author Administrator
 *
 */
public class WebServer {
	private ServerSocket server;
	private ExecutorService threadPool;
	/**
	 * 初始化WebServer
	 */
	public WebServer(){
		try {
			System.out.println("正在启动服务端...");
			server = new ServerSocket(8888);
			threadPool = Executors.newFixedThreadPool(50);
			System.out.println("服务端启动完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 服务端启动方法
	 */
	public void start(){
		try {
			while(true){
				System.out.println("等待客户端连接...");
				Socket socket = server.accept();
				System.out.println("一个客户端连接...");
				//启动线程处理客户端请求
				ClientHandler handler = new ClientHandler(socket);
				//将任务交给线程池
				threadPool.execute(handler);
//				Thread t = new Thread(handler);
//				t.start();
			}
		} catch (Exception e) {
			
		}
	}
	public static void main(String[] args){
		WebServer server = new WebServer();
		server.start();
	}
}
