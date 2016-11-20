package config;


import run.Configurator;

public class StringReplaceConfigFileTransformationStrategy implements ITransformationStrategy {
    private ConfigFileTransformation _transformation;

    public StringReplaceConfigFileTransformationStrategy(ConfigFileTransformation transformation) {
        _transformation = transformation;
    }

    public String Transform(String text, String selectedPattern) {
        text = text.replace(selectedPattern, _transformation.ReplaceTo);

        String quot = String.format("%s%s%s", Configurator.PatternSig, SysTokenKeys.Quoted, selectedPattern.substring(1));
        text = text.replace(quot, StrUtils.QuotStr(_transformation.ReplaceTo));

        return text;
    }

    public void OverrideTransformationParameters(ConfigFileTransformation transformationOverride) {
        _transformation.ReplaceTo = transformationOverride.ReplaceTo;
    }
}