package com.fox.factory.utils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
public class ImageUtil {

    public static String compressImage(byte[] data){

        return Base64.getEncoder().encodeToString(data);
    }


}
