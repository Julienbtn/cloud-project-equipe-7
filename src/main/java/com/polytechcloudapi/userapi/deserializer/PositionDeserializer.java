package com.polytechcloudapi.userapi.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 16-Nov-17.
 */
public class PositionDeserializer extends JsonDeserializer<double[]> {
    @Override
    public double[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        double[] position = new double[2];
        position[0] = node.get("lat").asDouble();
        position[1] = node.get("lon").asDouble();
        return position;
    }
}
