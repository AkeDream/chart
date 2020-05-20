package com.wifieye.akechartdemo.tex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;

/**
 * Create by ake on 2019/12/3
 * Describe:
 */
public class BitmapUtil {

    /**
     * 将图片资源转为BitmapDrawable
     * @param imageID
     * @return
     */
    private BitmapDrawable createBitmapDrawable(int imageID, Context context){
        InputStream is = null;
        try{
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            is = context.getResources().openRawResource(imageID);
            //decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，无需再使用java层的createBitmap，节省了java层的空间
            Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
            return bd;
        }finally{
            try {
                if(is != null){
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
