package config;


public interface ITemplateProcessor {
    void SkipTransform(String fullFilePath, String newFilePath);

    void SaveTransformResult(String templateFullFilePath, String newFilePath, String fileText, String encoding);

//    void WriteCopyTo(String newFilePath, String newCopyFilePath);
}
