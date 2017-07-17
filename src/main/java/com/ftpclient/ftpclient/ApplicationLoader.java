package com.ftpclient.ftpclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ApplicationLoader implements CommandLineRunner {

    @Autowired
    FtpClientService ftpService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNIG...");
        if (args.length == 0) {
            System.err.println("Enter server port username password line same order");
        }

        String server = args[0];
        int port = Integer.parseInt(args[1]);
        String user = args[2];
        String password = args[3];

        // get ftp connected
        FTPClient client = FtpClientUtil.connect(server, port, user, password);

        List<File> downloadedFiles = ftpService.downloadAllFiles(client);

        if (downloadedFiles != null && !downloadedFiles.isEmpty()) {
            for (File file : downloadedFiles) {
                String fileExt = file.getName().substring(file.getName().lastIndexOf("."));
                ;
                if (fileExt.equalsIgnoreCase(".pdf")) {
                    try {

                        PDFParser pdfParser = new PDFParser(new RandomAccessFile(file, "r"));
                        pdfParser.parse();
                        PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
                        PDFTextStripper pdfTextStripper = new PDFTextStripper();

                        String str = pdfTextStripper.getText(pdDocument);
                        System.out.println(str);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
