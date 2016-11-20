package config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IncludeSingleConfig")
public class IncludeSingleConfig {
    public String FilePath;

    @XmlElementWrapper(name = "GlobalConfigFileTransformations") @XmlElement(name = "ConfigFileTransformation")
    public ConfigFileTransformation[] GlobalConfigFileTransformations;

    @XmlElementWrapper(name = "ExternalTransformationsFiles") @XmlElement(name = "ExternalTransformationsFile")
    public ExternalTransformationsFile[] ExternalTransformationsFiles;
}