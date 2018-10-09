package com.interopx.platform.agent.util;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPConnector {
	

	public static void uploadFiles(String SFTPHOST, Integer SFTPPORT, String SFTPUSER, String SFTPPASSWORD, String SFTPSSHKEY, String SFTPWORKINGDIR, String filesDir) {

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			if(SFTPSSHKEY!=null&&!SFTPSSHKEY.isEmpty())
				jsch.addIdentity(SFTPSSHKEY);
			
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			if(SFTPPASSWORD!=null &&!SFTPPASSWORD.isEmpty())
				session.setPassword(SFTPPASSWORD);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			
			/*File f = new File(fileDir);
			channelSftp.put(new FileInputStream(f), f.getName());*/
			
			
			File localDir = new File(filesDir);
			 if (localDir.exists()) {
			  @SuppressWarnings("unchecked")
			  Collection<File> fileList = FileUtils.listFiles(localDir, TrueFileFilter.TRUE, null);
			 
			  for (File file : fileList) {
			   if (file.exists()) {
				   channelSftp.put(file.getAbsolutePath(), file.getName());
			    System.out.println("uploading - " + file.getName());
			   }
			  }
			  System.out.println("File(s) transfered successfully to host.");
			 } else {
				 System.err.println("local folder \"" + localDir.getAbsolutePath() + "\" does not exist");
			 }
		} catch (Exception ex) {
			System.out.println("Exception found while tranfer the response.");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {

			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}

}
