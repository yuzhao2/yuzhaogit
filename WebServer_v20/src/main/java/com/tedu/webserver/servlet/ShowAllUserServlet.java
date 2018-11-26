package com.tedu.webserver.servlet;

import java.io.RandomAccessFile;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 显示用户列表
 * @author Administrator
 *
 */
public class ShowAllUserServlet extends HttpServlet {
	
	public void service(HttpRequest request, HttpResponse response) {
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","r")
			) {
			/*
			 * 读取user.dat文件，将数据拼接到html中
			 */
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<title>用户列表</title>");
			sb.append("<meta charset='UTF-8'>");
			sb.append("</head>");
			sb.append("<boby>");
			sb.append("<center>");
			sb.append("<h1>用户列表</h1>");
			
			sb.append("<table border='1'>");
			sb.append("<tr><td>用户名</td><td>密码</td><td>昵称</td><td>年龄</td><td>操作</td></tr>");
			
			for (int i = 0; i < raf.length()/100; i++) {
				byte[] data = new byte[32];
				//读用户名
				raf.read(data);
				String username = new String(data,"UTF-8").trim();
				//读密码
				raf.read(data);
				String password = new String(data,"UTF-8").trim();
				//读昵称
				raf.read(data);
				String nickname = new String(data,"UTF-8").trim();
				//读年龄
				int age = raf.readInt();
				
				sb.append("<tr>");
				sb.append("<td>"+username+"</td>");
				sb.append("<td>"+password+"</td>");
				sb.append("<td>"+nickname+"</td>");
				sb.append("<td>"+age+"</td>");
				sb.append("<td><a href='toEditUser?username="+username+"'>修改</a></td>");
				sb.append("</tr>");
			}
			
			sb.append("</table>");
			
			sb.append("</center>");
			sb.append("</boby>");
			sb.append("</html>");
			
			//请求路径
			//http://localhost:8888/myweb/showAllUser
			
			byte[] data = sb.toString().getBytes("UTF-8");
			
			response.putHeader("Content-Type", "text/html");
			response.putHeader("Content-Length", data.length+"");
			
			response.setData(data);

			} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
