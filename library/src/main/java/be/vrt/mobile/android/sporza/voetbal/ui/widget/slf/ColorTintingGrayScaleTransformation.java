package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by Tim Rijckaert on 5/05/2016.
 */
public class ColorTintingGrayScaleTransformation implements Transformation {

    private static final float MAX_LUMINOSITY = 255f;
    private final int color;

    public ColorTintingGrayScaleTransformation(final int color) {
        this.color = color;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        ColorMatrix grayScaleMatrix = new ColorMatrix();
        grayScaleMatrix.setSaturation(0f);

        ColorMatrix tintMatrix = new ColorMatrix();
        tintMatrix.setScale(Color.red(color) / MAX_LUMINOSITY,
                Color.green(color) / MAX_LUMINOSITY,
                Color.blue(color) / MAX_LUMINOSITY,
                Color.alpha(color) / MAX_LUMINOSITY);

        grayScaleMatrix.postConcat(tintMatrix);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(grayScaleMatrix);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setColorFilter(filter);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();

        return bitmap;
    }

    @Override
    public String key() {
        return "ColorizeTransformation";
    }
}