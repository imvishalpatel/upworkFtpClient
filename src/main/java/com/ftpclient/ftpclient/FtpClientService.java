package com.ftpclient.ftpclient;

import java.io.File;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

public interface FtpClientService {
    public List<File> downloadAllFiles(FTPClient client) throws Exception;
}
