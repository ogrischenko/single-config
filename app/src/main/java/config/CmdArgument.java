package config;

public class CmdArgument
{

    private final String key;
    private final String value;

    public CmdArgument(String key, String value) {

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}