package org.openmrs.module.fdahplat;


// package org.openmrs.web;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;
public class CopyLegacyUiContentToWebInf implements ServletContextAware {
	
	private static Log log = LogFactory.getLog(CopyLegacyUiContentToWebInf.class);
	
	private static final String MODULE_ROOT_DIR = "/WEB-INF/view/module/fdahplat";
	private static final String LEGACYUI_ROOT_DIR = "/WEB-INF/view/module/legacyui";
	private static final String UIFRAMEWORK_ROOT_DIR = "/WEB-INF/view/module/uiframework";
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		
		String basePath = servletContext.getRealPath("");
		
		try{
			// copy images into legacyui/.../resources/images folder
			// File destDir = new File(basePath + UILIBRARY_ROOT_DIR + "/resources/images".replace("/", File.separator));
			File srcDir = new File(basePath + MODULE_ROOT_DIR + "/resources/images".replace("/", File.separator));
			// FileUtils.copyDirectory(srcDir, destDir);	
			String destDirPath = basePath + UIFRAMEWORK_ROOT_DIR + "/resources/images".replace("/", File.separator);
			
			File testFile = new File(destDirPath);
			if(testFile.exists()){
				iterateFiles(srcDir.listFiles(), destDirPath);
			}			
		}
		catch(IOException ex){
			log.error("Failed to copy fdahplat image files to uiframework/.../resources/images", ex);
		}
		
		try{
			// copy images into legacyui/.../resources/images folder
			// File destDir = new File(basePath + LEGACYUI_ROOT_DIR + "/resources/images".replace("/", File.separator));
			File srcDir = new File(basePath + MODULE_ROOT_DIR + "/resources/images".replace("/", File.separator));
			// FileUtils.copyDirectory(srcDir, destDir);	
			String destDirPath = basePath + LEGACYUI_ROOT_DIR + "/resources/images".replace("/", File.separator);

			File testFile = new File(destDirPath);
			if(testFile.exists()){
				iterateFiles(srcDir.listFiles(), destDirPath);
			}
		}
		catch(IOException ex){
			log.error("Failed to copy fdahplat image files to legacyui/.../resources/images", ex);
		}
		
		try {
								
			// copy images directly into basePath
			// File destDir = new File(basePath + "/images".replace("/", File.separator));
			File srcDir = new File(basePath + MODULE_ROOT_DIR + "/resources/images".replace("/", File.separator));
			//FileUtils.copyDirectory(srcDir, destDir);	
			String destDirPath = basePath + "/images".replace("/", File.separator);
			iterateFiles(srcDir.listFiles(), destDirPath);									
		}
		catch (IOException ex) {
			log.error("Failed to copy fdahplat image files to basePath", ex);
		}
	}
	
	public void iterateFiles(File[] files, String destDirPath) throws IOException{
		 /**
		  * Method to iterate over the files in the source directory and copy them into the
		  * destination directory
		  */
		
		for (File file : files) {
	        if (file.isDirectory()) {
	            log.info("Directory: " + file.getName());	            
	            iterateFiles(file.listFiles(), destDirPath); // Calls same method again.
	        } else {
	        	//replace a file of similar name in destination directory.
	        	File fileToReplace = new File(destDirPath + "/".replace("/", File.separator) + file.getName());
	        	if(fileToReplace.exists()){ 
	        		// confirm that the corresponding file exists in the destDir.
	        		// If file doesn't exist in destDir, then it has been taken
	        		// care of by settings override app and demoHeader.gsp
	        		FileUtils.copyFile(file, fileToReplace); // fileToReplace is destination file.
	        	}	        	
	            log.info("File: " + file.getName());
	        }
	    }
	}
}