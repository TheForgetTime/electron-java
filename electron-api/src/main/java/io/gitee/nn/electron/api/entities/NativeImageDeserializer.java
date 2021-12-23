package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.gitee.nn.electron.api.Electron;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class NativeImageDeserializer extends JsonDeserializer<NativeImage> {
    @Override
    public NativeImage deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        var dict = Electron.fromJsonString(p, HashMap.class, Float.class, String.class);
        var newDictionary = new HashMap<Float, BufferedImage>();
        for (var item : dict.entrySet()) {
            var bytes = Base64.getDecoder().decode(item.getValue());
            var bufIn = new ByteArrayInputStream(bytes);
            newDictionary.put(item.getKey(), ImageIO.read(bufIn));
        }

        return new NativeImage(newDictionary);
    }
}
