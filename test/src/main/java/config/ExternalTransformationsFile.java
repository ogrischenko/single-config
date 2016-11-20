package config;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExternalTransformationsFile")
public class ExternalTransformationsFile {
    public String FilePath;
}