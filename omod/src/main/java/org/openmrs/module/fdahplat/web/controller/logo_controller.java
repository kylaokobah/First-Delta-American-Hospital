/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fdahplat.web.controller;

import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller("${rootrootArtifactid}.FdahplatController")
@RequestMapping(value = "module/${rootArtifactid}/${rootArtifactid}.form")
public class FdahplatController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	UserService userService;
	
	/** Success form view name */
	private final String VIEW = "/module/${rootArtifactid}/${rootArtifactid}";
	
	/**
	 * Initially called after the getUsers method to get the landing form name
	 * 
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)

	@ResponseBody
	public void onGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "This method is not allowed.");
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String onPost(HttpServletRequest request, @RequestParam("file") String fileDataUrl,
	        @RequestParam("filename") String fileName, HttpServletResponse response) {
		
		System.out.println("UploadController: onPost  fileName: " + fileName);
		
		JSONObject jsonObject = new JSONObject();
		try {
			File file = getFile(fileDataUrl, fileName);
			jsonObject.put("result", "success");
		}
		catch (IOException e) {
			e.printStackTrace();
			jsonObject.put("result", "failed");
		}
		return jsonObject.toString();
	}
	
	
	private static File getFile(String fileDataUrl, String fileName) throws IOException {
		
		String folderLocation = OpenmrsUtil.getApplicationDataDirectory() + "/logo";
		File folder = new File(folderLocation);
		folder.mkdirs();
		File file = new File(folder, fileName);
		
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		fileDataUrl = fileDataUrl.substring(fileDataUrl.indexOf(",") + 1);
		stream.write(Base64.decodeBase64(fileDataUrl.getBytes()));
		stream.close();
		
		return file;
	}
	
}
