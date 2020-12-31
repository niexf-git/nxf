package com.cmsz.paas.web.group4a.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmsz.paas.web.base.util.JasypUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Component
public class SftpUtils {

	private static final Logger logger = LoggerFactory.getLogger(SftpUtils.class);
	
//	@Value("${sftp.hostname}")
//	private  String sftpHost;
//	@Value("${sftp.port}")
//	private  Integer sftpPort;
//	@Value("${sftp.username}")
//	private  String sftpUsername;
//	@Value("${sftp.password}")
//	private  String sftpPassword;
//	
//	@Value("${4alog.hostname}")
//	private  String sftp4aHost;
//	@Value("${4alog.port}")
//	private  Integer sftp4aPort;
//	@Value("${4alog.username}")
//	private  String sftp4aUsername;
//	@Value("${4alog.password}")
//	private  String sftp4aPassword;	
//	@Value("${4alog.path}")
//	private  String sftpPath;
	
	public static final String NO_FILE = "No such file";  

	public static ChannelSftp sftpClient = null;
	
	
	/**
	 * 初始化sftp服务器
	 * sftpHost,sftpPort,sftpUsername,sftpPassword
	 */
//	public void init4aSftpClient() {// 初始化连接服务器
//		try {
//			JSch jsch=new JSch();
//			Session session=jsch.getSession(PropertiesUtil.getValue("4alog.username"),PropertiesUtil.getValue("4alog.hostname"),Integer.parseInt(PropertiesUtil.getValue("4alog.port")));
//			logger.info("jsch session created");
//			session.setPassword(JasypUtil.decrypt(PropertiesUtil.getValue("4alog.password")));
//			Properties sshConfig = new Properties();
//			sshConfig.put("StrictHostKeyChecking", "no");
//			session.setConfig(sshConfig);
//			session.connect();
//			logger.info("Session connected");
//			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
//			channel.connect();
//			Class cl = channel.getClass();
//			Field f1 =cl.getDeclaredField("server_version");
//			f1.setAccessible(true);
//			f1.set(channel, 2);
//			channel.setFilenameEncoding("GBK");
//			sftpClient = channel;
//			logger.info("Connected to " + PropertiesUtil.getValue("4alog.hostname") + "."+sftpClient.pwd());
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			logger.error("连接文件服务器错误"+e.toString());
//			e.printStackTrace();
//		}
//	}
	/**
	 * 初始化sftp服务器
	 * sftpHost,sftpPort,sftpUsername,sftpPassword
	 * @throws Exception 
	 */
	public void initSftpClient() throws Exception {// 初始化连接服务器

		try {
			JSch jsch=new JSch();
			Session session=jsch.getSession(PropertiesUtil.getValue("sftp.username"),PropertiesUtil.getValue("sftp.hostname"),Integer.parseInt(PropertiesUtil.getValue("sftp.port")));
			
			logger.info("jsch session created");
			session.setPassword(JasypUtil.decrypt(PropertiesUtil.getValue("sftp.password")));
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);
			session.connect(6000);
			logger.info("Session connected.");
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			logger.info("channel connected.");
			
			Class cl = channel.getClass();
			Field f1 =cl.getDeclaredField("server_version");
			f1.setAccessible(true);
			f1.set(channel, 2);
			channel.setFilenameEncoding("GBK");
			sftpClient = channel;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			logger.error("连接文件服务器错误"+e.toString());
			throw new Exception("连接文件服务器错误" + e.toString()); 
		}
	}
	
	
	/**
	 * 上传文件
	 * 
	 * @param pathName
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param originFileName
	 *            待上传文件的名称（绝对地址） *
	 * @return
	 */
//	public  boolean Upload4AFile(String sftpPath,String fileName, String originFileName) {
//		boolean flag = false;
//		try {
//			init4aSftpClient();//初始化SFTP服务器连接
//			mkDir(sftpPath,sftpClient);// 调用此方法去创建文件目录
//			File file = new File(originFileName);
//			sftpClient.cd(sftpPath);//进入父级目录
//			FileInputStream fileInputStream = new FileInputStream(file);
//			sftpClient.put(fileInputStream, fileName);//上传
//			flag = true;
//			fileInputStream.close();
//		} catch (Exception e) {
//			logger.error("SFTP上传异常"+e.toString());
//			e.printStackTrace();
//		} finally {
//			if(sftpClient.isConnected()) {
//				try {
//					sftpClient.getSession().disconnect();
//				} catch (JSchException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				}
//				sftpClient.disconnect();
//			}
//		}
//		return flag;
//	}


	
	
	 /** 
     * 打开指定目录 
     * 
     * @param directory 
     *            directory 
     * @return 是否打开目录 
     */ 
    public static boolean openDir(String directory,ChannelSftp sftp) 
    { 
        try{ 
            sftp.cd(directory);
            return true; 
        } 
        catch (SftpException e){ 
            return false;
        } 
    }
	/** 
     * 创建指定文件夹 
     * 
     * @param dirName 
     *            dirName 
	 * @throws SftpException 
     */ 
    public static void mkDir(String dirName,ChannelSftp sftp) throws Exception 
    { 
        String[] dirs = dirName.split("/"); 
        
        try 
        { 
            String now = sftp.pwd(); 
            sftp.cd("/");
            for (int i = 0; i < dirs.length; i++) 
            { 
            	if (!isEmpty(dirs[i])) {
            		 boolean dirExists = openDir(dirs[i],sftp); 
                     if (!dirExists) 
                     { 
                         sftp.mkdir(dirs[i]); 
                         sftp.cd(dirs[i]); 
                     } 
				}
            } 
            sftp.cd(now); 
        } 
        catch (SftpException e) 
        { 
			logger.error("SFTP创建文件异常"+e.toString());
			throw new Exception("SFTP创建文件异常" + e.toString()); 
        } 
    } 
    
    private static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }
	/**
	 * 上传文件
	 * 
	 * @param pathName
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param inputStream
	 *            输入文件流
	 * @return
	 */
	public boolean uploadFile(String pathName, String fileName, InputStream inputStream) {
		boolean flag = false;
		try {
//			pathName = new String(pathName.getBytes("ISO-8859-1"),"UTF-8");
//			fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
			logger.info("开始上传文件...文件名:" + fileName + "文件存放路径：" + pathName);
			initSftpClient();//初始化SFTP服务器连接
			mkDir(pathName,sftpClient);// 调用此方法去创建文件目录
			sftpClient.cd(pathName);//进入父级目录
			sftpClient.put(inputStream, fileName);//上传文件
			flag = true;
			logger.info("上传文件成功！");
		} catch (Exception e) {
			logger.error("SFTP上传异常"+e.toString());
			logger.error("上传文件失败！文件名：" + fileName + "文件存放路径：" + pathName);
			e.printStackTrace();
		} finally {
			try {

				if (null != inputStream) {
					inputStream.close();// 关闭流
				}
				if(sftpClient.isConnected()) {
					sftpClient.getSession().disconnect();
					sftpClient.disconnect();//关闭连接
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSchException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param pathName
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param originFileName
	 *            待上传文件的名称（绝对地址） *
	 * @return
	 */
	public  boolean UploadFile(String sftpPath,String fileName, String originFileName) {
		boolean flag = false;
		try {
			initSftpClient();//初始化SFTP服务器连接
			mkDir(sftpPath,sftpClient);// 调用此方法去创建文件目录
			File file = new File(originFileName);
			sftpClient.cd(sftpPath);//进入父级目录
			FileInputStream fileInputStream = new FileInputStream(file);
			sftpClient.put(fileInputStream, fileName);//上传
			flag = true;
			fileInputStream.close();
		} catch (Exception e) {
			logger.error("SFTP上传异常"+e.toString());
		} finally {
			if(sftpClient.isConnected()) {
				try {
					sftpClient.getSession().disconnect();
				} catch (JSchException e) {
				}
				sftpClient.disconnect();
			}
		}
		return flag;
	}




	
	
	/**
	 * * 下载文件 *
	 * 
	 * @param pathname
	 *            FTP服务器文件目录 *
	 * @param filename
	 *            文件名称 *
	 * @param localpath
	 *            下载后的文件路径 *
	 * @return
	 * @throws Exception 
	 */
	public  boolean downloadFileHttpBy(String pathname, String filename, String localpath,
			HttpServletResponse response) throws Exception {
		boolean flag = false;
		logger.info(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件"+filename+"开始>>>>>>>>>>>>>");  
	        initSftpClient();  
	        File file = null;  
	        OutputStream output = null;  
	        try {  
	            file = new File(localpath+"/"+filename);  
	            if (file.exists()){  
	                file.delete();  
	            }  
	            file.createNewFile();  
	            sftpClient.cd(pathname);  
	            output = new FileOutputStream(file);  
	            sftpClient.get(filename, output);
	            response.reset();
   			 //设置http头信息的内容
   			response.setCharacterEncoding("utf-8");
   			response.setContentType("application/vnd.ms-excel");
   			response.addHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
   			File localFile = new File(localpath + "/" + file.getName()); 
            FileInputStream fileIn = new FileInputStream(localFile);
            byte[] byt = new byte[fileIn.available()];
            fileIn.read(byt);
   			ServletOutputStream sos = response.getOutputStream();
   			sos.write(byt);
   			sos.flush();
   			sos.close();
	            logger.info("===DownloadFile:" + filename + " success from sftp.");  
	            flag=true;
	        }  
	        catch (SftpException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            if (e.toString().equals(NO_FILE)) {  
	            	logger.info(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件失败" + pathname +filename+ "不存在>>>>>>>>>>>>>");  
	                throw new Exception("FtpUtil-->downloadFile--ftp下载文件失败" + pathname +filename + "不存在");  
	            }  
	            throw new Exception("ftp目录或者文件异常，检查ftp目录和文件" + e.toString());  
	        }  
	        catch (FileNotFoundException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            throw new Exception("本地目录异常，请检查" + file.getPath() + e.getMessage());  
	        }  
	        catch (IOException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            throw new Exception("创建本地文件失败" + file.getPath() + e.getMessage());  
	        }  
	        finally {  
	            if (output != null) {  
	                try {  
	                    output.close();  
	                }  
	                catch (IOException e) {  
	                    throw new Exception("Close stream error."+ e.getMessage());  
	                }  
	            }  
	            disconnect();  
	        }  
	  
	        logger.info(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件结束>>>>>>>>>>>>>");  
		return flag;
	}
	
	
	/**
	 * * 下载文件 *
	 * 
	 * @param pathname
	 *            FTP服务器文件目录 *
	 * @param filename
	 *            文件名称 *
	 * @param localpath
	 *            下载后的文件路径 *
	 * @return
	 * @throws Exception 
	 */
	public boolean downloadFileHttp(String pathname, String filename, String localpath,
			HttpServletResponse response) throws Exception {
		boolean flag = false;
	        initSftpClient();  
	        File file = null;  
	        OutputStream output = null;  
	        try {  
	        	File files = new File(localpath);
		       if (!files.isDirectory()) {
		           files.mkdirs();
		       }
	            file = new File(localpath+"/"+filename);  
	            if (file.exists()){  
	                file.delete();  
	            }  
	            file.createNewFile();  
	            sftpClient.cd(pathname);  
	            output = new FileOutputStream(file);  
	            sftpClient.get(filename, output);
	            response.reset();
   			 //设置http头信息的内容
   			response.setContentType("application/force-download;charset=UTF-8");
   			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(file.getName(), "UTF-8"));
   			File localFile = new File(localpath + "/" + file.getName()); 
            FileInputStream fileIn = new FileInputStream(localFile);
            byte[] byt = new byte[fileIn.available()];
            fileIn.read(byt);
   			ServletOutputStream sos = response.getOutputStream();
   			sos.write(byt);
   			sos.flush();
   			sos.close();
	            flag=true;
	        }  
	        catch (SftpException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            if (e.toString().equals(NO_FILE)) {  
	                throw new Exception("FtpUtil-->downloadFile--ftp下载文件失败" + pathname +filename + "不存在");  
	            }  
	            throw new Exception("ftp目录或者文件异常，检查ftp目录和文件" + e.toString());  
	        }  
	        catch (FileNotFoundException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            throw new Exception("本地目录异常，请检查" + file.getPath() + e.getMessage());  
	        }  
	        catch (IOException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            throw new Exception("创建本地文件失败" + file.getPath() + e.getMessage());  
	        }  
	        finally {  
	            if (output != null) {  
	                try {  
	                    output.close();  
	                }  
	                catch (IOException e) {  
	                    throw new Exception("Close stream error."+ e.getMessage());  
	                }  
	            }  
	            disconnect();  
	        }  
		return flag;
	}
	
	
	/**
	 * * 批量下载文件 *
	 * 
	 * @param pathname
	 *            FTP服务器文件目录 *
	 * @param filename
	 *            文件名称 *
	 * @param localpath
	 *            下载后的文件路径 *
	 * @return
	 * @throws Exception 
	 */
	public boolean downloadFilesHttp(String pathname, List<String>  filenames, String localpath,
			HttpServletResponse response) throws Exception {
		boolean flag = false;
	        initSftpClient();  
	        File file = null;  
	        OutputStream output = null;  
	        try {  
	        	File files = new File(localpath);
		       if (!files.isDirectory()) {
		           files.mkdirs();
		       }
		       int i=0;
		       String zipName=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".zip";
		       	String strZipPath = localpath+"/"+zipName;
	    		 ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
	    		 out.setMethod(ZipOutputStream.DEFLATED);
		    	for(;i<filenames.size();i++) {
		    		 file = new File(localpath+"/"+filenames.get(i)); 
		    		 if (file.exists()){  
			                file.delete();  
			            }  
		    		 file.createNewFile();  
		    		 if(i==0) { 
		    			 sftpClient.cd(pathname); 
		    		} 
		    		 output = new FileOutputStream(file);
		    		 sftpClient.get(filenames.get(i), output);
		    		 FileInputStream fis = new FileInputStream(file);
		    		 out.putNextEntry(new ZipEntry(filenames.get(i)));//将ftp文件下载到本地并写入zip
		    		 byte[] byt = new byte[fis.available()];
		    		 int len;
		    		 while ((len = fis.read(byt)) > 0) {
		    			 out.write(byt);
		    		 }
		    		 fis.close();
		    		 out.closeEntry();
		    	}
		    	output.close(); 
		    	out.flush();
		    	out.close();
		    	response.reset();
	   			 //设置http头信息的内容
	   			response.setContentType("multipart/form-data;charset=UTF-8");
	   			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(zipName, "UTF-8"));
	   			File localFile = new File(localpath + "/" + zipName); 
	   			FileInputStream fileIn = new FileInputStream(localFile);
	            byte[] by = new byte[fileIn.available()];
	            int length;
	            ServletOutputStream sos = response.getOutputStream();
	            while((length = fileIn.read(by)) > 0) {
	            	sos.write(by);
	            }
				fileIn.close();
				sos.flush();
				sos.close();
   			 	flag=true;	 
	        }  
	        catch (SftpException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            if (e.toString().equals(NO_FILE)) {  
	                throw new Exception("FtpUtil-->downloadFile--ftp下载文件失败" + pathname +file.getName() + "不存在");  
	            }  
	            throw new Exception("ftp目录或者文件异常，检查ftp目录和文件" + e.toString());  
	        }  
	        catch (FileNotFoundException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            throw new Exception("本地目录异常，请检查" + file.getPath() + e.getMessage());  
	        }  
	        catch (IOException e) {  
				logger.error("SFTP下载异常"+e.toString());
	            throw new Exception("创建本地文件失败" + file.getPath() + e.getMessage());  
	        }  
	        finally {  
	            disconnect();  
	        }  
		return flag;
	}
	  
	  
	  
	  
	  

	  
	  
	  /**
	   * 删除stfp文件
	   * @param directory：要删除文件所在目录
	   * @param deleteFile：要删除的文件
	   */
	  public boolean deleteSFTP(String directory, String deleteFile)
	  {
	    try
	    {
	    	initSftpClient();//初始化SFTP服务器连接
	      sftpClient.cd(directory);
	      sftpClient.rm(directory + deleteFile);
	      return true;
	    }
	    catch (Exception e)
	    {
	    	return false;
	    }finally {  
            disconnect();  
        } 
	  }



	  /** 
     * 关闭连接 
     */  
    public static void disconnect() {  
        if (sftpClient != null) {  
            if (sftpClient.isConnected()) {  
                sftpClient.disconnect();  
                sftpClient = null;  
                logger.info("sftp is closed already");  
            }  
        }  
       
    }  
    
    

    
}
