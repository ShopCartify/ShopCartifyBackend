package com.shopcartify.factory;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class QrCodeGeneratorFactory {

    public static byte[] generateByteQRCode(String text, int width, int height) {
        ByteArrayOutputStream outputStream = null;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            outputStream = new ByteArrayOutputStream();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static void generateImageQRCode(String text, int width, int height, String path) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(path));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
    public static String decodeQRCode(String imagePath) throws NotFoundException, IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));

        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

}
