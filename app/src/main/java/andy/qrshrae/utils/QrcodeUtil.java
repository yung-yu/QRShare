package andy.qrshrae.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by andyli on 2017/2/25.
 */

import java.util.Hashtable;

public class QrcodeUtil {

	public static Bitmap getQrCodeBitmap (String content,int valueColor,int width, int height){
		QRCodeWriter writer = new QRCodeWriter();
		Bitmap bmp = null;
		try {
			Hashtable<EncodeHintType,Object> hst = new Hashtable<EncodeHintType,Object>();
			hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hst);
			width = bitMatrix.getWidth();
			height = bitMatrix.getHeight();
			bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					bmp.setPixel(x, y, bitMatrix.get(x, y) ? valueColor : Color.WHITE);
				}
			}


		} catch (WriterException e) {
			e.printStackTrace();
			bmp = null;
		}
		return  bmp;

	}

}
