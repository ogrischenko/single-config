package netutil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Oleg on 20.11.2016.
 */
public class NetPath {
    public static String Combine(String first, String second) {
        File file1 = new File(first);
        File file2 = new File(file1, second);
        return file2.getPath();
    }

    public static String GetFullPath(String pathStr) {
        Path path = Paths.get(pathStr);
        Path absolutePath = path.toAbsolutePath();
        return absolutePath.toString();
    }
}
