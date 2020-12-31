package com.cmsz.paas.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;

public class CommandUtil {

	private static Logger logger = Logger.getLogger(CommandUtil.class);

	/**
	 * 执行命令
	 * 
	 * @param command
	 *            需要执行的命令
	 * @throws Exception
	 */
	public static String execCmd(String command) throws Exception {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			Runtime runTime = Runtime.getRuntime();
			Process p = runTime.exec(command);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			logger.error("Execute command error. ", e);
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					logger.error("Close BufferedReader error. ", e);
					throw e;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 执行命令
	 * 
	 * @param command
	 *            需要执行的命令
	 * @throws Exception
	 */
	public static void execCmdWithTimeout( String command, Long timeout ) 
			throws Exception {
		try {
			logger.debug( "execCmdWithTimeout begin try" );
			Runtime runTime = Runtime.getRuntime();
			Process p = runTime.exec( command );
			synchronized( p ) {
				p.wait( timeout );
			}
		} catch (Exception e) {
			logger.error("Execute command error. ", e);
			throw e;
		}
	}
	
	public static CommandRet execCmdWithRetCode(String command, Long timeout) {
		int ret = 0;
		CommandRet comRet = new CommandRet();
		
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			logger.debug("execCmdWithTimeout begin try");
			Runtime runTime = Runtime.getRuntime();
			Process p = runTime.exec(command);
			synchronized (p) {
				p.wait(timeout);
			}
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			ret = p.exitValue();
		} catch (Exception e) {
			logger.error("Execute command error. ", e);
			comRet.setRet(-1);
			comRet.setRetMsg(e.getMessage());
			return comRet;
		}
		comRet.setRet(ret);
		comRet.setRetMsg(sb.toString());
		return comRet;
	}
}
