package com.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


// HttpSessionListener: ������ �����ǰų� �Ҹ�ɶ� �߻��ϴ�  �̺�Ʈ(HttpSessionEvent)�� ó���ϴ� ������
public class CountManager implements HttpSessionListener{
	private static int currentCount;
	private static long toDayCount, yesterDayCount, totalCount;
	
	public static int getCurrentCount() {
		return currentCount;
	}
	
	public static long getToDayCount() {
		return toDayCount;
	}
	
	public static long getyesterDayCount() {
		return yesterDayCount;
	}
	
	public static long gettotalCount() {
		return totalCount;
	}
	public static void init(long toDay, long yesterDay, long total) {
		toDayCount = toDay;
		yesterDayCount = yesterDay;
		totalCount = total;
	}
	
	public CountManager() {
		TimerTask cron = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				yesterDayCount=toDayCount;
				toDayCount=0;
			}
		};
		Timer timer = new Timer();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);  // ���ù� 12��
		timer.schedule(cron, cal.getTime(),1000*60*60*24); // �Ϸ翡 �ѹ��� �� 12�ð� �Ǹ� �����϶� 
	}
	
	
	@Override
	public void sessionCreated(HttpSessionEvent evt) { // ������ ������ �� ����
		// TODO Auto-generated method stub
		HttpSession session = evt.getSession();
		
		synchronized (this) {
			currentCount++;
			toDayCount++;
			totalCount++;
		}
		session.getServletContext().log(session.getId()+": ���� ����...");	
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) { // ������ ����� �� ����
		// TODO Auto-generated method stub
		synchronized (this) {
			currentCount--;
			
			if(currentCount<0) currentCount=0;
		}
		
	}
	
	
	
}
