package com.tylx.leasephone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tylx.leasephone.R;


public class RoundImageView extends ImageView {
    Bitmap bitmap;
    Context context;
    int bgpadding = 2; //
    int round = 10;// 当话圆角的时候 会用到
    boolean isarc = true;// 是圆形

    public static enum RoundImageMode {
        ROUND, ARC
    }

    /***
     * public static final int ROUND = 1; public static final int ARC = 2;
     *
     * @param mode
     */
    public void setDrawMode(RoundImageMode mode) {
        if (mode == RoundImageMode.ROUND) {
            isarc = false;
        } else {
            isarc = true;
        }
        postInvalidate();
    }
//    @Override
//    public void setImageDrawable(Drawable drawable) {
//        // TODO Auto-generated method stub
//        if (drawable == null)
//            return;
//        super.setImageDrawable(drawable);
//        if (drawable != null) {
//            bitmap = ((BitmapDrawable) drawable).getBitmap();
//        }
//    }

//    @Override
//    public void setBackground(Drawable drawable) {
//        if (drawable == null)
//            return;
//        super.setBackground(drawable);
//        if (drawable != null) {
//            bitmap = ((BitmapDrawable) drawable).getBitmap();
//        }
//    }

//    @Override
//    public void setImageBitmap(Bitmap bm) {
//        // TODO Auto-generated method stub
//        if (bm == null)
//            return;
//        super.setImageBitmap(bm);
//        this.bitmap = bm;
//    }

    void RestSize() {
        Matrix m = new Matrix();
        float wbi;
        float hbi;

        wbi = (getWidth() - bgpadding) / (float) bitmap.getWidth();
        hbi = (getWidth() - bgpadding) / (float) bitmap.getHeight();
        m.setScale(wbi, hbi);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), m, false);
    }

    /**
     * 不会变形的
     */
    void RestSizeNew() {
        Matrix m = new Matrix();
        float wbi;
        float hbi;

        wbi = (getMeasuredWidth() - bgpadding) / (float) bitmap.getWidth();
        hbi = (getMeasuredHeight() - bgpadding) / (float) bitmap.getHeight();
        if (bitmap.getWidth() <= bitmap.getHeight())
            m.setScale(wbi, wbi);// 小于的 需要  把小的 扩大
        else
            m.setScale(hbi, hbi);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), m, false);
//        System.out.println(bitmap.getWidth() + "  " +
//                bitmap.getHeight() + "  " + wbi + "   " + hbi);
        if (bitmap.getWidth() != bitmap.getHeight()) {
            //如果不是正方形   ，上面已经 约束到最小了 必须裁剪 超出的
            if(bitmap.getWidth() >= bitmap.getHeight()){
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, (getWidth() - bgpadding) - 1, (getWidth() - bgpadding) - 1);
            }else{
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, (getHeight() - bgpadding) - 1, (getHeight() - bgpadding) - 1);
            }
        }

    }


    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (getDrawable() != null)
            this.bitmap = drawableToBitmap(getDrawable());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        framecolor = a.getColor(R.styleable.RoundImageView_framecolor, 0xe7e5e5);
        bgpadding = (int) a.getDimension(R.styleable.RoundImageView_framewidth, 2);
        isarc = a.getInteger(R.styleable.RoundImageView_rstyle, 0) == 0;
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);

    }

    public int getFramecolor() {
        return framecolor;
    }

    /***
     *
     * @param type  1男 2女 3好友
     */
    public void setFramecolor(int type) {
        switch (type) {
            case 3:
                this.framecolor = 0xFFE8CF10;
                break;
            case 1:
                this.framecolor = 0xFF172338;
                break;
            case 2:
                this.framecolor = 0xFFFB5779;
                break;
        }

    }

    int framecolor = 0xe7e5e5;

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        Drawable drawable = getDrawable() == null ? getBackground() : getDrawable();
        if (drawable != null) {
            bitmap = drawableToBitmap(drawable);
            if (bitmap == null)
                return;
            RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawColor(0x00000000);
            paint.setColor(framecolor);
            if (isarc) {
                canvas.drawArc(rectF, 0, 360, false, paint);
            } else {
                canvas.drawRoundRect(rectF, round, round, paint);
            }
            canvas.drawBitmap(getCircleBitmap(), 0, 0, new Paint());
        }

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);
        try {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = null;
        }

        return bitmap;
    }
//    public void setImageResource(int res) {
//        // bitmap = BitmapFactory.decodeResource(context.getResources(), res);
//        // // if (bitmap.getWidth() >= width) {
//        // Matrix m = new Matrix();
//        // float bi = (getWidth() - bgpadding - 10) / (float) bitmap.getWidth();
//        // m.setScale(bi, bi);
//        // bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//        // bitmap.getHeight(), m, false);
//        // }
//        Drawable drawable = ContextCompat.getDrawable(context, res);
//        if (drawable != null) {
//            bitmap = ((BitmapDrawable) drawable).getBitmap();
//        }
//        postInvalidate();
//    }

//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // height = View.MeasureSpec.getSize(heightMeasureSpec);
//        // width = View.MeasureSpec.getSize(widthMeasureSpec);
//        // setMeasuredDimension(width, height); //
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    Bitmap getCircleBitmap() {
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        RectF rect = new RectF(bgpadding, bgpadding, getMeasuredWidth() - bgpadding,
                getMeasuredHeight() - bgpadding);
        if (isarc) {
            canvas.drawArc(rect, 0, 360, false, paint);
        } else {
            canvas.drawRoundRect(rect, round, round, paint);
        }
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        RestSizeNew();
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return outBitmap;
    }

    public float dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (dpValue * density + 0.5f);
    }
}
