package config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConfigFileTransformation")
public class ConfigFileTransformation
{
    public String Pattern;

    @XmlElementWrapper(name = "Patterns") @XmlElement(name = "string")
    public String[] Patterns;

    public String ReplaceTo;
}