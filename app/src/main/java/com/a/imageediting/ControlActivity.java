package com.a.imageediting;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.a.imageediting.utility.Helper;
import com.a.imageediting.utility.TransformImage;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;
public class ControlActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
    Toolbar mControlToolbar;


    ImageView mCenterImageView;

    TransformImage mtransformImage;
    int mCurrentFilter;
    Uri mSelectedImageUri;

    SeekBar mSeekBar;
    ImageView mTickImageView;
    ImageView cancelImageView;


    int mScreenWidth;
    int mScreenHeight;
    Target mApplySingleFilter = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFilterValue = mSeekBar.getProgress();
            if (mCurrentFilter == TransformImage.FILTER_BRIGHTNESS){
                mtransformImage.applyBrightnessSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS),mtransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS)));
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mScreenHeight/2).into(mCenterImageView);

            }
            else if (mCurrentFilter == TransformImage.FILTER_VIGNETTE){

                mtransformImage.applyVignetteSubFilter(currentFilterValue);


                Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE),mtransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE)));

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0,mScreenHeight/2).into(mCenterImageView);

            }else if (mCurrentFilter == TransformImage.FILTER_SATURATION){

                mtransformImage.applySaturationSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION),mtransformImage.getBitmap(TransformImage.FILTER_SATURATION));
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION)));

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0,mScreenHeight/2).into(mCenterImageView);


            }else if (mCurrentFilter == TransformImage.FILTER_CONTRAST){

                mtransformImage.applyContrastSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST),mtransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST)));

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0,mScreenHeight/2).into(mCenterImageView);


            }
            }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    Target mSmallTarget = new Target(){
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

             mtransformImage = new TransformImage(ControlActivity.this,bitmap);
            mtransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);

            Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS),mtransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
            Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).fit().centerInside().into(mFirstImageView);

            //
            mtransformImage.applyVignetteSubFilter(TransformImage.DEFAULT_VIGNETTE);

            Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE),mtransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));
            Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).fit().centerInside().into(mSecondImageView);


            //
            mtransformImage.applySaturationSubFilter(TransformImage.DEFAULT_SATURATION);

            Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION),mtransformImage.getBitmap(TransformImage.FILTER_SATURATION));
            Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION))).fit().centerInside().into(mThirdImageView);


            //
            mtransformImage.applyContrastSubFilter(TransformImage.DEFAULT_CONTRAST);

            Helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST),mtransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
            Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST))).fit().centerInside().into(mFourthImageView);

        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    final static int PICK_IMAGE = 2;
    final static int MY_PERMISION_REQUEST_STORAGE_PERMISSION = 3;


    ImageView mFirstImageView;
    ImageView mSecondImageView;
    ImageView mThirdImageView;
    ImageView mFourthImageView;

    private static final String TAG = ControlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mControlToolbar = (Toolbar) findViewById(R.id.toolbar);

        mCenterImageView = (ImageView) findViewById(R.id.centerImageView);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar3);

        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.drawable.icon);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        mFirstImageView = (ImageView) findViewById(R.id.imageView22);
        mSecondImageView = (ImageView) findViewById(R.id.imageView23);
        mThirdImageView = (ImageView) findViewById(R.id.imageView24);
        mFourthImageView = (ImageView) findViewById(R.id.imageView25);


        mTickImageView = (ImageView) findViewById(R.id.imageView21);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlActivity.this, ImagePreviewActivity.class);
                startActivity(intent);
            }
        });


        mCenterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestStoragePermission();
                if (ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


        mFirstImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setMax(TransformImage.MAX_BRIGHTNESS);

                mSeekBar.setProgress(TransformImage.DEFAULT_BRIGHTNESS);
             mCurrentFilter = TransformImage.FILTER_BRIGHTNESS;

               Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });

        mSecondImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setMax(TransformImage.MAX_VIGNETTE);

                mSeekBar.setProgress(TransformImage.DEFAULT_VIGNETTE);

                mCurrentFilter = TransformImage.FILTER_VIGNETTE;

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });

        mThirdImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setMax(TransformImage.MAX_SATURATION);

                mSeekBar.setProgress(TransformImage.DEFAULT_SATURATION);

                mCurrentFilter = TransformImage.FILTER_SATURATION;
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0,mScreenHeight/2).into(mCenterImageView);

            }
        });

        mFourthImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setMax(TransformImage.MAX_CONTRAST);

                mSeekBar.setProgress(TransformImage.DEFAULT_CONTRAST);

                mCurrentFilter = TransformImage.FILTER_CONTRAST;

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });

        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.get().load(mSelectedImageUri).into(mApplySingleFilter);
            }
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
      mScreenHeight =  displayMetrics.heightPixels;
       mScreenWidth = displayMetrics.widthPixels;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {
        switch (requestCode) {
            case MY_PERMISION_REQUEST_STORAGE_PERMISSION:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    new MaterialDialog.Builder(ControlActivity.this).title("Permission Required")
                            .content("thank you for providing storage permission")
                            .negativeText("No")
                            .positiveText("Yes")
                            .canceledOnTouchOutside(true).show();
                } else {

                    Log.d(TAG, "Permission denied!");
                }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

             mSelectedImageUri = data.getData();

            Picasso.get().load(mSelectedImageUri).fit().centerInside().into(mCenterImageView);

            Picasso.get().load(mSelectedImageUri).resize(0,mScreenHeight/2).into(mSmallTarget);

        }
    }

    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                new MaterialDialog.Builder(ControlActivity.this).title("Permission Required")
                        .content("You need to give storage access  to easily save filtered image")
                        .negativeText("No")
                        .positiveText("Yes")
                        .canceledOnTouchOutside(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        })
                        .show();
            } else {

                ActivityCompat.requestPermissions(ControlActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISION_REQUEST_STORAGE_PERMISSION);

            }
            return;

        }


    }
}
