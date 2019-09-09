package com.segal.lqtauditproject;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.segal.lqtauditproject.CustomViews.HaImageView;
import com.segal.lqtauditproject.Data.LQTAuditDataSource;
import com.segal.lqtauditproject.Helpers.HttpHelpers;
import com.segal.lqtauditproject.Helpers.ImageHelpers;
import com.segal.lqtauditproject.Helpers.PermissionHelpers;
import com.segal.lqtauditproject.Helpers.StringHelpers;
import com.segal.lqtauditproject.Interfaces.SiteDetailsCallbacks;
import com.segal.lqtauditproject.Interfaces.SiteSection;
import com.segal.lqtauditproject.Models.Antenna;
import com.segal.lqtauditproject.Models.LoginInfo;
import com.segal.lqtauditproject.Models.Site;
import com.segal.lqtauditproject.ViewModels.AntennaViewModel;
import com.segal.lqtauditproject.ViewModels.ApiLoginViewModel;
import com.segal.lqtauditproject.ViewModels.PanoramaViewModel;
import com.segal.lqtauditproject.ViewModels.SiteInfoViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import okhttp3.Response;

public class SiteDetailsActivity extends AppCompatActivity implements SiteDetailsCallbacks {

    private static final String TAG = "SiteDetailsActivity";

    String mCurrentPhotoPath;
    private String currentPhotoName;
    private String currentSiteId;
    static final int REQUEST_TAKE_PHOTO = 1;


    HaImageView imageView;
    static final String imageViewTag = ",CurrentImageView";
    private Site site = new Site();
    //    private Site oldEntity = new Site();
    List<SiteSection> siteSections = new ArrayList<>();
    boolean isEditing = false;

    LQTAuditDataSource dataSource;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.setOffscreenPageLimit(2);


        PermissionHelpers.askForPermission(this, Manifest.permission.CAMERA, PermissionHelpers.CAMERA);
        dataSource = new LQTAuditDataSource();
        dataSource.open();
        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString("mCurrentPhotoPath");
            currentPhotoName = savedInstanceState.getString("currentPhotoName");
            currentSiteId = savedInstanceState.getString("currentSiteId");
            String savedSite = savedInstanceState.getString("site");
            if (savedSite != null)
                site = new Gson().fromJson(savedSite, Site.class);

        } else {
            String siteId = getIntent().getStringExtra("id");
            if (siteId != null && siteId.length() > 1) {
                site = new Site(dataSource.getSite(siteId));
                currentSiteId = site.getSiteId();

                site.setTemp(true);
                isEditing = true;
                mSectionsPagerAdapter.setEditingId(siteId);
            } else {
                Date now = Calendar.getInstance().getTime();
                site = new Site();
                site.setTemp(true);
                site.setAntennas(new RealmList<Antenna>());
                for (int s = 1; s < 5; s++) {
                    for (int a = 1; a < 5; a++) {
                        Antenna ant = new Antenna();
                        ant.setSectorIndex(s);
                        ant.setAntennaIndex(a);
                        site.getAntennas().add(ant);
                    }
                }
                site.setLocalCreatedAt(now);
            }
        }

//        if (site.getAntennas() == null) {
//            site.setAntennas(new RealmList<Antenna>());
//            for(int s=1;s<5;s++){
//                for(int a =1;a<5;a++){
//                    Antenna antenna = new Antenna();
//                    antenna.setSectorIndex(s);
//                    antenna.setAntennaIndex(a);
//                    site.getAntennas().add(antenna);
//
//                }
//            }
//        }
//        for (Antenna antenna : site.getAntennas()) {
//            if (StringHelpers.isNullOrEmpty(antenna.getOnSiteAntennaName()))
//                antenna.setOnSiteAntennaName("Other");
//            if (StringHelpers.isNullOrEmpty(antenna.getOnSiteETilt900()))
//                antenna.setOnSiteETilt900("RET");
//            if (StringHelpers.isNullOrEmpty(antenna.getOnSiteETilt1800()))
//                antenna.setOnSiteETilt1800("RET");
//            if (StringHelpers.isNullOrEmpty(antenna.getOnSiteETilt2100()))
//                antenna.setOnSiteETilt2100("RET");
//            if (StringHelpers.isNullOrEmpty(antenna.getOnSiteETilt2600()))
//                antenna.setOnSiteETilt2600("RET");
//            if (StringHelpers.isNullOrEmpty(antenna.getOnSiteETilt3500()))
//                antenna.setOnSiteETilt3500("RET");
//
//            if (StringHelpers.isNullOrEmpty(antenna.getRru1Band()))
//                antenna.setRru1Band("900");
//            if (StringHelpers.isNullOrEmpty(antenna.getRru2Band()))
//                antenna.setRru2Band("1800");
//            if (StringHelpers.isNullOrEmpty(antenna.getRru3Band()))
//                antenna.setRru3Band("2100");
//            if (StringHelpers.isNullOrEmpty(antenna.getRru4Band()))
//                antenna.setRru4Band("2600");
//        }
//        oldEntity = new Site(site);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        reqeustDatas();
        outState.putString("mCurrentPhotoPath", mCurrentPhotoPath);
        outState.putString("currentPhotoName", currentPhotoName);
        outState.putString("currentSiteId", currentSiteId);
        outState.putString("site", new Gson().toJson(site));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCurrentPhotoPath = savedInstanceState.getString("mCurrentPhotoPath");
        currentPhotoName = savedInstanceState.getString("currentPhotoName");
        currentSiteId = savedInstanceState.getString("currentSiteId");
        site = new Gson().fromJson(savedInstanceState.getString("site"), Site.class);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.segal.lqtauditproject.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File tempDir = new File(storageDir + "/Temp/");
        tempDir.mkdirs();
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                tempDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void savePic() {
        int targetW = 1000;
        int targetH = 1000;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rotation = 0;
        if (exif != null)
            rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        bitmap = rotateBitmap(bitmap, rotation);
//        bitmap = ImageHelpers.scaleBitmap(bitmap, 1500, 1500);
        bitmap = ImageHelpers.waterMarkInfo(bitmap, site);
        String imageFileName = "/" + currentSiteId + "/" + currentSiteId + "_" + currentPhotoName + ".jpg";
        File storageDir = getOutputMediaFile(imageFileName);


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(storageDir.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public File getOutputMediaFile(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        String dirName = "";
        if (fileName.contains("/")) {
            int index = fileName.lastIndexOf("/");
            dirName = fileName.substring(0, index);
            fileName = fileName.substring(index, fileName.length());
        }
        String rootDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();

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

    private void setPic() {
        if (imageView == null)
            return;
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        if (targetW == 0)
            targetW = 100;

        if (targetH == 0)
            targetH = 100;

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


    @Override
    public void onBackPressed() {
//        if (Site.equals(site, oldEntity))
//            super.onBackPressed();

        AlertDialog ad = new AlertDialog.Builder(this).setMessage(
                "Do you want to save changes?").setTitle(
                "Save changes?").setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveSite();
                                SiteDetailsActivity.super.onBackPressed();
                            }
                        })
                .setNegativeButton("Discard",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                SiteDetailsActivity.super.onBackPressed();
                            }
                        })
                .setNeutralButton("Continue",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }).show();
    }

    @Override
    public void setImageViewBitmap(final ImageView imageView, String bitmapPath) {
        if (bitmapPath == null)
            return;
        if (bitmapPath.startsWith("file:"))
            bitmapPath = bitmapPath.replace("file:", "");


//        Drawable d = new BitmapDrawable(getResources(), bitmap);
//        ((HaImageView) imageView).setThumbBitmap(ImageHelpers.waterMarkInfo(bitmap, site));
        final String finalCorrectedPath = bitmapPath;
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((HaImageView) imageView).setSourcePath(finalCorrectedPath);

                    }
                });
                reqeustDatas();
                postBackData();

            }
        }).start();
//        bitmap.recycle();


//        imageView.setImageBitmap(bitmap);
    }

    private void postBackData() {
        for (SiteSection siteSection : siteSections) {
            siteSection.requestData();
        }
    }

    @Override
    public void passSiteInfoViewModel(SiteInfoViewModel viewModel) {
//        if (isEditing)
//            dataSource.fillSiteInfoFrom(site, viewModel);
//        else

        site.fillSiteInfo(viewModel);
    }

    @Override
    public void passAntennaViewModel(AntennaViewModel viewModel, int sectorIndex, int antennaIndex) {
//        if (isEditing)
//            dataSource.addSiteAntennaFrom(site, viewModel, sectorIndex, antennaIndex);
//        else
        site.addAntenna(viewModel, sectorIndex, antennaIndex);
    }

    @Override
    public void passPanoramaViewModel(PanoramaViewModel viewModel) {
//        if (isEditing)
//            dataSource.fillSitePanoramaFrom(site, viewModel);
//        else
        site.fillPanorama(viewModel);
    }

    @Override
    public Site requestSite() {
        return site;
    }

    public void reqeustDatas() {
        for (SiteSection siteSection : siteSections) {
            siteSection.passData();
        }
    }

    private Handler handler;
    private ProgressDialog progress;

    @Override
    public void saveSite() {

        progress = new ProgressDialog(this);
        progress.setTitle("Saving");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
        site.setTemp(false);
        reqeustDatas();
        LoginInfo user = dataSource.getLastLoginInfo();
        site.setUserId(user.getUserId());
        dataSource.createSite(site);
        progress.dismiss();
        setResult(Activity.RESULT_OK);
        finish();

//        handler = new Handler()  {
//            public void handleMessage(android.os.Message msg) {
//                try {
//                }finally {
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                handler.sendEmptyMessage(0);
//            }
//        }).start();

    }

    private SiteUploadTask uploadTask = null;

    @Override
    public void uploadSite() {
        if (uploadTask != null)
            return;

        progress = new ProgressDialog(this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
        reqeustDatas();
        LoginInfo user = dataSource.getLastLoginInfo();
        site.setUserId(user.getUserId());
        dataSource.createSiteAsync(site);
        uploadTask = new SiteUploadTask(site, user.getToken());
        uploadTask.execute((Void) null);
//        handler = new Handler() {
//            public void handleMessage(android.os.Message msg) {
//                try {
//                    LoginInfo user = dataSource.getLastLoginInfo();
//                    site.setUserId(user.getUserId());
//                    dataSource.createSiteAsync(site);
//                    progress.dismiss();
//                } finally {
//                    setResult(Activity.RESULT_OK);
//                    finish();
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                site.setTemp(false);
//                reqeustDatas();
//
//                try {
//                    Response r = HttpHelpers.postSite(HttpHelpers.ROOTURL + "api/SitesApi/PostSite", site, dataSource.getLastLoginInfo().getToken());
////                    Log.d(TAG, "run: " + r.body().string());
////                    if (r.body().string() == "OK") {
//////                        Toast.makeText(SiteDetailsActivity.this, "Successfully Uploaded", Toast.LENGTH_LONG).show();
////
////                    } else {
//////                        Toast.makeText(SiteDetailsActivity.this, "Server Error!", Toast.LENGTH_LONG).show();
////                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                handler.sendEmptyMessage(0);
//            }
//        }).start();

    }

    @Override
    public void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            savePic();
            setPic();
            galleryAddPic();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_site_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void addSiteSection(SiteSection siteSection) {
        siteSections.add(siteSection);
    }

    @Override
    public void onCameraBtnClicked(ImageView targetImageView, String namePrefix, String tag, String imgNameAddon) {
        reqeustDatas();
        if (StringHelpers.isNullOrEmpty(site.getSiteId()) || StringHelpers.isNullOrEmpty(site.getOnSiteLatitude()) || StringHelpers.isNullOrEmpty(site.getOnSiteLongitude())) {
            Toast.makeText(this, "Fill SiteId,OnSiteLatitude and OnSiteLongitude fields to take pictures!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (site != null)
            currentSiteId = site.getSiteId();

        imageView = (HaImageView) targetImageView;
//        imageView.setTag(imageView.getTag() + imageViewTag);

//        String camBitmapName = "tmp_" + targetImageView.getTag().toString().replace("imgView,", "") + imgNameAddon;
        currentPhotoName = namePrefix + tag;
        dispatchTakePictureIntent();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Bundle bundle) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int tabNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView;

            rootView = inflater.inflate(R.layout.fragment_site_details, container, false);

            TextView textView = rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        String editingId;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setEditingId(String id) {
            editingId = id;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle b = new Bundle();
            if (editingId != null) {
                b.putString("id", editingId);
                b.putInt("position", position + 1);
            }
            switch (position) {
                case 0:
                    FragmentSiteInfo fragment = new FragmentSiteInfo();
                    fragment.setArguments(b);
                    return fragment;
                case 1:
                    return SectorsTabFragment.newInstance(b);
                case 2:
                    return FragmentPanorama.newInstance(b);
                default:
                    return PlaceholderFragment.newInstance(b);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public class PostSiteResult {
        public String result;
    }

    public class SiteUploadTask extends AsyncTask<Void, Void, Boolean> {

        private final Site mSite;
        private final String mToken;

        SiteUploadTask(Site site, String token) {
            mSite = site;
            mToken = token;
        }

        boolean success;
        String mResult = "";

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Response response = HttpHelpers.postSite(HttpHelpers.ROOTURL + "api/SitesApi/PostSite", mSite, mToken);
                if (response == null) {
                    success = false;
                    return false;
                }
                String res = response.body().string();
                PostSiteResult result = new Gson().fromJson(res, PostSiteResult.class);

                success = result.result.toUpperCase().trim() == "OK";
                mResult = result.result.toUpperCase().trim() == "OK" ? "Successfully Uploaded" : result.result;

                if (success) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                progress.dismiss();
                showToast("Successfully Uploaded");
                setResult(RESULT_OK);
                finish();
            } else {
                progress.dismiss();
                showToast(mResult);
            }
            uploadTask = null;
            progress.dismiss();

        }

        @Override
        protected void onCancelled() {
            uploadTask = null;
            progress.dismiss();
        }
    }
}
