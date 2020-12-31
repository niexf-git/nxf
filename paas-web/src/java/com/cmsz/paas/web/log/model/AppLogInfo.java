package com.cmsz.paas.web.log.model;

/**
 * 应用日志文件信息
 * @author li.lv
 * 2015-4-20	
 */
public class AppLogInfo {
	/** 文件名 */
	private String fileName;
	/** 文件大小 */
	private String fileSize;
	/** 文件更新时间 */
	private String fileTime;
	/** 文件路径  */
	private String filePath;
	/** 下载路径  */
	private String downloadPath;
	
	/** 文件类型  */
	private String fileType;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileTime() {
		return fileTime;
	}

	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "AppLogInfo [fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileTime=" + fileTime + ", filePath=" + filePath
				+ ", downloadPath=" + downloadPath + ", fileType=" + fileType
				+ "]";
	}
}
