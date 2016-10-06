/*
 * Copyright (C) 2009-2016 Slava Semushin <slava.semushin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package ru.mystamps.web.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// TODO: read http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html
// TODO: read http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/context/request/WebRequestInterceptor.html
// TODO: extract
public class DownloadImageFromExternalResourceInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	@SuppressWarnings("PMD.SignatureDeclareThrowsException")
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler) throws Exception {
		
		if ("POST".equals(request.getMethod())) {
			System.out.println("DEBUG: preHandle date: " + new java.util.Date());
			System.out.println("DEBUG: preHandle params: " + request.getParameterMap());
			System.out.println("DEBUG: preHandle image = " + request.getParameter("image"));
			System.out.println("DEBUG: preHandle imageUrl = " + request.getParameter("imageUrl"));
			
			// TODO: find attachment
			// TODO: if attachment is empty and url is not empty => download image and fill attach
			// TODO: if attachment is not empty and url is not empty => do nothing (validator will fail later)
			// TODO: how we can validate url?
			// TODO: where/how to show possible errors during downloading?
			
		}
		return true;
	}
	
}
