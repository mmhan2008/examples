package com.example.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EncodeFilter implements Filter {

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletrequest;
		String method = req.getMethod();
		if (method.equalsIgnoreCase("post")) {
			servletrequest.setCharacterEncoding("UTF-8");
		}else{
			Map<String, String[]> map = req.getParameterMap();
			Set<Entry<String, String[]>> set = map.entrySet();
			for (Entry<String, String[]> en : set) {
				String[] vs = en.getValue();
				for (int i = 0; i < vs.length; i++) {
					vs[i] = new String(vs[i].getBytes("ISO8859-1"),"UTF-8");
				}
			}
		}
		servletresponse.setContentType("text/html;charset=UTF-8");
		filterchain.doFilter(servletrequest, servletresponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
