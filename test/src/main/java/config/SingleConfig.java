package config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Oleg on 20.11.2016.
 */
@XmlRootElement(name = "SingleConfig")
public class SingleConfig {

    public String SrcRoot;

    @XmlElementWrapper(name = "ConfigFiles") @XmlElement(name = "ConfigFile")
    public ConfigFile[] ConfigFiles;

    @XmlElementWrapper(name = "IncludeSingleConfigs") @XmlElement(name = "IncludeSingleConfig")
    public IncludeSingleConfig[] IncludeSingleConfigs;

    @XmlElementWrapper(name = "GlobalConfigFileTransformations") @XmlElement(name = "GlobalConfigFileTransformation")
    public ConfigFileTransformation[] GlobalConfigFileTransformations;

    @XmlElementWrapper(name = "DevConfigFiles") @XmlElement(name = "DevConfigFile")
    public ConfigFile[] DevConfigFiles;

    @XmlElementWrapper(name = "ExternalTransformationsFiles") @XmlElement(name = "ExternalTransformationsFile")
    public ExternalTransformationsFile[] ExternalTransformationsFiles;

    public String ValidateRegExp;

    public Boolean SkipDevConfigFilesCheck;

    public SingleConfig() {

    }

    public SingleConfig(ConfigFile configFile) {
        ConfigFiles = new ConfigFile[]{configFile};
    }
}
