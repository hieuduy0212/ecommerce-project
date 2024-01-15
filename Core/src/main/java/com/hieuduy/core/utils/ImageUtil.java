package com.hieuduy.core.utils;

import java.util.Base64;

public class ImageUtil {
    public String picByteToString(byte[] picByte){
        return Base64.getMimeEncoder().encodeToString(picByte);
    }
}
