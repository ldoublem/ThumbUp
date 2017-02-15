package com.ldoublem.thumbUplib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;

/**
 * Created by lumingmin on 16/8/3.
 */

public class ThumbUpView extends View {


    private Paint mPaint;

    private Paint mPaintbg;


    private Paint mPaintLike;
    private int mTagKey;
    private Paint mPaintBrokenLine;

    private RectF rectFBg;

    private float loveSize = 0.8f;

    private LikeType mUnLikeType = LikeType.broken;
    private int edgeColor = Color.BLACK;
    private int fillColor = Color.rgb(229, 115, 108);
    private int cracksColor = Color.WHITE;
    private int bgColor = Color.WHITE;

    private Bitmap mBitmapBrokenLeftLove = null;
    private Bitmap mBitmapBrokenRightLove = null;
    private OnThumbUp mOnThumbUp;

    private int mBrokenAngle = 13;
    private ValueAnimator valueAnimator;
    private float mAnimatedLikeValue = 0f;
    private float mAnimatedBrokenValue = 0f;
    float MaxSize = 1.2f;


    private WeakReference<View> mThumbUpView;

    public void setSearchView(View searchView) {
        this.mThumbUpView = new WeakReference<>(searchView);
    }

    public View getSearchView() {
        return mThumbUpView != null ? mThumbUpView.get() : null;
    }


    public void setUnLikeType(LikeType type) {
        this.mUnLikeType = type;
    }


    public void setEdgeColor(int color) {
        this.edgeColor = color;
    }

    public void setFillColor(int color) {
        this.fillColor = color;
    }

    public void setCracksColor(int color) {
        this.cracksColor = color;
    }

    public void setOnThumbUp(OnThumbUp onThumbUp) {
        this.mOnThumbUp = onThumbUp;
    }

    public void setBgColor(int color) {
        this.bgColor = color;
    }



    public ThumbUpView(Context context) {
        this(context, null);
    }

    public ThumbUpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThumbUpView);
        if (typedArray != null) {
            edgeColor = typedArray.getColor(R.styleable.ThumbUpView_edgeColor, edgeColor);
            fillColor = typedArray.getColor(R.styleable.ThumbUpView_fillColor, fillColor);
            cracksColor = typedArray.getColor(R.styleable.ThumbUpView_cracksColor, cracksColor);
            bgColor = typedArray.getColor(R.styleable.ThumbUpView_bgColor, bgColor);

            int type = typedArray.getInteger(R.styleable.ThumbUpView_unlikeType, 0);

            if (type == 0) {
                mUnLikeType = LikeType.broken;

            } else {
                mUnLikeType = LikeType.unlike;


            }

            typedArray.recycle();
        }
        initPaint();
    }


    private void initPaint() {

        mTagKey = getId();
        setSearchView(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);

        mPaintbg = new Paint();
        mPaintbg.setAntiAlias(true);
        mPaintbg.setStyle(Paint.Style.FILL);


        mPaintLike = new Paint();
        mPaintLike.setAntiAlias(true);


        mPaintLike.setStyle(Paint.Style.FILL);


        mPaintBrokenLine = new Paint();
        mPaintBrokenLine.setAntiAlias(true);
        mPaintBrokenLine.setStyle(Paint.Style.STROKE);


        rectFBg = new RectF();
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST
                && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(dip2px(30), dip2px(30));
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(heightSpecSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, widthSpecSize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    RectF rectFloveBg
            = new RectF();

    private void drawLove(Canvas canvas, Paint mPaint, float mAnimatedValue, boolean fill) {


        if (mAnimatedValue - 1 > (MaxSize - 1) / 2f) {
            mAnimatedValue = 1 + (MaxSize - mAnimatedValue);
        }
        mAnimatedValue = mAnimatedValue * loveSize;

        RectF rectFlove = new RectF();
        rectFlove.top = rectFBg.centerY() - (rectFBg.height() / 2f + mPaint.getStrokeWidth()) * mAnimatedValue;//* 0.5f;
        rectFlove.bottom = rectFBg.centerY() + (rectFBg.height() / 2f + mPaint.getStrokeWidth()) * mAnimatedValue;// * 0.5f;
        rectFlove.left = rectFBg.centerX() - (rectFBg.width() / 2f + mPaint.getStrokeWidth()) * mAnimatedValue;// * 0.5f;
        rectFlove.right = rectFBg.centerX() + (rectFBg.width() / 2f + mPaint.getStrokeWidth()) * mAnimatedValue;//* 0.5f;

        float realWidth = rectFlove.width();
        float realHeight = rectFlove.height();
        rectFlove.top = rectFlove.top + realHeight * (1 - 0.8f) / 2f;
        Path path = new Path();

        float startYScale = 0;
        if (fill) {
            startYScale = 0.17f;
        } else {
            startYScale = 0.185f;
        }

        path.moveTo((float) (0.5 * realWidth) + rectFlove.left, (float) (startYScale * realHeight) + rectFlove.top);
        path.cubicTo((float) (0.15 * realWidth) + rectFlove.left, (float) (-0.35 * realHeight + rectFlove.top),
                (float) (-0.4 * realWidth) + rectFlove.left, (float) (0.45 * realHeight) + rectFlove.top,
                (float) (0.5 * realWidth) + rectFlove.left, (float) (realHeight * 0.8 + rectFlove.top));
//        path.moveTo( (float) (0.5 * realWidth) + rectFlove.left, (float) (realHeight * 0.8 + rectFlove.top));
        path.cubicTo((float) (realWidth + 0.4 * realWidth) + rectFlove.left, (float) (0.45 * realHeight) + rectFlove.top,
                (float) (realWidth - 0.15 * realWidth) + rectFlove.left, (float) (-0.35 * realHeight) + rectFlove.top,
                (float) (0.5 * realWidth) + rectFlove.left, (float) (startYScale * realHeight) + rectFlove.top);
        path.close();


        canvas.drawPath(path, mPaintbg);

        canvas.drawPath(path, mPaint);
    }


    private void drawBrokenLove(Canvas canvasMain, float mAnimatedBrokenValue) {
        Canvas canvas = null;
        RectF rectFlove = new RectF();
        rectFlove.top = rectFBg.centerY() - (rectFBg.height() / 2f + mPaintLike.getStrokeWidth()) * loveSize;//* 0.5f;
        rectFlove.bottom = rectFBg.centerY() + (rectFBg.height() / 2f + mPaintLike.getStrokeWidth()) * loveSize;// * 0.5f;
        rectFlove.left = rectFBg.centerX() - (rectFBg.width() / 2f + mPaintLike.getStrokeWidth()) * loveSize;// * 0.5f;
        rectFlove.right = rectFBg.centerX() + (rectFBg.width() / 2f + mPaintLike.getStrokeWidth()) * loveSize;//* 0.5f;
        float realWidth = rectFlove.width();
        float realHeight = rectFlove.height();
        rectFlove.top = rectFlove.top + realHeight * (1 - 0.8f) / 2f;
        float fristX = (float) (0.5 * realWidth) + rectFlove.left;
        float fristY = (float) (0.17 * realHeight) + rectFlove.top;
        float lastX = (float) (0.5 * realWidth) + rectFlove.left;
        float lastY = (float) (realHeight * 0.8 + rectFlove.top);
        float secondX = lastX + realWidth / 14f;
        float secondY = fristY + (lastY - fristY) / 4f;
        float thirdX = lastX - realWidth / 12f;
        float thirdY = fristY + (lastY - fristY) / 2.5f;

//        if (mBitmapBrokenLeftLove == null) {

        mBitmapBrokenLeftLove = Bitmap.createBitmap(getMeasuredWidth(), (int) lastY, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmapBrokenLeftLove);
        canvas.rotate(-1 * mBrokenAngle * mAnimatedBrokenValue, lastX, lastY);

        Path path = new Path();
        path.moveTo((float) (0.5 * realWidth) + rectFlove.left, (float) (0.17 * realHeight) + rectFlove.top);
        path.cubicTo((float) (0.15 * realWidth) + rectFlove.left, (float) (-0.35 * realHeight + rectFlove.top),
                (float) (-0.4 * realWidth) + rectFlove.left, (float) (0.45 * realHeight) + rectFlove.top,
                (float) (0.5 * realWidth) + rectFlove.left, (float) (realHeight * 0.8 + rectFlove.top));
        path.lineTo(thirdX, thirdY);
        path.lineTo(secondX, secondY);
        path.close();
        canvas.drawPath(path, mPaintLike);
//        }

//        if (mBitmapBrokenRightLove == null) {
        mBitmapBrokenRightLove = Bitmap.createBitmap(getMeasuredWidth(), (int) lastY, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmapBrokenRightLove);
        canvas.rotate(mBrokenAngle * mAnimatedBrokenValue, lastX, lastY);

//            Path path = new Path();
        path.reset();

        path.moveTo((float) (0.5 * realWidth) + rectFlove.left, (float) (realHeight * 0.8 + rectFlove.top));
        path.cubicTo((float) (realWidth + 0.4 * realWidth) + rectFlove.left, (float) (0.45 * realHeight) + rectFlove.top,
                (float) (realWidth - 0.15 * realWidth) + rectFlove.left, (float) (-0.35 * realHeight) + rectFlove.top,
                (float) (0.5 * realWidth) + rectFlove.left, (float) (0.17 * realHeight) + rectFlove.top);
        path.lineTo(secondX, secondY);
        path.lineTo(thirdX, thirdY);

        path.close();
        canvas.drawPath(path, mPaintLike);
//        }


        canvasMain.drawBitmap(mBitmapBrokenLeftLove


                , 0, 0, mPaint);
        canvasMain.drawBitmap(
                mBitmapBrokenRightLove
                , 0, 0, mPaint);


    }


//    private Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree
//            , float x, float y) {
//        Matrix m = new Matrix();
//        m.setRotate(orientationDegree, x, y);
//
//        try {
//            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
//            return bm1;
//        } catch (OutOfMemoryError ex) {
//        }
//        return null;
//    }


    private void drawBrokenLine(Canvas canvas, float mAnimatedBrokenValue) {


        RectF rectFlove = new RectF();
        rectFlove.top = rectFBg.centerY() - (rectFBg.height() / 2f + mPaintLike.getStrokeWidth()) * loveSize;//* 0.5f;
        rectFlove.bottom = rectFBg.centerY() + (rectFBg.height() / 2f + mPaintLike.getStrokeWidth()) * loveSize;// * 0.5f;
        rectFlove.left = rectFBg.centerX() - (rectFBg.width() / 2f + mPaintLike.getStrokeWidth()) * loveSize;// * 0.5f;
        rectFlove.right = rectFBg.centerX() + (rectFBg.width() / 2f + mPaintLike.getStrokeWidth()) * loveSize;//* 0.5f;
        float realWidth = rectFlove.width();
        float realHeight = rectFlove.height();
        rectFlove.top = rectFlove.top + realHeight * (1 - 0.8f) / 2f;
        float fristX = (float) (0.5 * realWidth) + rectFlove.left;
        float fristY = (float) (0.17 * realHeight) + rectFlove.top;


        float lastX = (float) (0.5 * realWidth) + rectFlove.left;
        float lastY = (float) (realHeight * 0.8 + rectFlove.top);


        float secondX = lastX + realWidth / 14f;
        float secondY = fristY + (lastY - fristY) / 4f;


        float thirdX = lastX - realWidth / 12f;
        float thirdY = fristY + (lastY - fristY) / 2.5f;


        Path line = new Path();
        line.moveTo(fristX, fristY);

        if (mAnimatedBrokenValue > 0 && mAnimatedBrokenValue < 0.25f) {

            line.lineTo((secondX - fristX) * (mAnimatedBrokenValue / 0.25f) + fristX,
                    (secondY - fristY) * (mAnimatedBrokenValue / 0.25f) + fristY);
        }
        if (mAnimatedBrokenValue >= 0.25 && mAnimatedBrokenValue < 0.5f) {
            line.lineTo(secondX, secondY);
            line.lineTo((thirdX - secondX) * ((mAnimatedBrokenValue - 0.25f) / 0.25f) + secondX
                    , (thirdY - secondY) * ((mAnimatedBrokenValue - 0.25f) / 0.25f) + secondY);
        }
        if (mAnimatedBrokenValue >= 0.5 && mAnimatedBrokenValue <= 1f) {
            line.lineTo(secondX, secondY);
            line.lineTo(thirdX, thirdY);
            line.lineTo((lastX - thirdX) * ((mAnimatedBrokenValue - 0.5f) / 0.5f) + thirdX,
                    (lastY - thirdY) * ((mAnimatedBrokenValue - 0.5f) / 0.5f) + thirdY);
        }
        mPaintBrokenLine.setStrokeWidth(rectFlove.width() / 40);

        canvas.drawPath(line, mPaintBrokenLine);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPaint.setColor(edgeColor);
        mPaintLike.setColor(fillColor);
        mPaintBrokenLine.setColor(cracksColor);
        mPaintbg.setColor(bgColor);
        rectFBg = new RectF(0, 0,
                getMeasuredWidth(),
                getMeasuredHeight());

        rectFloveBg.top = rectFBg.centerY() - (rectFBg.height() / 2f) * loveSize;//* 0.5f;
        rectFloveBg.bottom = rectFBg.centerY() + (rectFBg.height() / 2f) * loveSize;// * 0.5f;
        rectFloveBg.left = rectFBg.centerX() - (rectFBg.width() / 2f) * loveSize;// * 0.5f;
        rectFloveBg.right = rectFBg.centerX() + (rectFBg.width() / 2f) * loveSize;//* 0.5f;


        mPaintLike.setStrokeWidth(rectFBg.width() / 20 + dip2px(1));
        mPaint.setStrokeWidth(rectFBg.width() / 40);


//        if (mAnimatedBrokenValue == 0||mAnimatedBrokenValue==1) {
        drawLove(canvas, mPaint, 1f, false);
//        }
        drawLove(canvas, mPaintLike, mAnimatedLikeValue, true);

        if (mAnimatedBrokenValue > 0 && mAnimatedBrokenValue < 0.5f) {
            float v = mAnimatedBrokenValue / 0.5f;
            drawBrokenLine(canvas, v);
        } else if (mAnimatedBrokenValue >= 0.5f && mAnimatedBrokenValue < 0.75f) {
            mAnimatedLikeValue = 0f;
            float v = (mAnimatedBrokenValue - 0.5f) / 0.25f;
            drawBrokenLove(canvas, mAnimatedBrokenValue);
        } else if (mAnimatedBrokenValue >= 0.75f && mAnimatedBrokenValue < 1f) {
            float v = (mAnimatedBrokenValue - 0.75f) / 0.25f;
            drawDrops(canvas, v);


        }


        canvas.restore();
    }


    private void drawDrops(Canvas canvas, float mAnimatedBrokenValue) {

        if (mAnimatedBrokenValue == 1)
            return;

        RectF rectFlove = new RectF();
        rectFlove.top = rectFBg.centerY() -
                (rectFBg.height() / 2f + mPaintLike.getStrokeWidth()) * loveSize;//* 0.5f;
        rectFlove.bottom = rectFBg.centerY() + (rectFBg.height() / 2f +
                mPaintLike.getStrokeWidth()) * loveSize;// * 0.5f;
        rectFlove.left = rectFBg.centerX() - (rectFBg.width() / 2f +
                mPaintLike.getStrokeWidth()) * loveSize;// * 0.5f;
        rectFlove.right = rectFBg.centerX() + (rectFBg.width() / 2f +
                mPaintLike.getStrokeWidth()) * loveSize;//* 0.5f;


        canvas.drawCircle(rectFlove.centerX() -
                        rectFlove.width() / 4,
                rectFlove.centerY() + rectFlove.height() / 10 +
                        rectFlove.height() / 3 * mAnimatedBrokenValue,
                rectFlove.width() / 15 +
                        rectFlove.width() / 18 * (1 - mAnimatedBrokenValue),
                mPaintLike

        );


        canvas.drawCircle(rectFlove.centerX() +
                        rectFlove.width() / 4,
                rectFlove.centerY() + rectFlove.height() / 10
                        + rectFlove.height() / 3 * mAnimatedBrokenValue,
                rectFlove.width() / 15 +
                        rectFlove.width() / 18 * (1 - mAnimatedBrokenValue),
                mPaintLike

        );


    }

    public void Like() {


        if (mAnimatedLikeValue == 0 || mAnimatedLikeValue == MaxSize) {
            post(new Runnable() {
                @Override
                public void run() {
                    startLikeAnim(LikeType.like);
                }
            });
        }

    }

    public void UnLike() {
        if (mAnimatedLikeValue == MaxSize
                ) {
            post(new Runnable() {
                @Override
                public void run() {
                    startLikeAnim(mUnLikeType);
                }
            });
        }
    }


    private void startLikeAnim(LikeType like) {

        if (like == LikeType.unlike) {
            startViewAnim(0f, 1f, 200, like);
//            setTag(false,"tag");

            getSearchView().setTag(mTagKey, false);

        } else if (like == LikeType.like) {
            getSearchView().setTag(mTagKey, true);
            startViewAnim(0f, 1f, 200, like);

        } else if (like == LikeType.broken) {
            getSearchView().setTag(mTagKey, false);
            startViewAnim(0f, 1f, 400, like);
        }
        if (mOnThumbUp != null)
            mOnThumbUp.like((Boolean) getSearchView().getTag(mTagKey));


    }


    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
            mAnimatedLikeValue = 0f;
            postInvalidate();
        }
    }


    private ValueAnimator startViewAnim(float startF, final float endF, long time, final LikeType like) {


        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {


                if (like == LikeType.unlike) {
                    mAnimatedLikeValue = (float) valueAnimator.getAnimatedValue();
                    mAnimatedLikeValue = 1 - mAnimatedLikeValue;
                } else if (like == LikeType.like) {
                    mAnimatedLikeValue = (float) valueAnimator.getAnimatedValue();
                    mAnimatedLikeValue = mAnimatedLikeValue + (MaxSize - 1f);
                } else if (like == LikeType.broken) {
                    mAnimatedBrokenValue = (float) valueAnimator.getAnimatedValue();
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();

        }

        return valueAnimator;
    }

    float startX = 0;
    float startY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            startX = event.getX();
            startY = event.getY();
            return true;
        } else if (MotionEvent.ACTION_UP == event.getAction()) {
            if (Math.abs(event.getX() - startX) < 5 &&
                    Math.abs(event.getY() - startY) < 5) {
                if (rectFloveBg.contains(event.getX(), event.getY())) {
                    if (getSearchView().getTag(mTagKey) == null || !(Boolean) getSearchView().getTag(mTagKey)) {
                        startLikeAnim(LikeType.like);

                    } else if ((Boolean) getSearchView().getTag(mTagKey)) {

                        if (mUnLikeType == LikeType.broken) {
                            startLikeAnim(LikeType.broken);
                        } else if (mUnLikeType == LikeType.unlike) {
                            startLikeAnim(LikeType.unlike);

                        }

                    }

                }
                return true;

            }


        }

        return false;
    }

    public enum LikeType {
        broken, unlike, like
    }


    public interface OnThumbUp {
        void like(boolean like);
    }


    public void setLike() {
        mAnimatedLikeValue = MaxSize;
        mAnimatedBrokenValue = 0f;
        getSearchView().setTag(mTagKey, true);
        invalidate();

    }

    public void setUnlike() {
        mAnimatedLikeValue = 0f;
        mAnimatedBrokenValue = 0f;
        getSearchView().setTag(mTagKey, false);

        invalidate();
    }


}
