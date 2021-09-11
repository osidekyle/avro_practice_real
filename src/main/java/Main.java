import org.apache.avro.*;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main {


    public void doThing(){
        Schema.Parser parser = new Schema.Parser();
        try {
            Schema schema = parser.parse(
                    getClass().getResourceAsStream("StringPair.avsc")
            );
            GenericRecord datum = new GenericData.Record(schema);
            datum.put("left","L");
            datum.put("right","R");
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            DatumWriter<GenericRecord> writer= new GenericDatumWriter<GenericRecord>(schema);
            Encoder encoder= EncoderFactory.get().binaryEncoder(out,null);
            writer.write(datum,encoder);
            encoder.flush();
            out.close();

            DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
            Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(),null);
            GenericRecord result = reader.read(null,decoder);

        }
        catch (IOException ex){
            System.out.println("darn");
        }

    }
}


