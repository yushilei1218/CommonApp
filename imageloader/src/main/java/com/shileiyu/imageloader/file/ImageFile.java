package com.shileiyu.imageloader.file;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;

import com.shileiyu.imageloader.util.Namer;

import java.io.File;


/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public final class ImageFile implements IFile {
    private Context mContext;
    private static final String PATH = "img";

    public ImageFile(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public File getDir() {
        File dir;
        if (Environment.isExternalStorageEmulated()) {
            dir = new File(Environment.getExternalStorageDirectory(), PATH);
        } else {
            File cacheDir = mContext.getCacheDir();
            dir = new File(cacheDir, PATH);
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    @Override
    public File newFile(String key) {
        return new File(getDir(), Namer.key2name(key));
    }

    @Override
    public File getValidFile(String key) {
        File file = newFile(key);
        if (file.exists() && file.length() > 0) {
            return file;
        }
        return null;
    }
}
