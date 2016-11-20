package config;

import netutil.NetFile;
import netutil.NetPath;

public class SingleConfigLoader {
    private String basePath;

    public SingleConfigLoader(String basePath) {
        this.basePath = basePath;
    }

    public SingleConfig LoadSingleConfig(String configFileName) {
        String configFullPath = NetPath.Combine(basePath, configFileName);

        if (!NetFile.Exists(configFullPath)) {
            throw new InvalidOperationException(String.format("NetFile {0} not found", configFullPath));
        }

        var config = XmlSerializer.Deserialize < SingleConfig > (NetFile.ReadAllText(configFullPath));

        return config;
    }
}
