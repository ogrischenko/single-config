package config;

public class StrUtils {
    public static String QuotStr(String str) {
        return str.replace("\\", "\\\\");
    }
}
