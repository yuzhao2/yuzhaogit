package com.tedu.webserver.servlet;

import java.io.RandomAccessFile;

import com.tedu.webserver.http.HttpRequest;
import com.tedu.webserver.http.HttpResponse;

/**
 * 生成修改指定用户信息页面
 * @author Administrator
 *
 */
public class ToEditUserServt extends HttpServlet {

	public void service(HttpRequest request, HttpResponse response) {
		//获取要修改的用户的名字
		String username = request.getParameter("username");
		
		//找到该用户信息，并生成页面
		try (
				RandomAccessFile raf 
					= new RandomAccessFile("user.dat","rw");	
		) {
			for(int i =0;i<raf.length()/100;i++){
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				if(name.equals(username)){
					raf.read(data);
					String password = new String(data,"UTF-8").trim();
					raf.read(data);
					String nickname = new String(data,"UTF-8").trim();
					int age = raf.readInt();
					
					StringBuilder sb = new StringBuilder();
					sb.append("<html>");
					sb.append("<head>");
					sb.append("<titel>用户列表</title>");
					sb.append("<meta charset='UTF-8'>");
					sb.append("</head>");
					sb.append("<body>");
					sb.append("<center>");
					sb.append("<h1>用户列表</h1>");
					sb.append("<form action='updateAll' method='post'>");
					sb.append("<table border='1'>");
					sb.append("<tr>");
					sb.append("<td>用户名</td>");
					sb.append("<td>"+username+"<input type='hidden' name='username' value='"+username+"'</td>");
					sb.append("</tr>");
					sb.append("<tr>");
					sb.append("<td>密码</td>");
					sb.append("<td><input type='password' name='password'"+"value='"+password+"'></td>");
					sb.append("</tr>");
					sb.append("<tr>");
					sb.append("<td>昵称</td>");
					sb.append("<td><input type='text' name='nickname'"+"value='"+nickname+"'></td>");
					sb.append("</tr>");
					sb.append("<tr>");
					sb.append("<td>年龄</td>");
					sb.append("<td><input type='text' name='age'"+"value='"+age+"'></td>");;
					sb.append("</tr>");
					sb.append("<tr>");
					sb.append("<td align='center' colspan='2'>");
					sb.append("<input type='submit' value='修改'>");
					sb.append("</tr>");
					sb.append("</table>");
					sb.append("</form>");
					sb.append("</center>");
					sb.append("</body>");
					sb.append("</html>");
					
					byte[] arr = sb.toString().getBytes("UTF-8");
					response.putHeader("Content-Type", "text/html");
					response.putHeader("Content-Length", arr.length+"");
					
					response.setData(arr);
					break;
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
