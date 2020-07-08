package com.websarva.wings.android.pictures3;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {


    public static Bitmap decodeSampledBitmap(Context context, String filePath, int maxWidth, int maxHeight, Bitmap.Config config) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        AssetManager assetManager = context.getResources().getAssets();
        try (InputStream is = assetManager.open(filePath)) {
            BitmapFactory.decodeStream(is, null, options);

            final float wRatio_inv = (float) options.outWidth / maxWidth,
                    hRatio_inv = (float) options.outHeight / maxHeight;
            final int finalW, finalH, minRatio_inv ;

            if (wRatio_inv > hRatio_inv) {
                minRatio_inv = (int) wRatio_inv;
                finalW = maxWidth;
                finalH = Math.round(options.outHeight / wRatio_inv);
            } else {
                minRatio_inv = (int) hRatio_inv;
                finalH = maxHeight;
                finalW = Math.round(options.outWidth / hRatio_inv);
            }

            options.inSampleSize = pow2Ceil(minRatio_inv);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = config;

            InputStream is2 = assetManager.open(filePath);
            Bitmap map = BitmapFactory.decodeStream(is2, null, options);
            is2.close();

            return Bitmap.createScaledBitmap(map, finalW, finalH, true);
        } catch (IOException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }


    public static int pow2Ceil(int number) {
        return 1 << -(Integer.numberOfLeadingZeros(number) + 1);
    }
}

