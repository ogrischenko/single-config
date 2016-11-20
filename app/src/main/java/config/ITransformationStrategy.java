package config;

public interface ITransformationStrategy {
    String Transform(String text, String selectedPattern);

    void OverrideTransformationParameters(ConfigFileTransformation transformationOverride);
}
