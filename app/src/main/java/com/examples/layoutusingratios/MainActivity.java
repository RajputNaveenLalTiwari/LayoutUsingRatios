package com.examples.layoutusingratios;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
{
    private Context context;
    private int screenWidth,screenHeight;

    private ImageView  image;
    private ViewGroup.LayoutParams imageParams;
    private int imageWidth,imageHeight;
    private Bitmap imageBitmap;

    private LinearLayout linearLayout;
    private ViewGroup.LayoutParams linearLayoutParams;
    private int linearLayoutWidth,linearLayoutHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;

        // imageview is of 3:2 ratio
        image = (ImageView) findViewById(R.id.imageID);
        imageParams = (ViewGroup.LayoutParams) image.getLayoutParams();

        imageWidth = screenWidth;
        imageHeight = ((imageWidth * 2)/3);

        imageParams.width = imageWidth;
        imageParams.height = imageHeight;

        imageBitmap = optimizedBitmap(context,R.drawable.navigation,imageWidth,imageHeight);
        image.setImageBitmap(imageBitmap);

        // linearlayout is of 1:1 ratio
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutID);
        linearLayoutParams = (ViewGroup.LayoutParams) linearLayout.getLayoutParams();

        linearLayoutWidth = screenWidth;
        linearLayoutHeight = ((linearLayoutWidth * 1)/1);

        linearLayoutParams.width = linearLayoutWidth;
        linearLayoutParams.height = linearLayoutHeight;
    }

    public static int pixelsToDip( int pixels, Context context )
    {
        int dp = 0;

        final float densityScale = context.getResources().getDisplayMetrics().density;
        dp = (int) (pixels * densityScale + 0.5f);
        return dp;
    }

    public static Bitmap optimizedBitmap(Context context, int resourceId, int requiredWidth, int requiredHeight )
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resourceId, options);
    }

    public static int calculateInSampleSize( BitmapFactory.Options options, int requiredWidth, int requiredHeight )
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > requiredHeight || width > requiredWidth)
        {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > requiredHeight && (halfWidth / inSampleSize) > requiredWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
