package com.example.music_fly_project.logic;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class FtpLogic {
    private static String pathToSaveSong="C:\\Users\\user\\Desktop\\w\\";
    public static String getPath(){
        return pathToSaveSong;
    }
    public static boolean uploadFile(String server, int port, long user, long pass, byte[] song,long nameFile) {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            boolean sec=ftpClient.login(Long.toString(user), Long.toString(pass));
            if(sec==false){
                Thread.sleep(10000);
                ftpClient.login(Long.toString(user), Long.toString(pass));
            }
            System.out.println(sec);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


            ByteArrayInputStream inputStream = new ByteArrayInputStream(song);
            String pathToSave=pathToSaveSong+"\\"+user+"\\"+nameFile;
            System.out.println("Start uploading file");
            boolean done = ftpClient.storeFile(pathToSave, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The file is uploaded successfully.");
                return true;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }
    public static byte[] requestFileFromServer(String server, int port, long user, long pass, String pathSong) {
        FTPClient ftpClient = new FTPClient();
        OutputStream outputStream=new ByteArrayOutputStream();;
        try {
            ftpClient.connect(server, port);
            boolean sec=ftpClient.login(Long.toString(user),Long.toString(pass));
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // יצירת OutputStream עבור הקובץ המקומי שבו נשמור את הקובץ המורד
            // בקשה להוריד את הקובץ מהשרת
            System.out.println("Start downloading file from server");
            boolean success = ftpClient.retrieveFile(pathSong, outputStream);
            outputStream.close();
            if (success) {
                System.out.println("The file is downloaded successfully.");

            } else {
                System.out.println("Failed to download the file.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }
}
