package config;

import netutil.NetString;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TemplateConfigureProcessor implements ITemplateProcessor {
    public void SkipTransform(String fullFilePath, String newFilePath) {
        try {
            FileUtils.copyFile(new File(fullFilePath), new File(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SaveTransformResult(String templateFullFilePath, String newFilePath, String fileText, String encoding) {
        if (NetString.IsNullOrEmpty(encoding)) {
            try {
                FileUtils.writeStringToFile(new File(newFilePath), fileText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.writeStringToFile(new File(newFilePath), fileText, encoding);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public void WriteCopyTo(String newFilePath, String newCopyFilePath) {
//        var newCopyFileDir = Path.GetDirectoryName(newCopyFilePath);
//
//        if (!Directory.Exists(newCopyFileDir)) {
//            Directory.CreateDirectory(newCopyFileDir);
//        }
//
//        NetFile.Copy(newFilePath, newCopyFilePath, true);
//    }
}