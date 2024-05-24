package anton3413.telegramlanguagebot.Model;

public enum TranslateLanguage {

    TRANSLATE_LANGUAGE_ENGLISH("en","English"),
    TRANSLATE_LANGUAGE_SPANISH("es","Español"),
    TRANSLATE_LANGUAGE_GERMAN("de","Deutsch"),
    TRANSLATE_LANGUAGE_ITALIAN("it","Italiano"),
    TRANSLATE_LANGUAGE_RUSSIAN("ru","Русский"),
    TRANSLATE_LANGUAGE_FRENCH("fr","Français"),
    TRANSLATE_LANGUAGE_UKRAINIAN("uk", "Українська"),
    TRANSLATE_LANGUAGE_CHINESE("zh", "中文"),
    TRANSLATE_LANGUAGE_JAPANESE("ja", "日本語"),
    TRANSLATE_LANGUAGE_PORTUGUESE("pt", "Português"),
    TRANSLATE_LANGUAGE_HINDI("hi", "हिन्दी"),
    TRANSLATE_LANGUAGE_ARABIC("ar", "العربية");

    private final  String shortName;
    private final String fullName;

    TranslateLanguage(String shortName, String fullName){
        this.shortName = shortName;
        this.fullName = fullName;
    }
    public String getShortName(){
        return this.shortName;
    }

    public String getFullName(){
        return this.fullName;
    }

}
