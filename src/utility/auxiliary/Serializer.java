package utility.auxiliary;

import java.io.*;

public class Serializer {
    public static Serializable deserialize(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Serializable) ois.readObject();
    }
    public static byte[] serialize(Object object) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(object);
        return out.toByteArray();
    }
}
