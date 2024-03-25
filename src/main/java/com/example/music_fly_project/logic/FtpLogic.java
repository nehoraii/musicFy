package com.example.music_fly_project.logic;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class FtpLogic {
    private static String server="127.0.0.1";
    private static int port=21;
    private static String userRequest="2";
    private static String passRequest="2";
    private static String pathToSaveSong="C:\\Users\\user\\Desktop\\w";
    public static String getPath(){
        return pathToSaveSong;
    }
    public static boolean uploadFile(Long user, Long pass,Long songId, byte[] song,Long nameFile) {
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
            String pathToSave=pathToSaveSong + "\\" + user + "\\" + songId +"\\" + nameFile;
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
    public static byte[] requestFileFromServer(String pathSong) {
        FTPClient ftpClient = new FTPClient();
        OutputStream outputStream=new ByteArrayOutputStream();;
        try {
            ftpClient.connect(server, port);
            ftpClient.login(userRequest,passRequest);
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
    public static boolean deleteFile(String path,Long user,Long pass){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean sec=ftpClient.login(Long.toString(user),Long.toString(pass));
            if(sec==false){
                return false;
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // יצירת OutputStream עבור הקובץ המקומי שבו נשמור את הקובץ המורד
            // בקשה להוריד את הקובץ מהשרת
            System.out.println("Start delete file from server");
            boolean success = ftpClient.deleteFile(path);
            if (success) {
                System.out.println("The file is delete successfully.");

            } else {
                System.out.println("Failed to delete the file.");
                return false;
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
        return true;
    }
    public static boolean createDirectory(Long songId,Long user,Long pass){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean sec=ftpClient.login(Long.toString(user),Long.toString(pass));
            if(sec==false){
                return false;
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // יצירת OutputStream עבור הקובץ המקומי שבו נשמור את הקובץ המורד
            // בקשה להוריד את הקובץ מהשרת
            System.out.println("Start create dir from server");
            boolean success = ftpClient.makeDirectory(getPath()+"\\"+Long.toString(user)+"\\"+Long.toString(songId));
            if (success) {
                System.out.println("The create dir successfully.");

            } else {
                System.out.println("Failed create dir.");
                return false;
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
        return true;
    }
    public static int getAmountOfFiles(String path){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean sec=ftpClient.login(userRequest,passRequest);
            if(sec==false){
                return 0;
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);

            // יצירת OutputStream עבור הקובץ המקומי שבו נשמור את הקובץ המורד
            // בקשה להוריד את הקובץ מהשרת
            int length = ftpClient.listFiles().length;
            return length;
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
        return -1;
    }
    /*public static boolean deleteDirectory(long songId,long user,long pass){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean sec=ftpClient.login(Long.toString(user),Long.toString(pass));
            if(sec==false){
                return false;
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // יצירת OutputStream עבור הקובץ המקומי שבו נשמור את הקובץ המורד
            // בקשה להוריד את הקובץ מהשרת
            System.out.println("Start delete file from server");
            boolean success = ftpClient.makeDirectory(getPath()+"\\"+Long.toString(songId));
            if (success) {
                System.out.println("The file is delete successfully.");

            } else {
                System.out.println("Failed to delete the file.");
                return false;
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
        return true;
    }

     */
}
