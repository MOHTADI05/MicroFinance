package tn.esprit.mfb.util;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage convertByteArrayToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return ImageIO.read(bis);
    }
    }


//    public static byte[] compressImage(byte[] data) {
//        Deflater deflater = new Deflater();
//        deflater.setLevel(Deflater.BEST_COMPRESSION);
//        deflater.setInput(data);
//        deflater.finish();
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] tmp = new byte[4*1024];
//        while (!deflater.finished()) {
//            int size = deflater.deflate(tmp);
//            outputStream.write(tmp, 0, size);
//        }
//        try {
//            outputStream.close();
//        } catch (Exception ignored) {
//        }
//        return outputStream.toByteArray();
//    }
//
//
//
//    public static byte[] decompressImage(byte[] data) {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] tmp = new byte[4*1024];
//        try {
//            while (!inflater.finished()) {
//                int count = inflater.inflate(tmp);
//                outputStream.write(tmp, 0, count);
//            }
//            outputStream.close();
//        } catch (Exception ignored) {
//        }
//        return outputStream.toByteArray();
//    }


