package com.shileiyu.imageloader.file;

import java.io.File;

/**
 * @author shilei.yu
 * @since on 2018/2/1.
 */

public interface IFile {
    File getDir();

    File newFile(String key);

    File getValidFile(String key);
}
