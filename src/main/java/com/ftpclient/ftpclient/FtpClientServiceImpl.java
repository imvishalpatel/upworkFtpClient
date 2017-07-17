package com.ftpclient.ftpclient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

@Service
public class FtpClientServiceImpl implements FtpClientService {

    @Override
    public List<File> downloadAllFiles(FTPClient client) throws IOException {
        List<File> downloadedFiles = new ArrayList<>();
        FTPFile[] files = client.listFiles();
        for (FTPFile file : files) {

            if (file.isFile()) {

                String fileName = file.getName();

                String fileExt = fileName.substring(fileName.lastIndexOf("."));

                if (fileExt.equalsIgnoreCase(".pdf") || fileExt.equalsIgnoreCase(".txt")
                        || fileExt.equalsIgnoreCase(".csv")) {

                    File downloadFile = new File(fileName);
                    OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(
                            downloadFile));
                    boolean success = client.retrieveFile(fileName, outputStream1);
                    outputStream1.close();

                    if (success) {
                        downloadedFiles.add(downloadFile);
                        System.out.println("File " + fileName
                                + " has been downloaded successfully.");
                    }
                }
            }
        }

        return downloadedFiles;
    }

}
