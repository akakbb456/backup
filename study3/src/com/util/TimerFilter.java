package com.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class TimerFilter implements Filter{
	private FilterConfig config;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.config = config;
	}
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// request 필터
		long before = System.currentTimeMillis();
		
		// 다음 필터. 또는 필터의 마지막이면 servlet또는 jsp실행
		chain.doFilter(req, resp);
		
		// response 필터
		long after = System.currentTimeMillis();
		
		String uri="";
		if(req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest)req;
			uri = request.getRequestURI();
			config.getServletContext().log(uri+":"+(after-before)+"ms..."); // 각각의 명령어를 실행하는데 걸리는 시간을 구한다
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
