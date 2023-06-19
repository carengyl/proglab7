package serverUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import commonUtil.OutputUtil;
import commonUtil.StreamUtil;
import entities.Car;
import entities.CollectionManager;
import entities.Coordinates;
import entities.HumanBeing;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class XMLParser {
    private final XStream xStream = new XStream();

    private final StreamUtil streamUtil = new StreamUtil();

    private void initParser() {
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(CollectionManager.class);
        xStream.processAnnotations(HumanBeing.class);
        xStream.processAnnotations(Coordinates.class);
        xStream.processAnnotations(Car.class);
    }

    public CollectionManager readFromXML(Path filename) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filename.toFile()));
        initParser();

        String xmlText = streamUtil.streamToString(bufferedInputStream);
        bufferedInputStream.close();
        
        CollectionManager collection = null;

        try {
            collection = new CollectionManager(filename, (HashMap<Long, HumanBeing>) xStream.fromXML(xmlText));
            ClassValidator.validateClass(collection);
        } catch (XStreamException e) {
            OutputUtil.printErrorMessage("Collection is corrupted. Unable to load this XML file: " + filename);
        }
        return collection;
    }

    public void writeToXML(CollectionManager collection) throws IOException {
        initParser();
        String stringXMLCollection = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xStream.toXML(collection.getHumanBeings());
        FileWriter fileWriter = new FileWriter(new File(collection.getFileName().toUri()));
        fileWriter.write(stringXMLCollection);
        fileWriter.close();
    }
}