package netutil;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Oleg on 20.11.2016.
 */
public class NetFile {
    public static boolean Exists(String configFullPath) {
        return new File(configFullPath).exists();
    }

    public static String ReadAllText(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
