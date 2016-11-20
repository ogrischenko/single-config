
import config.ConfigFile;
import config.SingleConfig;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Oleg on 20.11.2016.
 */
public class Test {
    @org.junit.Test
    public void testUnmarshall() throws Exception {
        File file = new File("c:\\Work\\single-config\\app\\target\\classes\\local.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(SingleConfig.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        SingleConfig conf = (SingleConfig) jaxbUnmarshaller.unmarshal(file);
        System.out.println(ReflectionToStringBuilder.toString(conf, new MultilineRecursiveToStringStyle()));
    }

    @org.junit.Test
    public void testMarshall() throws Exception {
        ConfigFile configFile = new ConfigFile();
        configFile.FilePath = "test";
        SingleConfig config = new SingleConfig(configFile);

        JAXBContext jaxbContext = JAXBContext.newInstance(SingleConfig.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//        jaxbMarshaller.marshal(config, file);
        jaxbMarshaller.marshal(config, System.out);

    }
}
