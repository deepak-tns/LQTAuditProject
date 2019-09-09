package com.segal.lqtauditproject.Helpers;

import android.os.Environment;
import android.util.Log;

import com.segal.lqtauditproject.LQTAuditProjectApplication;
import com.segal.lqtauditproject.Models.Site;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Hossein on 11/18/2017.
 */

public class ZipHelpers {
    private static final int BUFFER = 2048;

    public void zip(String[] _files, String zipFileName) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _files.length; i++) {
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String zipSitePhotos(Site site){
        ZipHelpers zipHelpers = new ZipHelpers();
        String rootPath = LQTAuditProjectApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + site.getSiteId() + "/";
        Log.e("ZIPPING", "zipSitePhotos: " +  rootPath);
        File directory = new File(rootPath);
        if(!directory.exists())
            return null;

        File[] files = directory.listFiles();
        List<String> filteredFiles = new ArrayList<>();
        for (File file : files) {
            if((file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".png"))){
                filteredFiles.add(file.getAbsolutePath());
            }
        }
        String destPath = rootPath + "/" + site.getSiteId() + ".zip";
        zipHelpers.zip(filteredFiles.toArray(new String[filteredFiles.size()]), destPath);
        return destPath;
    }

//    public void unzip(String _zipFile, String _targetLocation) {
//
//        //create target location folder if not exist
//        dirChecker(_targetLocatioan);
//
//        try {
//            FileInputStream fin = new FileInputStream(_zipFile);
//            ZipInputStream zin = new ZipInputStream(fin);
//            ZipEntry ze = null;
//            while ((ze = zin.getNextEntry()) != null) {
//
//                //create dir if required while unzipping
//                if (ze.isDirectory()) {
//                    dirChecker(ze.getName());
//                } else {
//                    FileOutputStream fout = new FileOutputStream(_targetLocation + ze.getName());
//                    for (int c = zin.read(); c != -1; c = zin.read()) {
//                        fout.write(c);
//                    }
//
//                    zin.closeEntry();
//                    fout.close();
//                }
//
//            }
//            zin.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}
