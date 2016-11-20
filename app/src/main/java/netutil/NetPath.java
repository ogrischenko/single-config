package netutil;

import java.io.File;

/**
 * Created by Oleg on 20.11.2016.
 */
public class NetPath {
    public static String Combine(String first, String second) {
        File file1 = new File(first);
        File file2 = new File(file1, second);
        return file2.getPath();
    }
}
