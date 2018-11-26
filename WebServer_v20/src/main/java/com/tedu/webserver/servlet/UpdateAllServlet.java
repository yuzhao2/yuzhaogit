package com.tedu.webserver.servlet;

import java.io.RandomAccessFile;
import java.util.Arrays;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

public class UpdateAllServlet extends HttpServlet{

	public void service(HttpRequest request,
			HttpResponse response) {
		System.out.println("UpdateAllServlet：开始处理用户列表业务");
	
		//获取用户修改信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.valueOf(request.getParameter("age"));
//		String age = request.getParameter("age");
		
		//读取user.dat对比信息
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");
		) {
			for (int i = 0; i < raf.length()/100; i++) {
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				if(name.equals(username)){
				//判断用户名输入信息
					if(!password.isEmpty()&&!nickname.isEmpty()){
						
						raf.seek(i*100+32);
						data = password.getBytes();
						data = Arrays.copyOf(data, 32);
						raf.write(data);
					
						data = nickname.getBytes();
						data = Arrays.copyOf(data, 32);
						raf.write(data);
						
//						data = age.getBytes();
//						data = Arrays.copyOf(data, 4);
						raf.writeInt(age);
						//信息修改成功
						forward("/myweb/updateAll_success.html",request,response);
						System.out.println("信息修改成功");
						break;
					}		
				}else{
					//信息修改失败
					forward("/myweb/updateAll_fail.html",request,response);
					System.out.println("信息有误！请重新修改...");
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	
	
	
	
	}

}
