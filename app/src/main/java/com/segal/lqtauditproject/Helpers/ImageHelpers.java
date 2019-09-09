package com.segal.lqtauditproject.Helpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.widget.ImageView;

import com.segal.lqtauditproject.LQTAuditProjectApplication;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.sql.StatementEvent;

/**
 * Created by Hossein on 10/28/2017.
 */

public abstract class ImageHelpers {

    private static final String TAG = "ImageHelpers";

    public static Bitmap waterMark(Bitmap src, String watermark, Point locationFromBottomLeft, int color, int alpha, float sizeInPercent, boolean underline, int backgroundColor, int backgroundAlpha) {
        //get source image width and height
        int w = src.getWidth();
        int h = src.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        //create canvas object
        Canvas canvas = new Canvas(result);
        //draw bitmap on canvas
        canvas.drawBitmap(src, 0, 0, null);
        //create paint object
        Paint paint = new Paint();
        //apply color
        paint.setColor(color);
        //set transparency
        paint.setAlpha(alpha);
        //set text size

        Paint bgPaint = new Paint();

        bgPaint.setColor(backgroundColor);
        bgPaint.setAlpha(backgroundAlpha);
        int size = (int) (sizeInPercent * result.getWidth() / 100);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        int padding = canvas.getWidth() / 100;
        float right = paint.measureText(watermark) + locationFromBottomLeft.x + 2;
        float height = paint.getFontMetrics().descent - paint.getFontMetrics().ascent + paint.getFontMetrics().leading;
//        float height = (size / 10) + 10;
        canvas.drawRect(locationFromBottomLeft.x - padding,
                canvas.getHeight() - locationFromBottomLeft.y - height + padding - 1,
                right,
                canvas.getHeight() - locationFromBottomLeft.y + padding,
                bgPaint);
        //set should be underlined or not
        paint.setUnderlineText(underline);
        //draw text on given location
        canvas.drawText(watermark, locationFromBottomLeft.x, canvas.getHeight() - locationFromBottomLeft.y, paint);
        src.recycle();
        return result;
    }

    public static Bitmap waterMarkInfo(Bitmap src, Site site) {
        return waterMarkInfo(src, site.getOnSiteLatitude(), site.getOnSiteLongitude());
    }

    public static Bitmap waterMarkInfo(Bitmap src, String lat, String lng) {
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).format(Calendar.getInstance().getTime());
        StringBuilder str = new StringBuilder();
        str.append(formattedDate);
        str.append("\n");
        str.append("| " + lat + " , " + lng);
        return waterMark(src,
                str.toString(),
                new Point(10, 10),
                ResourcesCompat.getColor(LQTAuditProjectApplication.getAppResources(), R.color.colorAccent, null),
                255, 3, false, Color.BLACK, 255);
    }

    public static Bitmap scaleBitmap(Bitmap bm, int maxWidth, int maxHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        if (width > height) {
            // landscape
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int) (height / ratio);
        } else if (height > width) {
            // portrait
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int) (width / ratio);
        } else {
            // square
            height = maxHeight;
            width = maxWidth;
        }

        return Bitmap.createScaledBitmap(bm, width, height, false);
    }

    public static Bitmap decodeSampledBitmapFromFile(String filePath,
                                                     int reqWidth, int reqHeight) {

        Log.d(TAG, "decodeSampledBitmapFromFile: " + filePath);
//        File f = getOutputMediaFile(filePath);
//        File f = new File(getImageFullPath(filePath));
        String fPath = getImageFullPath(filePath);
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fPath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

//
//    public static boolean saveBitmap(Bitmap bmp, String filename) {
//        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//        FileOutputStream out = null;
//        String path = extStorageDirectory + File.separator + filename + ".jpeg";
//        File f = new File(path);
//        f.mkdirs();
//        try {
//            f.createNewFile();
//
//            out = new FileOutputStream(path);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 80, out); // bmp is your Bitmap instance
//            // PNG is a lossless format, the compression factor (100) is ignored
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                if (out != null) {
//                    Log.d("Save Bitmap", path + " - Desc: " + out.getFD());
//                    bmp.recycle();
//                    out.flush();
//                    out.close();
//                    return true;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        return false;
//    }

    public static void storeImage(Bitmap image, String fileName, String title) {
        if (fileName == null || fileName.length() < 1)
            fileName = new SimpleDateFormat("LQTAudit_ddMMyyyyHHmmss", Locale.ENGLISH).format(Calendar.getInstance().getTime());

        if (title == null || title.length() < 1)
            title = "LQTAudit Photo";

        File pictureFile = getOutputMediaFile(fileName);

        if (pictureFile == null) {
            Log.e(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        Log.d(TAG, "storeImage: " + pictureFile.getPath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 80, fos);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "storeImage: FileNotFoundException: " + e.getMessage());
        } finally {
//            image.recycle();
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void storeImageFromFile(String srcPath, String destFileName, String title, String latitude, String longitude) {
//        File srcFile = getOutputMediaFile(srcPath);
        srcPath = srcPath;

//        File destFile = getOutputMediaFile(destFileName);

//        Log.d(TAG, "storeImageFromFile: dest: " + destFileName + ": " + destFile.lastModified() + "   - src: " + srcPath + ": " + srcFile.lastModified());
//        if (destFile.exists()) {
//            if (destFile.lastModified() == srcFile.lastModified())
//                return;
//        }

        storeImage(waterMarkInfo(decodeSampledBitmapFromFile(srcPath, 1000, 1000), latitude, longitude), destFileName, title);
    }

//    public static final String ROOTPATH = Environment.getExternalStorageDirectory()
//            + "/Android/data/"
//            + LQTAuditProjectApplication.getContext().getPackageName()
//            + "/Files/";

    public static final String ROOTPATH = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";

    public static File getOutputMediaFile(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        String dirName = "";
        if (fileName.contains("/")) {
            int index = fileName.lastIndexOf("/");
            dirName = fileName.substring(0, index);
            fileName = fileName.substring(index, fileName.length());
        }
        String rootDir = ROOTPATH;

        if (!dirName.toLowerCase().contains(rootDir.toLowerCase()))
            dirName = rootDir + dirName;

        File mediaStorageDir = new File(dirName);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName = fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ? fileName : fileName + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static String getImageFullPath(String fileName) {
        fileName = fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ? fileName : fileName + ".jpg";

        if (fileName.toLowerCase().contains(ROOTPATH.toLowerCase()))
            return fileName;

        return ROOTPATH + fileName;
    }

    public static Bitmap loadBitmapFromFile(String fileName) {
        File file = getOutputMediaFile(fileName);
        if (file == null || !file.exists()) {
            Log.d(TAG, "loadBitmapFromFile not found: " + file.getAbsolutePath());
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return bitmap;
    }

    public static Bitmap loadCameraBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    public static Bitmap loadBitmapFromFileForView(String fileName) {
        return decodeSampledBitmapFromFile(fileName, 100, 100);
    }

    public static  void setImageViewPic(ImageView imageView, String siteId, String prefix) {

        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        if (targetW == 0)
            targetW = 100;

        if (targetH == 0)
            targetH = 100;

        String tag = imageView.getTag().toString().replace("imgView,", "");
        String mCurrentPhotoPath = LQTAuditProjectApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + "/" + siteId + "/" + siteId + "_" + prefix + tag + ".jpg";

        File f = new File(mCurrentPhotoPath);
        if (!f.exists()) {
            return;
        }

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

//    private File savebitmap(String filename) {
//        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//        OutputStream outStream = null;
//
//        File file = new File(filename + ".jpeg");
//        if (file.exists()) {
//            file.delete();
//            file = new File(extStorageDirectory, filename + ".jpeg");
//            Log.e("file exist", "" + file + ",Bitmap= " + filename);
//        }
//        try {
//            // make a new bitmap from your file
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());
//
//            outStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
//            outStream.flush();
//            outStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.e("file", "" + file);
//        return file;
//
//    }

}
