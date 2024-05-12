package tn.esprit.services;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayOutputStream;

public class QRCodeGeneratorService {

    public byte[] generateQRCode(String accommodationDetails) {
        ByteArrayOutputStream stream = QRCode.from(accommodationDetails).to(ImageType.PNG).stream();
        return stream.toByteArray();
    }
}
