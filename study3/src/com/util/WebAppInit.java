package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
// ServletContextListener
// �����ؽ�Ʈ�� �ʱ�ȭ�ǰų� �����Ҷ� �߻��ϴ� �̺�Ʈ�� ó���ϴ� ������
	@WebListener
public class WebAppInit implements ServletContextListener{
	private String pathname = "/WEB-INF/count.txt";
	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		// ������ ����Ǵ� ����
		saveFile();
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		
		pathname = evt.getServletContext().getRealPath(pathname);
		
		// ������ ���۵Ǵ� ����
		loadFile();
	}
	
	private void loadFile() {
		try {
			long toDay, yesterDay, total;
			
			File f = new File(pathname);
			if(! f.exists())
				return;
			
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String s;
			s=br.readLine();
			br.close();
			
			if(s!=null) {
				String[]ss = s.split(":");
				
				if(ss.length==3) {
					toDay=Integer.parseInt(ss[0].trim());
					yesterDay=Integer.parseInt(ss[1].trim());
					total=Integer.parseInt(ss[2].trim());
					
					CountManager.init(toDay, yesterDay, total);
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void saveFile() {
		try {
			long toDay, yesterDay, total;
			
			toDay = CountManager.getToDayCount();
			yesterDay = CountManager.getyesterDayCount();
			total = CountManager.gettotalCount();
			
			String s = toDay+":"+yesterDay+":"+total;
			
			PrintWriter out = new PrintWriter(pathname);
			out.print(s);
			out.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
