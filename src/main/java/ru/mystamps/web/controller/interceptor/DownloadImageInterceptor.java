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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.RequiredArgsConstructor;

// TODO: javadoc
// TODO: add togglz feature
public class DownloadImageInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(DownloadImageInterceptor.class);
	
	@Override
	@SuppressWarnings("PMD.SignatureDeclareThrowsException")
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler) throws Exception {
		
		if (!"POST".equals(request.getMethod())) {
			return true;
		}
		
		// Inspecting AddSeriesForm.imageUrl field.
		// If it doesn't have a value, then nothing to do here.
		String imageUrl = request.getParameter("imageUrl");
		if (StringUtils.isEmpty(imageUrl)) {
			return true;
		}
		
		if (!(request instanceof StandardMultipartHttpServletRequest)) {
			LOG.warn(
				"Unknown type of request ({}). "
				+ "Downloading images from external servers won't work!",
				request
			);
			return true;
		}
		
		LOG.debug("preHandle imageUrl = {}", request.getParameter("imageUrl"));
		
		StandardMultipartHttpServletRequest multipartRequest =
			(StandardMultipartHttpServletRequest)request;
		MultipartFile image = multipartRequest.getFile("image");
		if (image != null && StringUtils.isNotEmpty(image.getOriginalFilename())) {
			LOG.debug("User provided image, exited");
			// user specified both image and image URL, we'll handle it later, during validation
			return true;
		}
		
		// user specified image URL: we should download file and represent it as "image" field.
		// Doing this our validation will be able to check downloaded file later.
		
		LOG.debug("User provided link, downloading");
		// TODO: change user agent
		byte[] data;
		try (InputStream stream = new BufferedInputStream(new URL(imageUrl).openStream())) {
			data = StreamUtils.copyToByteArray(stream);
		} catch (IOException ex) {
			// TODO(security): fix possible log injection
			// TODO: where/how to show possible errors during downloading?
			LOG.error("Couldn't download file from URL '" + imageUrl + "'", ex);
			return true;
		}
		LOG.debug("Downloaded!");
		
		multipartRequest.getMultiFileMap().set("image", new MyMultipartFile(data, imageUrl));
		LOG.debug("Request updated");
		
		// TODO: how we can validate url?
		
		return true;
	}
	
	@RequiredArgsConstructor
	private class MyMultipartFile implements MultipartFile {
		private final byte[] content;
		private final String link;
		
		@Override
		public String getName() {
			throw new IllegalStateException("Not implemented");
		}

		@Override
		public String getOriginalFilename() {
			return link;
		}

		// TODO: preserve original content type
		@Override
		public String getContentType() {
			return "image/jpeg";
		}

		@Override
		public boolean isEmpty() {
			return getSize() == 0;
		}

		@Override
		public long getSize() {
			return content.length;
		}

		@Override
		public byte[] getBytes() throws IOException {
			return content;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(content);
		}

		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			throw new IllegalStateException("Not implemented");
		}
	}
	
}
