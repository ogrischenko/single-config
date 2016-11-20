package config;

import netutil.NetFile;
import netutil.NetPath;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SingleConfigLoader {
    private String basePath;

    public SingleConfigLoader(String basePath) {
        this.basePath = basePath;
    }

    public SingleConfig LoadSingleConfig(String configFileName) {
        String configFullPath = NetPath.Combine(basePath, configFileName);

        if (!NetFile.Exists(configFullPath)) {
            throw new InvalidOperationException(String.format("File %s not found", configFullPath));
        }

        File file = new File(configFullPath);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(SingleConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SingleConfig conf = (SingleConfig) jaxbUnmarshaller.unmarshal(file);
            return conf;
        } catch (JAXBException e) {
            throw  new RuntimeException(e);
        }
    }
}
