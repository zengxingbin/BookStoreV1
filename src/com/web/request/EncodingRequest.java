package com.web.request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingRequest extends HttpServletRequestWrapper {

	private Map<String, String[]> parameMap;

	@SuppressWarnings("unchecked")
	public EncodingRequest(HttpServletRequest request) {
		super(request);
		parameMap = request.getParameterMap();
		if(parameMap == null || parameMap.size() == 0) {
			return;
		}
		try {
			String method = request.getMethod();
			if("post".equalsIgnoreCase(method)) {
				request.setCharacterEncoding("UTF-8");

			} else {
				for (Map.Entry<String, String[]> parameEntry : parameMap.entrySet()) {
					String[] values = parameEntry.getValue();
					if(values != null) {
						for (int i = 0; i < values.length; i++) 
							values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
					}				
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public String getParameter(String name) {
		
		String[] values = parameMap.get(name);
		return values != null ? values[0] : null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {			
		return parameMap;
	}

	@Override
	public String[] getParameterValues(String name) {
		return parameMap.get(name);
	}	

}