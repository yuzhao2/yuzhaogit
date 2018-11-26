package com.tedu.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 处理用户注册业务
 * @author Administrator
 *
 */
public class RegServlet extends HttpServlet{
	public void service(HttpRequest request,
			HttpResponse response){
		System.out.println("RegServlet:开始处理用户注册");
		/*
		 * 1：获取表单数据(用户页面输入的注册信息)
		 * 2：将数据写入user.dat文件
		 * 3：设置response响应注册成功页面
		 */
		//1
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(request.getParameter("age"));
		
		
		System.out.println("username:"+username);
		System.out.println("passwork:"+password);
		System.out.println("nickname:"+nickname);
		System.out.println("age:"+age);
		
		try(
			RandomAccessFile raf 
				= new RandomAccessFile("user.dat","rw")
		){
			//先将光标移到文件末尾
			raf.seek(raf.length());
			//写用户名
			byte[] data = username.getBytes("UTF-8");
			//扩容至32字节
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			data = password.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			data = nickname.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			raf.writeInt(age);
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//3设置response，响应注册成功页面
		forward("/myweb/reg_success.html", request, response);
		
		
		
		
		
		
		
		
		System.out.println("RegServlet:处理用户注册完毕");
	}

}

















