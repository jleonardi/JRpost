package edu.bluejack16_2.jrpost.utilities;

/**
 * Created by User on 7/8/2017.
 */

public class LanguageModel {
    private String languageCode;
    private String languageName;

    public LanguageModel(String languageCode, String languageName) {
        this.languageCode = languageCode;
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
