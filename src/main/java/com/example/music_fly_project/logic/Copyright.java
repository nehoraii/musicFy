package com.example.music_fly_project.logic;
import org.apache.tika.Tika;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Copyright {
    //בדיקה של X שניות תחילת השיר ו-X שניות סוף השיר אין בדיקה של אמצע שיר
    private static int durationInSeconds;
    private static int sampleRate=44100;
    private static int numChannels=2;
    private static int presentToCheck=65;
    private static double presentOfTime=25.0;//X אחוזים בדיקה מאורך השיר
    private static String identifyAudioFileType(byte[] audioData) {
        Tika tika = new Tika();
        String format;
        try {
            format = tika.detect(new ByteArrayInputStream(audioData));
            return format;
        } catch (IOException e) {
            return null;
        }
    }
    private static void putValue(byte[] audio1,int sampleRate, int numChannels) {
        int numSamplesPerChannel;
        numSamplesPerChannel = audio1.length / numChannels; // מספר הדגימות בערוץ
        durationInSeconds = numSamplesPerChannel / sampleRate; // משך הקובץ השמע בשניות
    }
    private static float checkStart(byte[] audioSource, byte[] audioImitation, int numSamplesPerXSeconds){
        float ratio;
        float present;
        int countCopyright=0;
        int offset;
        int maxCountCopyright=0;
        for (int i = 0; i <numSamplesPerXSeconds; i++) {
            countCopyright=0;
            offset=0;
            for (int j = 0; j < numSamplesPerXSeconds; j++) {
                //System.out.println(countCopyright);
                if(audioSource[i+offset]==audioImitation[j]){
                    //System.out.println("audioSource: "+audioSource[i+offset]+ "audioImitation: "+audioImitation[j]);
                    countCopyright++;
                    offset++;
                }
                else {
                    if(countCopyright>maxCountCopyright){
                        maxCountCopyright=countCopyright;
                    }
                    countCopyright=0;
                    offset=0;
                }
            }
            if(countCopyright>maxCountCopyright){
                maxCountCopyright=countCopyright;
            }
            ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
            present=ratio*100;
            if(present>=presentToCheck){
                System.out.println(maxCountCopyright);
                System.out.println(numSamplesPerXSeconds);
                System.out.println(present);
                return present;
            }
        }
        if(countCopyright>maxCountCopyright){
            maxCountCopyright=countCopyright;
        }
        System.out.println(maxCountCopyright);
        System.out.println(numSamplesPerXSeconds);
        ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
        present=ratio*100;
        System.out.println(present);
        return present;
    }
    private static float checkEnd(byte[] audioSource, byte[] audioImitation, int numSamplesPerXSeconds){
        float ratio;
        float present;
        int countCopyright=0;
        int offset;
        int maxCountCopyright=0;

        for (int i = 0; i <numSamplesPerXSeconds; i++) {
            byte temp=audioSource[i];
            audioSource[i]=audioSource[audioSource.length-i-1];
            audioSource[audioSource.length-1]=temp;
        }
        for (int i = 0; i <numSamplesPerXSeconds; i++) {
            byte temp=audioImitation[i];
            audioImitation[i]=audioImitation[audioImitation.length-i-1];
            audioImitation[audioImitation.length-1]=temp;
        }
        for (int i = numSamplesPerXSeconds-1; i > 0; i--) {
            countCopyright=0;
            offset=0;
            for (int j = numSamplesPerXSeconds-1; j > 0; j--) {
                //System.out.println(countCopyright);
                if(i+offset<0){break;}
                if(audioSource[i+offset]==audioImitation[j]){
                    //System.out.println("audioSource: "+audioSource[i+offset]+ "audioImitation: "+audioImitation[j]);
                    countCopyright++;
                    offset--;
                }
                else {
                    if(countCopyright>maxCountCopyright){
                        maxCountCopyright=countCopyright;
                    }
                    countCopyright=0;
                    offset=0;
                }
            }
            if(countCopyright>maxCountCopyright){
                maxCountCopyright=countCopyright;
            }
            ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
            present=ratio*100;
            if(present>=presentToCheck){
                System.out.println(maxCountCopyright);
                System.out.println(numSamplesPerXSeconds);
                System.out.println(present);
                return present;
            }
        }
        if(countCopyright>maxCountCopyright){
            maxCountCopyright=countCopyright;
        }
        System.out.println(maxCountCopyright);
        System.out.println(numSamplesPerXSeconds);
        ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
        present=ratio*100;
        System.out.println(present);
        return present;
    }
    public static boolean compareFirst5Seconds(byte[] audioSource, byte[] audioImitation) {
        System.out.println(audioSource.length);
        System.out.println(audioImitation.length);
        //audioSource=byteArrayToWav(audioSource);
        //audioImitation=byteArrayToWav(audioImitation);
        putValue(audioSource,sampleRate ,numChannels);
        double amountOfSecondsToCheck=(durationInSeconds*presentOfTime)/100;//כמה אחוז מהשיר לבדוק
        int numSamplesPerXSeconds = (int) (amountOfSecondsToCheck * durationInSeconds);
        float present2=checkStart(audioSource,audioImitation,numSamplesPerXSeconds);
        if(present2>=presentToCheck){
            return true;
        }
        present2=checkEnd(audioSource,audioImitation,numSamplesPerXSeconds);
        if(present2>=presentToCheck){
            return true;
        }
        return false;
        /*
        if (audioSource == null || audioImitation == null || audioSource.length < numSamplesPerXSeconds) {
            return false;
        }
        float ratio;
        float present;
        int countCopyright=0;
        int offset;
        int maxCountCopyright=0;
        for (int i = 0; i <audioSource.length; i++) {
            byte temp=audioSource[i];
            audioSource[i]=audioSource[audioSource.length-i-1];
            audioSource[audioSource.length-1]=temp;
        }
        for (int i = 0; i <audioImitation.length; i++) {
            byte temp=audioImitation[i];
            audioImitation[i]=audioImitation[audioImitation.length-i-1];
            audioImitation[audioImitation.length-1]=temp;
        }
        for (int i = 0; i <numSamplesPerXSeconds; i++) {
            countCopyright=0;
            offset=0;
            for (int j = 0; j < numSamplesPerXSeconds; j++) {
                //System.out.println(countCopyright);
                if(audioSource[i+offset]==audioImitation[j]){
                    //System.out.println("audioSource: "+audioSource[i+offset]+ "audioImitation: "+audioImitation[j]);
                    countCopyright++;
                    offset++;
                }
                else {
                    if(countCopyright>maxCountCopyright){
                        maxCountCopyright=countCopyright;
                    }
                    countCopyright=0;
                    offset=0;
                }
            }
            if(countCopyright>maxCountCopyright){
                maxCountCopyright=countCopyright;
            }
            ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
            present=ratio*100;
            if(present>=presentToCheck){
                System.out.println(maxCountCopyright);
                System.out.println(numSamplesPerXSeconds);
                System.out.println(present);
                return true;
            }
        }
        if(countCopyright>maxCountCopyright){
            maxCountCopyright=countCopyright;
        }
        System.out.println(maxCountCopyright);
        System.out.println(numSamplesPerXSeconds);
        ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
        present=ratio*100;
        System.out.println(present);
        return present>=presentToCheck;
         */
    }
    private static byte[] byteArrayToWav(byte[] audioData) {
        if(identifyAudioFileType(audioData).toUpperCase().contains("WAV")){
            return audioData;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, AudioSystem.getAudioInputStream(byteArrayInputStream).getFormat(), audioData.length);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
