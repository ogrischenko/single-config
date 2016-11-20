package config;

import netutil.NetPath;

public class Logger {
    private String  basePath;

    public Logger(String basePath) {
        this.basePath = basePath;
    }

    public void Write(String text) {
        System.out.println(text);
//        File.AppendAllText(GetFilePath("log.txt"), string.Format("{0}{1}", text, Environment.NewLine));
    }

    private String GetFilePath(String filePath) {
        String logTxt = NetPath.Combine(basePath, filePath);
        return logTxt;
    }

    public void Error(String text) {
        System.out.println(text);
//        NetFile.AppendAllText(GetFilePath("error.txt"), string.Format("{0}{1}", text, Environment.NewLine));
    }

    public void Error(Exception e) {
        e.printStackTrace();
    }
}