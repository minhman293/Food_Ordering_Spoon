package com.man293.food_ordering_spoon.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.InputStream;

public class FileUtils {

    public static String getPath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }

        return null;
    }
    public static File uriToFile(Context context ,Uri uri) {
        try {
            String filePath = FileUtils.getPath(context, uri);
            if (filePath != null) {
                return new File(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
