package com.example.music_fly_project.logic;

import com.example.music_fly_project.entity.SongsEntity;
import com.example.music_fly_project.vo.SongsVO;

public class Security {
    private static int numToClient=7;
    private static int numToDB=3;
    private static int specialNumToSongName(){
        return 31;
    }
    public static void encodeToClient(SongsVO songsVO){
        if(songsVO.getTheSong()!=null){
            byte[] arr=songsVO.getTheSong();
            FunSong(arr,true,numToClient);
            songsVO.setTheSong(arr);
        }
        if(songsVO.getNameSong()!=null){
            String nameSong=songsVO.getNameSong();
            nameSong=FunName(nameSong,true,numToClient);
            songsVO.setNameSong(nameSong);
        }
        if(songsVO.getZaner()!=null){
            String zaner=songsVO.getZaner();
            zaner=FunZaner(zaner,true,numToClient);
            songsVO.setZaner(zaner);
        }
    }
    public static void decipherFromClient(SongsVO songsVO){
        if(songsVO.getTheSong()!=null){
            byte[] arr=songsVO.getTheSong();
            FunSong(arr,false,numToClient);
            songsVO.setTheSong(arr);
        }
        if(songsVO.getNameSong()!=null){
            String nameSong=songsVO.getNameSong();
            nameSong=FunName(nameSong,false,numToClient);
            songsVO.setNameSong(nameSong);
        }
        if(songsVO.getZaner()!=null){
            String zaner=songsVO.getZaner();
            zaner=FunZaner(zaner,false,numToClient);
            songsVO.setZaner(zaner);
        }
    }
    public static void encodeToDB(SongsEntity songsEntity){
        if(songsEntity.getTheSong()!=null){
            byte[] arr=songsEntity.getTheSong();
            FunSong(arr,true,numToDB);
            songsEntity.setTheSong(arr);
        }
        if(songsEntity.getNameSong()!=null){
            //מיוחד כדי שנוכל לעשות LIKE
            String nameSong=songsEntity.getNameSong();
            nameSong=addingToAllString(nameSong,specialNumToSongName());
            songsEntity.setNameSong(nameSong);
        }
        if(songsEntity.getZaner()!=null){
            String zaner=songsEntity.getZaner();
            zaner=FunZaner(zaner,true,numToDB);
            songsEntity.setZaner(zaner);
        }
    }
    public static void decipherFromDB(SongsEntity songsEntity){
        if(songsEntity.getTheSong()!=null){
            byte[] arr=songsEntity.getTheSong();
            FunSong(arr,false,numToDB);
            songsEntity.setTheSong(arr);
        }
        if(songsEntity.getNameSong()!=null){
            //מיוחד כדי שנוכל לעשות LIKE
            String nameSong=songsEntity.getNameSong();
            nameSong=removingToAllString(nameSong,specialNumToSongName());
            songsEntity.setNameSong(nameSong);
        }
        if(songsEntity.getZaner()!=null){
            String zaner=songsEntity.getZaner();
            zaner=FunZaner(zaner,false,numToDB);
            songsEntity.setZaner(zaner);
        }
    }
    private static void FunSong(byte[]arr ,boolean status,int divisionNumber){
        int key=(arr[0]+arr[arr.length-1])/divisionNumber;
        System.out.println(key);
        if(status){
            adding(arr,key);
            rotateArrayByKey(arr,key,1,arr.length-2);
            return;
        }
        reverseRotateArrayByKey(arr,key,1,arr.length-2);
        removeAdding(arr,key);
    }
    private static String FunName(String str ,boolean status,int  divisionNumber){
        int key=(str.charAt(0)+str.charAt(str.length()-1))/divisionNumber;
        if(status){
            str=adding(str,key);
            return str;
        }
        str =removeAdding(str,key);
        return str;
    }
    private static String FunZaner(String str , boolean status, int divisionNumber){
        int key=(str.charAt(0)+str.charAt(str.length()-1))/divisionNumber;
        if(status){
            str=removeAdding(str,key);
            return str;
        }
        str=adding(str,key);
        return str;
    }
    private static void removeAdding(byte[]bytesArray,int key){
        int j;
        for (int i = 1; i < bytesArray.length-1;) {
            for (j = 0; j <Math.abs(key) ; j++) {
                if(i>= bytesArray.length-1){
                    break;
                }
                bytesArray[i]-=key;
                i++;
            }
            for (j = 0; j <Math.abs(key) ; j++) {
                if(i>= bytesArray.length-1){
                    break;
                }
                bytesArray[i]+=key;
                i++;
            }
        }
    }
    private static void reverseRotateArrayByKey(byte[] arr, int key, int startIndex, int endIndex) {
        int rotations = key % (endIndex - startIndex + 1);
        System.out.println("rotate:"+rotations);
        for (int j = 0; j < rotations; j++) {
            byte temp = arr[endIndex];
            for (int i = endIndex; i > startIndex; i--) {
                arr[i] = arr[i - 1];
            }
            arr[startIndex] = temp;
        }
    }
    private static void adding(byte[]bytesArray,int key){
        int j;
        for (int i = 1; i < bytesArray.length-1;) {
            for (j = 0; j <Math.abs(key) ; j++) {
                if(i>= bytesArray.length-1){
                    break;
                }
                bytesArray[i]+=key;
                i++;
            }
            for (j = 0; j <Math.abs(key) ; j++) {
                if(i>= bytesArray.length-1){
                    break;
                }
                bytesArray[i]-=key;
                i++;
            }
        }
    }
    private static void rotateArrayByKey(byte[] arr, int key, int startIndex, int endIndex) {
        int rotations = key % (endIndex - startIndex + 1);
        for (int j = 0; j < rotations; j++) {
            byte temp = arr[startIndex];
            for (int i = startIndex; i < endIndex; i++) {
                arr[i] = arr[i + 1];
            }
            arr[endIndex] = temp;
        }
    }
    private static String adding(String str,int key){
        char[] charArray = str.toCharArray();
        for (int i = 1; i < charArray.length-1;i++) {
                charArray[i]+=key;
            }
        return new String(charArray);
    }
    private static String addingToAllString(String str,int key){
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length;i++) {
            charArray[i]+=key;
        }
        return new String(charArray);
    }
    private static String removingToAllString(String str,int key){
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length;i++) {
            charArray[i]-=key;
        }
        return new String(charArray);
    }
    private static String removeAdding(String str,int key){
        char[] charArray = str.toCharArray();
        for (int i = 1; i < charArray.length-1;i++) {
                charArray[i]-=key;
        }
        return new String(charArray);
    }

}
