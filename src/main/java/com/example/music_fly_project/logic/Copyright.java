package com.example.music_fly_project.logic;

import com.example.music_fly_project.enums.TypeAudioFileEnum;
import org.apache.tika.Tika;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Copyright {
    private static int durationInSeconds;
    private static int sampleRate=44100;
    private static int numChannels=2;
    private static int offsetInSeconds = 0;
    private static int presentToCheck=55;
    private static double amountOfSecondsToCheck=30.0;
    private static TypeAudioFileEnum identifyAudioFileType(byte[] audioData) {
        Tika tika = new Tika();
        String format;
        try {
            format = tika.detect(new ByteArrayInputStream(audioData));
            TypeAudioFileEnum t=TypeAudioFileEnum.findEnumValue(format);
            return t;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void putValue(byte[] audio1,int sampleRate, int numChannels) {
        int numSamplesPerChannel;
        numSamplesPerChannel = audio1.length / numChannels; // מספר הדגימות בערוץ
        durationInSeconds = numSamplesPerChannel / sampleRate; // משך הקובץ השמע בשניות
    }
    public static boolean compareFirst5Seconds(byte[] audioSource, byte[] audioImitation) {
        System.out.println("source: "+ identifyAudioFileType(audioSource) + " fake: " + identifyAudioFileType(audioImitation));
        putValue(audioSource,sampleRate ,numChannels);
        int numSamplesPerXSeconds = (int) (amountOfSecondsToCheck * durationInSeconds);

        int numSamplesOffset = offsetInSeconds * durationInSeconds;

        if (audioSource == null || audioImitation == null || audioSource.length < numSamplesPerXSeconds || audioImitation.length < numSamplesPerXSeconds + numSamplesOffset) {
            return false;
        }
        int countCopyright=0;
        int offset;
        int maxCountCopyright=0;
        for (int i = 0; i <numSamplesPerXSeconds; i++) {
            countCopyright=0;
            offset=0;
            for (int j = i; j < numSamplesPerXSeconds; j++) {
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
        }
        if(countCopyright>maxCountCopyright){
            maxCountCopyright=countCopyright;
        }
        System.out.println(maxCountCopyright);
        System.out.println(numSamplesPerXSeconds);
        float ratio=(float)maxCountCopyright/(float) numSamplesPerXSeconds;
        float present=ratio*100;
        System.out.println(present);
        return present>=presentToCheck;
    }

}
