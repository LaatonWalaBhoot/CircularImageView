package d.com.mycustomview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Aishwarya on 4/26/2018.
 */

@SuppressLint("AppCompatCustomView")
public class CircularImageView extends ImageView {

    private float mBorderWidth = 0;

    private int mBorderColor;
    private int mWidth;
    private int mHeight;
    private int mCanvasSize;

    private Paint mArcPaint;
    private Paint mPaint;
    private Bitmap mBitmap;

    //Constructor for view creation programmatically
    public CircularImageView(Context context) {
        super(context);
    }

    //Constructor for Inflating XML
    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Getting measurements according to SpecMode
        mWidth = getMeasuredDimension(widthMeasureSpec);
        mHeight = getMeasuredDimension(heightMeasureSpec);
        mCanvasSize = Math.min(mWidth, mHeight);

        /*Without this your layout will not be inflated
        Here the the width and height parameters are the same that you
        have given in your XML layout
         */
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paintCircularImageView();
        mBitmap = drawableToBitmap(getDrawable());

        //If no Source is defined return
        if (mBitmap == null) {
            return;
        }
        float mRadius = (Math.min(mHeight, mWidth) - (mBorderWidth * 2)) / 2;

        //Dawing Border
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius + mBorderWidth, mArcPaint);
        setShaderMatrix(mWidth, mHeight, mBitmap);
        //Drawing ImageView
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != ScaleType.CENTER_CROP) {
            throw new IllegalArgumentException(String.format("ScaleType %s is not supported. ScaleType.CENTER_CROP is used by default", scaleType));
        }
    }

    @Override
    public ScaleType getScaleType() {
        return ScaleType.CENTER_CROP;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {

            //Density
            float density = getResources().getDisplayMetrics().density;

            //Defaults
            mBorderColor = ContextCompat.getColor(context, R.color.colorPrimary);
            mBorderWidth = (int) (mBorderWidth * density);

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, 0, 0);

            //Setting property values
            setBorderColor(typedArray.getColor(R.styleable.CircularImageView_border_color, mBorderColor));
            setBorderWidth(typedArray.getDimension(R.styleable.CircularImageView_border_width, mBorderWidth));

            typedArray.recycle();
        }
    }

    public void setBorderWidth(float borderWidth) {
        mBorderWidth = borderWidth;
        requestLayout();
        invalidate();
    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
        requestLayout();
        invalidate();
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setShaderMatrix(int width, int height, Bitmap bitmap) {
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();

        /*
        This is the zoom level set in ratio of the canvas size
        to the size of the Bitmap image
         */
        matrix.setScale(((float) width / (float) bitmap.getWidth()), ((float) height / (float) bitmap.getHeight()));
        bitmapShader.setLocalMatrix(matrix);

        //Bitmap image is getting loaded into the paint object for drawing
        mPaint.setShader(bitmapShader);
    }

    private int getMeasuredDimension(int dimension) {
        int specMode = View.MeasureSpec.getMode(dimension);
        int specSize = View.MeasureSpec.getSize(dimension);

        //When you have set the dimension in dp
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        }
        //When you have set the dimension as MATCH_PARENT or WRAP_CONTENT
        else if (specMode == MeasureSpec.AT_MOST) {
            return specSize;
        } else {
            return mCanvasSize;
        }
    }

    private void paintCircularImageView() {
        //Setting border
        mArcPaint = new Paint();
        mArcPaint.setColor(mBorderColor);
        mArcPaint.setStrokeWidth(mBorderWidth);
        mArcPaint.setAntiAlias(true);

        //Setting ImageView
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
}
