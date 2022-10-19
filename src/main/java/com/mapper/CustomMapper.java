package com.mapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.mapper.*;

import com.opensymphony.xwork2.config.ConfigurationManager;

class CustomMapper implements ActionMapper {

	@Override
	public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager config) {
		ActionMapping mapping = new DefaultActionMapper().getMapping(request,config);
		mapping.setMethod(request.getMethod().toLowerCase()+"_"+mapping.getName());
//		System.out.println(mapping.getMethod());
		return mapping;
	}

	@Override
	public ActionMapping getMappingFromActionName(String arg0) {
		return null;
	}

	@Override
	public String getUriFromActionMapping(ActionMapping arg0) {
		return null;
	}
	
}