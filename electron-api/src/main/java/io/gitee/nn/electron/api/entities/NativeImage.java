package io.gitee.nn.electron.api.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gitee.nn.electron.api.util.PathUtil;
import io.gitee.nn.electron.api.util.ResourceFileUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@JsonDeserialize(using = NativeImageDeserializer.class)
@JsonSerialize(using = NativeImageSerializer.class)
public class NativeImage {
    private static final Map<String, Float> ScaleFactorPairs = new HashMap<>();

    static {
        ScaleFactorPairs.put("@1x", 1.0f);
        ScaleFactorPairs.put("@2x", 2.0f);
        ScaleFactorPairs.put("@3x", 3.0f);
        ScaleFactorPairs.put("@4x", 4.0f);
        ScaleFactorPairs.put("@5x", 5.0f);
        ScaleFactorPairs.put("@1.25x", 1.25f);
        ScaleFactorPairs.put("@1.33x", 1.33f);
        ScaleFactorPairs.put("@1.4x", 1.4f);
        ScaleFactorPairs.put("@1.5x", 1.5f);
        ScaleFactorPairs.put("@1.8x", 1.8f);
        ScaleFactorPairs.put("@2.5x", 2.5f);
    }

    private Map<Float, BufferedImage> images = new HashMap<>();
    private boolean isTemplateImage;

    public NativeImage() {
    }

    public NativeImage(BufferedImage bitmap) {
        this(bitmap, 1.0f);
    }

    public NativeImage(BufferedImage bitmap, float scaleFactor) {
        images.put(scaleFactor, bitmap);
    }

    public NativeImage(Map<Float, BufferedImage> imageDictionary) {
        images = imageDictionary;
    }

    private static Float ExtractDpiFromFilePath(String filePath) {
        var withoutExtension = PathUtil.GetFileNameWithoutExtension(filePath);
        return ScaleFactorPairs.entrySet().stream()
                .filter(p -> withoutExtension.endsWith(p.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
    }

    private static BufferedImage BytesToImage(byte[] bytes) throws IOException {
        var bufIn = new ByteArrayInputStream(bytes);
        return ImageIO.read(bufIn);
    }

    public static NativeImage CreateEmpty() {
        return new NativeImage();
    }

    public static NativeImage CreateFromBufferedImage(BufferedImage image) {
        return CreateFromBufferedImage(image, null);
    }

    public static NativeImage CreateFromBufferedImage(BufferedImage image, CreateFromBitmapOptions options) {
        if (options == null) {
            options = new CreateFromBitmapOptions();
        }

        return new NativeImage(image, options.getScaleFactor());
    }

    public static NativeImage CreateFromBuffer(byte[] buffer) throws IOException {
        return CreateFromBuffer(buffer, null);
    }

    public static NativeImage CreateFromBuffer(byte[] buffer, CreateFromBufferOptions options) throws IOException {
        if (options == null) {
            options = new CreateFromBufferOptions();
        }

        var image = BytesToImage(buffer);

        return new NativeImage(image, options.getHeight());
    }

    public static NativeImage CreateFromDataURL(String dataUrl) throws IOException {
        var images = new HashMap<Float, BufferedImage>();
        var pattern = Pattern.compile("data:image/(?<type>.+?),(?<data>.+)");
        var parsedDataUrl = pattern.matcher(dataUrl);
        parsedDataUrl.matches();
        var actualData = parsedDataUrl.group("data");
        var binData = Base64.getDecoder().decode(actualData);

        var image = BytesToImage(binData);

        images.put(1.0f, image);

        return new NativeImage(images);
    }

    public static NativeImage CreateFromPath(String path) throws Exception {
        var images = new HashMap<Float, BufferedImage>();
        var pattern = Pattern.compile("(@.+?x)");
        if (pattern.matcher(path).matches()) {
            var dpi = ExtractDpiFromFilePath(path);
            if (dpi == null) {
                throw new Exception("Invalid scaling factor for '" + path + "'.");
            }

            images.put(dpi, ImageIO.read(new File(path)));
        } else {
            var fileNameWithoutExtension = PathUtil.GetFileNameWithoutExtension(path);
            var extension = PathUtil.GetExtension(path);
            // Load as 1x dpi
            var file1x = new File(ResourceFileUtil.UnzipFromJar(path));
            if (file1x.exists()) {
                images.put(1.0f, ImageIO.read(file1x));
            }

            for (var scale : ScaleFactorPairs.entrySet()) {
                var fileName = "" + fileNameWithoutExtension + "" + scale.getKey() + "." + extension + "";
                var file = new File(ResourceFileUtil.UnzipFromJar(fileName));
                if (file.exists()) {
                    var dpi = ExtractDpiFromFilePath(fileName);
                    if (dpi != null) {
                        images.put(dpi, ImageIO.read(file));
                    }
                }
            }
        }

        return new NativeImage(images);
    }

    Map<Float, BufferedImage> getImages() {
        return images;
    }

    void setImages(Map<Float, BufferedImage> images) {
        this.images = images;
    }

    public NativeImage Crop(Rectangle rect) {
        var images = new HashMap<Float, BufferedImage>();
        for (var image : this.images.entrySet()) {
            images.put(image.getKey(), Crop(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), image.getKey()));
        }

        return new NativeImage(images);
    }

    public NativeImage Resize(ResizeOptions options) {
        var images = new HashMap<Float, BufferedImage>();
        for (var image : this.images.entrySet()) {
            images.put(image.getKey(), Resize(options.getWidth(), options.getHeight(), image.getKey()));
        }

        return new NativeImage(images);
    }

    public void AddRepresentation(AddRepresentationOptions options) throws IOException {
        if (options.getBuffer().length > 0) {
            images.put(options.getScaleFactor(),
                    CreateFromBuffer(options.getBuffer(), CreateFromBufferOptions.builder().scaleFactor(options.getScaleFactor()).build())
                            .GetScale(options.getScaleFactor()));
        } else if (options.getDataUrl() != null && !options.getDataUrl().isEmpty()) {
            images.put(options.getScaleFactor(), CreateFromDataURL(options.getDataUrl()).GetScale(options.getScaleFactor()));
        }
    }

    public Float GetAspectRatio() {
        return GetAspectRatio(1.0f);
    }

    public float GetAspectRatio(float scaleFactor) {
        var image = GetScale(scaleFactor);
        if (image != null) {
            return (float) (image.getWidth() / image.getHeight());
        }

        return 0f;
    }

    public Size GetSize() {
        return GetSize(1.0f);
    }

    public Size GetSize(float scaleFactor) {
        if (images.containsKey(scaleFactor)) {
            var image = images.get(scaleFactor);
            return Size.builder().width(image.getWidth()).height(image.getHeight()).build();
        }

        return null;
    }

    public boolean IsEmpty() {
        return images.size() <= 0;
    }

    public String ToDataURL(ToDataUrlOptions options) throws IOException {
        if (!this.images.containsKey(options.getScaleFactor())) {
            return null;
        }

        var bytes = ImageToBytes("png", options.getScaleFactor());
        var base64 = Base64.getEncoder().encodeToString(bytes);

        return "data:image/png;base64," + base64 + "";
    }

    public byte[] ToJPEG() throws IOException {
        return ImageToBytes("jpg", 1.0f);
    }

    public byte[] ToPNG(ToPNGOptions options) throws IOException {
        return ImageToBytes("png", options.getScaleFactor());
    }

    private byte[] ImageToBytes(String imageFormat, Float scaleFactor) throws IOException {
        if (imageFormat == null) {
            imageFormat = "png";
        }

        if (scaleFactor == null) {
            scaleFactor = 1.0f;
        }

        var ms = new ByteArrayOutputStream();

        if (this.images.containsKey(scaleFactor)) {
            var image = this.images.get(scaleFactor);
            ImageIO.write(image, imageFormat, ms);
            return ms.toByteArray();
        }

        return null;
    }

    private BufferedImage Resize(Integer width, Integer height) {
        return Resize(width, height, 1.0f);
    }

    private BufferedImage Resize(Integer width, Integer height, float scaleFactor) {
        if (!this.images.containsKey(scaleFactor) || (width == null && height == null)) {
            return null;
        }

        var image = this.images.get(scaleFactor);
        var aspect = GetAspectRatio(scaleFactor);

        if (width == null) {
            width = (int) (image.getWidth() * aspect);
        }
        if (height == null) {
            height = (int) (image.getHeight() * aspect);
        }

        width = (int) (width * scaleFactor);
        height = (int) (height * scaleFactor);

        var bmp = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        var g = bmp.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(image,
                0, 0, bmp.getWidth(), bmp.getHeight(),
                0, 0, image.getWidth(), image.getHeight(), null);

        return bmp;
    }

    private BufferedImage Crop(Integer x, Integer y, Integer width, Integer height) {
        return Crop(x, y, width, height, 1.0f);
    }

    private BufferedImage Crop(Integer x, Integer y, Integer width, Integer height, float scaleFactor) {
        if (!this.images.containsKey(scaleFactor)) {
            return null;
        }

        var image = this.images.get(scaleFactor);

        if (x == null) {
            x = 0;
        }
        if (y == null) {
            y = 0;
        }

        x = (int) (x * scaleFactor);
        y = (int) (y * scaleFactor);

        if (width == null) {
            width = image.getWidth();
        }
        if (height == null) {
            height = image.getHeight();
        }

        width = (int) (width * scaleFactor);
        height = (int) (height * scaleFactor);

        var bmp = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        var g = bmp.createGraphics();
        g.drawImage(image,
                x, y, width, height,
                0, 0, image.getWidth(), image.getHeight(),
                null);
        return bmp;
    }

    Map<Float, String> GetAllScaledImages() {
        var dict = new HashMap<Float, String>();
        try {
            for (var entry : this.images.entrySet()) {
                dict.put(entry.getKey(), Base64.getEncoder().encodeToString(ImageToBytes(null, entry.getKey())));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dict;
    }


    BufferedImage GetScale(float scaleFactor) {
        if (this.images.containsKey(scaleFactor)) {
            return images.get(scaleFactor);
        }

        return null;
    }

}
