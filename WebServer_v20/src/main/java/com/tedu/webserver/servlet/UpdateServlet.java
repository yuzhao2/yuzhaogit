package com.tedu.webserver.servlet;

import java.io.RandomAccessFile;
import java.util.Arrays;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 处理用户修改密码业务
 * @author Administrator
 *
 */
public class UpdateServlet extends HttpServlet {
	public void service(HttpRequest request,
			HttpResponse response){
		System.out.println("UpdateServlet：开始处理修改密码业务");
		
		//获取用户修改信息
		String oldname = request.getParameter("oldname");
		String oldword = request.getParameter("oldword");
		String newword = request.getParameter("newword");
		
		//读取user.dat对比信息
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw")
		) {
			//默认为登录开关不成功
			boolean check = false;
			for (int i = 0; i < raf.length()/100; i++) {
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				//判断用户输入信息
				if(name.equals(oldname)){
					check = true;
					//对比密码
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					if(pwd.equals(oldword)){
						raf.seek(i*100+32);
						data = newword.getBytes();
						data = Arrays.copyOf(data, 32);
						raf.write(data);
						System.out.println("修改成功");
						forward("/myweb/update_success.html",request,response);
						break;
					}
				}
				if(check){
					//修改失败
					forward("/myweb/update_fail.html",request,response);
					
				}else {
					//无此用户名
					forward("/myweb/no_user.html",request,response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
