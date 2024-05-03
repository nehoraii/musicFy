package com.example.music_fly_project.logic;

import com.example.music_fly_project.vo.SongsVO;
//קלאס שאחראי על ההצפנות בשרת זה
public class Security {
    private static final int  numToClient=7;//שדה המכיל מספר קבוע  שעוזר להצפנה בתקשורת בין השרת לקליינט והפוך.
    private static final int numToDB=3;//שדה המכיל מספר קבוע  שעוזר להצפנה בתקשורת בין השרת למסד הנתונים והפוך.
    private static final int specialNumToSongName=  31;//שדה המכיל מספר קבוע אשר עוזר להצפנה של שם השיר.

    /*
    מקבלת: אובייקט המייצג את השיר.
    מבצעת: מצפינה אותו בשביל הקליינט.
    מחזירה: כלום.
    */
    public static void encodeToClient(SongsVO songsVO){
        if(songsVO.getTheSong()!=null){
            byte[] arr=songsVO.getTheSong();
            funSong(arr,true,numToClient);
            songsVO.setTheSong(arr);
        }
        if(songsVO.getNameSong()!=null){
            String nameSong=songsVO.getNameSong();
            nameSong=funName(nameSong,true,numToClient);
            songsVO.setNameSong(nameSong);
        }
        if(songsVO.getZaner()!=null){
            String zaner=songsVO.getZaner();
            zaner=funZaner(zaner,true,numToClient);
            songsVO.setZaner(zaner);
        }
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: מפענחת אותו מהקליינט.
    מחזירה: כלום.
    */
    public static void decipherFromClient(SongsVO songsVO){
        if(songsVO.getTheSong()!=null){
            byte[] arr=songsVO.getTheSong();
            funSong(arr,false,numToClient);
            songsVO.setTheSong(arr);
        }
        if(songsVO.getNameSong()!=null){
            String nameSong=songsVO.getNameSong();
            nameSong=funName(nameSong,false,numToClient);
            songsVO.setNameSong(nameSong);
        }
        if(songsVO.getZaner()!=null){
            String zaner=songsVO.getZaner();
            zaner=funZaner(zaner,false,numToClient);
            songsVO.setZaner(zaner);
        }
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: מצפינה אותו למסד המידע..
    מחזירה: כלום.
    */
    public static void encodeToDB(SongsVO songsVO){
        if(songsVO.getTheSong()!=null){
            byte[] arr=songsVO.getTheSong();
            funSong(arr,true,numToDB);
            songsVO.setTheSong(arr);
        }
        if(songsVO.getNameSong()!=null){
            //מיוחד כדי שנוכל לעשות LIKE
            String nameSong=songsVO.getNameSong();
            nameSong=addingToAllString(nameSong,specialNumToSongName);
            songsVO.setNameSong(nameSong);
        }
        if(songsVO.getZaner()!=null){
            String zaner=songsVO.getZaner();
            zaner=funZaner(zaner,true,numToDB);
            songsVO.setZaner(zaner);
        }
    }


    /*
    מקבלת: אובייקט המייצג שיר.
    מבצעת: מפענחת אותו מהמסד מידע.
    מחזירה: כלום.
    */
    public static void decipherFromDB(SongsVO songsEntity){
        if(songsEntity.getTheSong()!=null){
            byte[] arr=songsEntity.getTheSong();
            funSong(arr,false,numToDB);
            songsEntity.setTheSong(arr);
        }
        if(songsEntity.getNameSong()!=null){
            //מיוחד כדי שנוכל לעשות LIKE
            String nameSong=songsEntity.getNameSong();
            nameSong=removingToAllString(nameSong,specialNumToSongName);
            songsEntity.setNameSong(nameSong);
        }
        if(songsEntity.getZaner()!=null){
            String zaner=songsEntity.getZaner();
            zaner=funZaner(zaner,false,numToDB);
            songsEntity.setZaner(zaner);
        }
    }
    /*public static void decipherFromDB(SongsEntity songsEntity){
        if(songsEntity.getNameSong()!=null){
            //מיוחד כדי שנוכל לעשות LIKE
            String nameSong=songsEntity.getNameSong();
            nameSong=removingToAllString(nameSong,specialNumToSongName);
            songsEntity.setNameSong(nameSong);
        }
        if(songsEntity.getZaner()!=null){
            String zaner=songsEntity.getZaner();
            zaner=funZaner(zaner,false,numToDB);
            songsEntity.setZaner(zaner);
        }
    }

     */


    /*
    מקבלת: מערך של בייתים מצב ומספר החלוקה.
    מבצעת: במידה והמצב זה אמת אז היא מצפינה ובמידה וזה שקר היא מפענחת, מהמספר חלוקה מוציאה את המפתח.
    מחזירה: כלום.
    */
    private static void funSong(byte[]arr ,boolean status,int divisionNumber){
        int a=arr[0];
        int b=arr[arr.length-1];
        int key=(arr[0]+arr[arr.length-1])/divisionNumber;
        System.out.println(key);
        if(key==0){
            return;
        }
        if(status){
            System.out.println("key: " + key);
            System.out.println("a = " + a + " b = " + b);
            adding(arr,key,1,arr.length-1);
            rotateArrayByKey(arr,key,1,arr.length-2);
            return;
        }
        reverseRotateArrayByKey(arr,key,1,arr.length-2);
        removeAdding(arr,key);
    }


    /*
    מקבלת: שם של השיר מצב ומספר החלוקה.
    מבצעת: במידה והמצב זה אמת אז היא מצפינה ובמידה וזה שקר היא מפענחת, מהמספר חלוקה מוציאה את המפתח.
    מחזירה: כלום.
    */
    private static String funName(String str ,boolean status,int  divisionNumber){
        int key=(str.charAt(0)+str.charAt(str.length()-1))/divisionNumber;
        if(key==0){
            return str;
        }
        if(status){
            str=adding(str,key,1,str.length()-1);
            return str;
        }
        str =removeAdding(str,key,1,str.length()-1);
        return str;
    }


    /*
    מקבלת: ז'אנר של השיר מצב ומספר החלוקה.
    מבצעת: במידה והמצב זה אמת אז היא מצפינה ובמידה וזה שקר היא מפענחת, מהמספר חלוקה מוציאה את המפתח.
    מחזירה: כלום.
    */
    private static String funZaner(String str , boolean status, int divisionNumber){
        int key=(str.charAt(0)+str.charAt(str.length()-1))/divisionNumber;
        if(key==0){
            return str;
        }
        if(status){
            str=removeAdding(str,key,1,str.length()-1);
            return str;
        }
        str=adding(str,key,1,str.length()-1);
        return str;
    }


    /*
    מקבלת: מערך של בייתים ואת המפתח.
    מבצעת:מחסירה מכל האיברים במערך למעט האיבר הראשון והאחרון את המפתח.
    מחזירה: כלום.
    */
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


    /*
    מקבלת: מערך של בייתים מפתח אינדקס התחלה אינדקס סוף.
    מבצעת: מסובבת בגודל המפתח את כל האיברים בין הטווחים של שתי האינדקסים.
    מחזירה: כלום.
    */
    private static void reverseRotateArrayByKey(byte[] arr, int key, int startIndex, int endIndex) {
        int rotations = key % (endIndex - startIndex + 1);
        for (int j = 0; j < rotations; j++) {
            byte temp = arr[endIndex];
            for (int i = endIndex; i > startIndex; i--) {
                arr[i] = arr[i - 1];
            }
            arr[startIndex] = temp;
        }
    }


    /*
    מקבלת: מערך של בייתים מפתח אינדקס התחלה אינדקס סוף.
    מבצעת: מוסיפה את הערך של המפתח לכל איבר בתחום של האינדקס ההתחלה והסוף.
    מחזירה: כלום.
    */
    private static void adding(byte[]bytesArray,int key,int startIndex,int lastIndex){
        int j;
        for (int i = startIndex; i < lastIndex;) {
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


    /*
     מקבלת: מערך של בייתים מפתח אינדקס התחלה ואינדקס סוף.
    מבצעת: מסובבת קדימה את כל האיברים בטווח שבין האינדקס התחלה לאינדקס סוף בגודל המפתח.
    מחזירה: כלום.
     */
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


    /*
       מקבלת: מחרוזת,מפתח אינדקס התחלה אינדקס סוף.
    מבצעת: מוסיפה לכל איבר בטווח שבין האינדקס התחלה לאינקס סוף את הערך של המפתח.
מחזירה: את המחרוזת החדשה.
    */
    private static String adding(String str,int key,int startIndex,int lastIndex){
        char[] charArray = str.toCharArray();
        for (int i = startIndex; i < lastIndex;i++) {
                charArray[i]+=key;
            }
        return new String(charArray);
    }


    /*
    מקבלת: מחרוזת,מפתח.
    מבצעת: מוסיפה לכל המחרוזת את המפתח.
    מחזירה: את המחרוזת החדשה.
    */
    private static String addingToAllString(String str,int key){
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length;i++) {
            charArray[i]+=key;
        }
        return new String(charArray);
    }


    /*
    מקבלת: מחרוזת,מפתח.
    מבצעת: מחסירה לכל המחרוזת את המפתח.
    מחזירה: את המחרוזת החדשה.
    */
    private static String removingToAllString(String str,int key){
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length;i++) {
            charArray[i]-=key;
        }
        return new String(charArray);
    }


    /*
    מקבלת: מחרוזת,מפתח אינדקס התחלה אינדקס סוף.
    מבצעת: מחסירה לכל איבר בטווח שבין האינדקס התחלה לאינקס סוף את הערך של המפתח.
    מחזירה: את המחרוזת החדשה.
    */
    private static String removeAdding(String str,int key,int startIndex,int lastIndex){
        char[] charArray = str.toCharArray();
        for (int i = startIndex; i < lastIndex;i++) {
                charArray[i]-=key;
        }
        return new String(charArray);
    }

}
