package server;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.model.Table;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ConverterJSON {

    ///Funkcja zamienia List<Table> na String JSON
    public static String tableListToJSON(List<Table> list){

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(out, list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] data = out.toByteArray();
        return(new String(data));
    }
}
