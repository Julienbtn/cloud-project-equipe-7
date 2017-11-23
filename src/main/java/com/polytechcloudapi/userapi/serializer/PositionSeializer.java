package com.polytechcloudapi.userapi.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.polytechcloudapi.userapi.model.Position;

import java.io.IOException;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 16-Nov-17.
 */
public class PositionSeializer extends JsonSerializer<double[]> {

    @Override
    public void serialize(double[] doubles, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        Position position = new Position(doubles[0], doubles[1]);
        jsonGenerator.writeObject(position);
    }
}
