package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NativeImageSerializer extends JsonSerializer<NativeImage> {
    @Override
    public void serialize(NativeImage value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var scaledImages = value.GetAllScaledImages();
        gen.writeObject(scaledImages);
    }
}
