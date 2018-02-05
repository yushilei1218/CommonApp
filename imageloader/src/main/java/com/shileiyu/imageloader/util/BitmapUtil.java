package com.shileiyu.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.shileiyu.imageloader.request.ImageRequest;

import java.io.File;
import java.io.InputStream;


/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public class BitmapUtil {
    public static Bitmap tranfer(File file, BitmapFactory.Options options) {
        if (Util.isValidFile(file)) {
            return BitmapFactory.decodeFile(file.getName(), options);
        }
        return null;
    }

    public static BitmapFactory.Options getOps(InputStream ins, ImageRequest request) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, opts);
        int reqWidth = request.getWidth() == -1 ? opts.outWidth : request.getWidth();
        int reqHeight = request.getHeight() == -1 ? opts.outHeight : request.getHeight();
        
        opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
        return opts;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 计算原始图像的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //判定，当原始图像的高和宽大于所需高度和宽度时
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            //算出长宽比后去比例小的作为inSamplesize，保障最后imageview的dimension比request的大
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            //计算原始图片总像素
            final float totalPixels = width * height;
            // Anything more than 2x the requested pixels we'll sample down further

            //所需总像素*2,长和宽的根号2倍
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            //如果遇到很长，或者是很宽的图片时，这个算法比较有用
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
}
