package com.cmsz.paas.web.base.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.log.model.AppLogInfo;

/**
 * FTP工具类-上传、下载、获取文件列表
 * 
 * @author li.lv
 */
public class FtpClientUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(FtpClientUtil.class);

	private FTPClient ftpClient;
//	public static final String IP = PropertiesUtil.getValue("ftpServerIp");
//	public static final int PORT = Integer.parseInt(PropertiesUtil
//			.getValue("ftpServerPort"));
//	public static final String USER = PropertiesUtil.getValue("ftpServerUser");
//	public static final String PWD = PropertiesUtil.getValue("ftpServerPWD");
//	public static final String BASEPATH = PropertiesUtil
//			.getValue("ftpServerFileUrl");

	// CC版本库ftp配置
//	public static final String SVN_IP = PropertiesUtil
//			.getValue("svnftpServerIp");
//	public static final int SVN_PORT = Integer.parseInt(PropertiesUtil
//			.getValue("svnftpServerPort"));
//	public static final String SVN_USER = PropertiesUtil
//			.getValue("svnftpServerUser");
//	public static final String SVN_PWD = PropertiesUtil
//			.getValue("svnftpServerPWD");

	/**
	 * 连接FTP服务器
	 * 
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @param path
	 * @throws SocketException
	 * @throws IOException
	 * @throws PaasWebException
	 */
	public void connectServer(String server, int port, String user,
			String password, String path) throws SocketException, IOException,
			PaasWebException {
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(server, port);
		} catch (SocketException se) {
			logger.error("Connected to " + server + "fail.", se);
			throw new PaasWebException(Constants.FTP_SOCKET_EXCEPTION,
					se.getMessage());
		}
		try {
			logger.info("Connected to " + server + "success.");
			ftpClient.login(user, password);
			logger.info("Login name:" + user);
			// ftpClient.setDefaultTimeout(30 * 1000);
			// ftpClient.setConnectTimeout(30 * 1000);
			ftpClient.setDataTimeout(30 * 1000);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(
					FTPClientConfig.SYST_UNIX);
			conf.setServerLanguageCode("zh");
			// Path is the sub-path of the FTP path
			if (path.length() != 0) {
				ftpClient.changeWorkingDirectory(path);
			}
		} catch (IOException ex) {
			logger.error("Login" + server + "fail.", ex);
			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
					ex.getMessage());
		}
	}

	/**
	 * 关闭FTP服务连接
	 * 
	 * @throws PaasWebException
	 */
	public void closeServer() throws PaasWebException {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			throw new PaasWebException(Constants.FTP_SOCKET_EXCEPTION,
					ex.getMessage());
		}
	}

	/**
	 * 获取文件列表
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<AppLogInfo> getFileList(String path) throws Exception,
			PaasWebException {
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles(path);
			List<AppLogInfo> retList = new ArrayList<AppLogInfo>();
			if (ftpFiles == null || ftpFiles.length == 0) {
				return retList;
			}
			for (FTPFile ftpFile : ftpFiles) {
					AppLogInfo info = new AppLogInfo();
					info.setFileName(ftpFile.getName());
					
					info.setFilePath(path);
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					//sdf.setTimeZone(TimeZone.getTimeZone("GMT+16"));
					info.setFileTime(sdf.format(new java.util.Date(ftpFile
							.getTimestamp().getTimeInMillis())));
					info.setDownloadPath(path + "@" + ftpFile.getName());
					if (ftpFile.isDirectory()) {
						info.setFileSize("");
						info.setFileType("1");// 文件夹
					}
					if (ftpFile.isFile()) {
						info.setFileSize(formetFileSize(ftpFile.getSize()));
						info.setFileType("0");// 文件
					}
					retList.add(info);
			}
			ListSort<AppLogInfo> listSort= new ListSort<AppLogInfo>(); 
			listSort.Sort(retList, "getFileTime", "desc");  
			return retList;
		} catch (Exception ex) {
			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
					ex.getMessage());
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	public boolean uploadFile(String fileName, String newName)
			throws IOException {
		boolean flag = false;
		InputStream iStream = null;
		newName = new String(newName.getBytes("GBK"), "iso-8859-1");
		try {
			iStream = new FileInputStream(fileName);
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
					e.getMessage());
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	public boolean uploadFile(String fileName) throws IOException {
		return uploadFile(fileName, fileName);
	}

	/**
	 * 以流方式上传文件
	 * 
	 * @param iStream
	 * @param newName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(InputStream iStream, String newName)
			throws IOException {
		boolean flag = false;
		try {
			// can execute [OutputStream storeFileStream(String remote)]
			// Above method return's value is the local file stream.
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 下载文件到指定路径
	 * 
	 * @param remoteFileName
	 *            远程文件名
	 * @param localFileName
	 *            本地文件名
	 * @return
	 * @throws IOException
	 * @throws PaasWebException
	 */
	public boolean download(String remoteFileName, String localFileName)
			throws IOException, PaasWebException {
		try {
			OutputStream is = new FileOutputStream(localFileName);
			ftpClient.retrieveFile(remoteFileName, is);
			is.flush();
			is.close();
			return true;
		} catch (Exception ex) {
			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
					ex.getMessage());
		}
	}

//	public boolean downloadCCFile(String remoteFileName, String localFileName)
//			throws IOException, PaasWebException {
//		try {
//			connectServer(SVN_IP, SVN_PORT, SVN_USER, SVN_PWD, "");
//			String mulu = remoteFileName.substring(0,
//					remoteFileName.lastIndexOf("/"));
//			String fileName = remoteFileName.substring(remoteFileName
//					.lastIndexOf("/") + 1);
//			boolean hasFile = false;
//			if (ftpClient.changeWorkingDirectory(mulu)) {
//				String[] listNames = ftpClient.listNames();
//				if (listNames != null && listNames.length > 0) {
//					for (String name : listNames) {
//						if (fileName.endsWith(name)) {
//							hasFile = true;
//							break;
//						}
//					}
//				}
//			}
//			if (!hasFile) {
//				throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
//						"服务器不存在该文件:" + remoteFileName);
//			}
//			//
//			// FTPFile[] listFiles = ftpClient.listFiles(remoteFileName);
//			// if (listFiles == null || listFiles.length == 0) {
//			// throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
//			// "服务器不存在该文件:" + remoteFileName);
//			// }
//			OutputStream is = new FileOutputStream(localFileName);
//			ftpClient.retrieveFile(remoteFileName, is);
//			is.flush();
//			is.close();
//			return true;
//		} catch (Exception ex) {
//			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
//					ex.getMessage());
//		}
//	}

	/**
	 * 下载文件以流方式输出
	 * 
	 * @param sourceFileName
	 *            远程文件名称
	 * @return InputStream
	 * @throws IOException
	 */
	public InputStream downFile(String sourceFileName) throws IOException {
		try {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			return ftpClient.retrieveFileStream(sourceFileName);
		} catch (IOException e) {
			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
					e.getMessage());
		}
	}

	/*
	 * ftp上传文件， 文件路径为绝对路径 add by fubl
	 */
//	public boolean ftpUploadFile(String fileSrcUrl, String fileName)
//			throws PaasWebException {
//
//		String ftpServerFileUrl = PropertiesUtil.getValue("ftpServerFileUrl");
//		boolean result = false;
//		try {
//			connectServer(IP, PORT, USER, PWD, ftpServerFileUrl);
//
//			String desUrl = ftpServerFileUrl + fileName;
//
//			if (existDirectory(ftpServerFileUrl, fileName)) {
//				createDirectory("dddd/log");
//			}
//
//			result = uploadFile(fileSrcUrl, desUrl);
//
//		} catch (IOException e) {
//			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
//					e.getMessage());
//		} finally {
//			closeServer();
//		}
//
//		return result;
//	}

	/**
	 * 根据本地文件，集群名称，应用名称，版本名称，版本文件 以ftp通道上传文件
	 * 
	 * @param fileSrcUrl
	 * @param clusterLabel
	 * @param appName
	 * @param appVersionName
	 * @param fileName
	 * @return
	 * @throws PaasWebException
	 */
//	public String ftpUploadFile(String fileSrcUrl, String clusterLabel, String appGroup,
//			String appName, String appVersionName, String fileName)
//			throws PaasWebException {
//
//		String ftpServerFileUrl = PropertiesUtil.getValue("ftpServerFileUrl");
//		String result = "";
//		String desUrl = ftpServerFileUrl + clusterLabel;
//		try {
//			connectServer(IP, PORT, USER, PWD, ftpServerFileUrl);// 连接到ftp服务器
//
//			if (existDirectory(ftpServerFileUrl, clusterLabel)) {
//				createDirectory(clusterLabel);
//			}
//			this.changeDirectory(desUrl);
//			
//			if(existDirectory(desUrl, appGroup)){
//				createDirectory(appGroup);
//			}
//			desUrl = desUrl + "/" + appGroup;
//			this.changeDirectory(desUrl);
//			
//			if (existDirectory(desUrl, appName)) {
//				createDirectory(appName);
//			}
//			desUrl = desUrl + "/" + appName;
//			this.changeDirectory(desUrl);
//
//			if (existDirectory(desUrl, appVersionName)) {
//				createDirectory(appVersionName);
//			}
//			desUrl = desUrl + "/" + appVersionName;
//			this.changeDirectory(desUrl);
//		} catch (PaasWebException ex) {
//			throw new PaasWebException(ex.getKey(), ex.getMessage());
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			boolean uploadOk = uploadFile(fileSrcUrl, desUrl + "/" + fileName);
//			if (uploadOk) {
//				result = desUrl + "/" + fileName;
//			} else {
//				throw new PaasWebException(
//						Constants.USER_UPLOAD_FILE_IO_EXCEPTION,
//						"用户上传文件到PAAS系统失败，ftp api返回false");
//			}
//		} catch (IOException e) {
//			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
//					e.getMessage());
//		} finally {
//			closeServer();
//		}
//		return result;
//	}

	/*
	 * ftp下载文件， 文件路径为绝对路径 add by fubl
	 */
//	public boolean ftpDownloadFile(String fileSrcUrl, String fileName)
//			throws PaasWebException {
//
//		// String ftpServerFileUrl =
//		// PropertiesUtil.getValue("ftpServerFileUrl");
//		String downLoadFileTempUrl = PropertiesUtil
//				.getValue("downLoadFileTempUrl");
//
//		boolean result = false;
//
//		try {
//			connectServer(IP, PORT, USER, PWD, fileSrcUrl);
//			result = download(fileSrcUrl, downLoadFileTempUrl + fileName);
//
//		} catch (IOException e) {
//			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
//					e.getMessage());
//		} finally {
//			closeServer();
//		}
//
//		return result;
//	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public String formetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public boolean changeDirectory(String path) throws IOException {
		boolean flag = false;
		try {
			flag = ftpClient.changeWorkingDirectory(path);
		} catch (Exception e) {
			throw new PaasWebException(Constants.FTP_CHGDIR_EXCEPTION,
					e.getMessage());
		}
		return flag;
	}

	public boolean createDirectory(String pathName) throws IOException {
		boolean flag = false;
		try {
			flag = ftpClient.makeDirectory(pathName);
		} catch (Exception e) {
			throw new PaasWebException(Constants.FTP_CREDIR_EXCEPTION,
					e.getMessage());
		}
		return flag;
	}

	public boolean removeDirectory(String path) throws IOException {
		boolean flag = false;
		try {
			flag = ftpClient.removeDirectory(path);
		} catch (Exception e) {
			throw new PaasWebException(Constants.FTP_REMDIR_EXCEPTION,
					e.getMessage());
		}
		return flag;
	}

	// delete all subDirectory and files.
	public boolean removeDirectory(String path, boolean isAll)
			throws IOException {
		try {
			if (!isAll) {
				return removeDirectory(path);
			}
			FTPFile[] ftpFileArr = ftpClient.listFiles(path);
			if (ftpFileArr == null || ftpFileArr.length == 0) {
				return removeDirectory(path);
			}
			//
			for (FTPFile ftpFile : ftpFileArr) {
				String name = ftpFile.getName();
				if (ftpFile.isDirectory()) {
					System.out.println("* [sD]Delete subPath [" + path + "/"
							+ name + "]");
					removeDirectory(path + "/" + name, true);
				} else if (ftpFile.isFile()) {
					System.out.println("* [sF]Delete file [" + path + "/"
							+ name + "]");
					deleteFile(path + "/" + name);
				} else if (ftpFile.isSymbolicLink()) {

				} else if (ftpFile.isUnknown()) {

				}
			}
			return ftpClient.removeDirectory(path);
		} catch (Exception e) {
			throw new PaasWebException(Constants.FTP_REMDIR_EXCEPTION,
					e.getMessage());
		}
	}

	// Check the path is exist; exist return true, else false.
	public boolean existDirectory(String path, String fileName)
			throws IOException {
		boolean flag = true;
		try {
			FTPFile[] ftpFileArr = ftpClient.listDirectories(path);
			// FTPFile[] ftpFileArr = ftpClient.listFiles(path);
			for (FTPFile ftpFile : ftpFileArr) {
				if (ftpFile.isDirectory()
						&& ftpFile.getName().equals(fileName)) {
					flag = false;
					break;
				}
			}
		} catch (Exception e) {
			throw new PaasWebException(Constants.FTP_IO_EXCEPTION,
					e.getMessage());
		}
		return flag;
	}
}