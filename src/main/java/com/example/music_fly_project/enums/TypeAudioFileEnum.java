package com.example.music_fly_project.enums;

public enum TypeAudioFileEnum {
    MP3,
    NOT_FOUND,
    WAV;
    public static TypeAudioFileEnum findEnumValue(String value) {
        try {
            return TypeAudioFileEnum.valueOf(value);
        } catch (IllegalArgumentException e) {
            return NOT_FOUND;
        }
    }
}
