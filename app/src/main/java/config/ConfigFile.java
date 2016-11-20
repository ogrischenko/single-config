package config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConfigFile")
public class ConfigFile
{
    public String FilePath;

    @XmlElementWrapper(name = "Transformations") @XmlElement(name = "Transformation")
    public ConfigFileTransformation[] Transformations;

    public String NewFilePath;

    public Boolean SkipTransform;

    public Boolean SkipWhenFileLocked;

    public String[] WriteCopyTo;

    public String Encoding;
}