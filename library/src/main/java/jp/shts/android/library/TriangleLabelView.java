package jp.shts.android.library;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class TriangleLabelView extends View {

    private static final String TAG = TriangleLabelView.class.getSimpleName();

    private static class PaintHolder {
        String text = "";
        Paint paint;
        int color;
        float size;
        float height;
        float width;
        int style;

        void initPaint() {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(size);
            if (style == 1) {
                paint.setTypeface(Typeface.SANS_SERIF);
            } else if (style == 2) {
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }

        void resetStatus() {
            Rect rectText = new Rect();
            paint.getTextBounds(text, 0, text.length(), rectText);
            width = rectText.width();
            height = rectText.height();
        }
    }

    private PaintHolder primary = new PaintHolder();
    private PaintHolder secondary = new PaintHolder();

    private float topPadding;
    private float bottomPadding;
    private float centerPadding;

    private Paint trianglePaint;
    private int backGroundColor;

    private int width;
    private int height;

    private static final int DEGREES_LEFT = -45;
    private static final int DEGREES_RIGHT = 45;

    private Corner corner;

    public enum Corner {
        TOP_LEFT(1),
        TOP_RIGHT(2),
        BOTTOM_LEFT(3),
        BOTTOM_RIGHT(4),;
        private final int type;

        Corner(int type) {
            this.type = type;
        }

        private boolean top() {
            return this == TOP_LEFT || this == TOP_RIGHT;
        }

        private boolean left() {
            return this == TOP_LEFT || this == BOTTOM_LEFT;
        }

        private static Corner from(int type) {
            for (Corner c : values()) {
                if (c.type == type) return c;
            }
            return Corner.TOP_LEFT;
        }
    }

    public TriangleLabelView(Context context) {
        this(context, null);
    }

    public TriangleLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TriangleLabelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TriangleLabelView);

        this.topPadding = ta.getDimension(R.styleable.TriangleLabelView_labelTopPadding, dp2px(7));
        this.centerPadding = ta.getDimension(R.styleable.TriangleLabelView_labelCenterPadding, dp2px(3));
        this.bottomPadding = ta.getDimension(R.styleable.TriangleLabelView_labelBottomPadding, dp2px(3));

        this.backGroundColor = ta.getColor(R.styleable.TriangleLabelView_backgroundColor, Color.parseColor("#66000000"));
        this.primary.color = ta.getColor(R.styleable.TriangleLabelView_primaryTextColor, Color.WHITE);
        this.secondary.color = ta.getColor(R.styleable.TriangleLabelView_secondaryTextColor, Color.WHITE);

        this.primary.size = ta.getDimension(R.styleable.TriangleLabelView_primaryTextSize, sp2px(11));
        this.secondary.size = ta.getDimension(R.styleable.TriangleLabelView_secondaryTextSize, sp2px(8));

        final String primary = ta.getString(R.styleable.TriangleLabelView_primaryText);
        if (primary != null) {
            this.primary.text = primary;
        }
        final String secondary = ta.getString(R.styleable.TriangleLabelView_secondaryText);
        if (secondary != null) {
            this.secondary.text = secondary;
        }

        this.primary.style = ta.getInt(R.styleable.TriangleLabelView_primaryTextStyle, 2);
        this.secondary.style = ta.getInt(R.styleable.TriangleLabelView_secondaryTextStyle, 0);

        this.corner = Corner.from(ta.getInt(R.styleable.TriangleLabelView_corner, 1));

        ta.recycle();

        this.primary.initPaint();
        this.secondary.initPaint();

        trianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        trianglePaint.setColor(backGroundColor);

        this.primary.resetStatus();
        this.secondary.resetStatus();
    }

    public void setLabelTopPadding(float dp) {
        topPadding = dp2px(dp);
    }

    public float getLabelTopPadding() {
        return topPadding;
    }

    public void setLabelCenterPadding(float dp) {
        centerPadding = dp2px(dp);
    }

    public float getLabelCenterPadding() {
        return centerPadding;
    }

    public void setLabelBottomPadding(float dp) {
        bottomPadding = dp2px(dp);
    }

    public float getLabelBottomPadding() {
        return bottomPadding;
    }

    public void setPrimaryText(String text) {
        primary.text = text;
        primary.resetStatus();
    }

    public void setPrimaryText(@StringRes int textRes) {
        primary.text = getContext().getString(textRes);
        primary.resetStatus();
    }

    public String getPrimaryText() {
        return primary.text;
    }

    public void setSecondaryText(String smallText) {
        secondary.text = smallText;
        secondary.resetStatus();
    }

    public void setSecondaryText(@StringRes int textRes) {
        secondary.text = getContext().getString(textRes);
        secondary.resetStatus();
    }

    public String getSecondaryText() {
        return secondary.text;
    }

    public void setPrimaryTextColor(@ColorInt int color) {
        primary.color = color;
        primary.initPaint();
        primary.resetStatus();
    }

    public void setPrimaryTextColorResource(@ColorRes int colorResource) {
        primary.color = ContextCompat.getColor(getContext(), colorResource);
        primary.initPaint();
        primary.resetStatus();
    }

    public void setSecondaryTextColor(@ColorInt int color) {
        secondary.color = color;
        secondary.initPaint();
        secondary.resetStatus();
    }

    public void setSecondaryTextColorResource(@ColorRes int colorResource) {
        secondary.color = ContextCompat.getColor(getContext(), colorResource);
        secondary.initPaint();
        secondary.resetStatus();
    }

    public void setPrimaryTextSize(float sp) {
        primary.size = sp2px(sp);
    }

    public void setSecondaryTextSize(float sp) {
        secondary.size = sp2px(sp);
    }

    public float getPrimaryTextSize() {
        return primary.size;
    }

    public float getSecondaryTextSize() {
        return secondary.size;
    }

    public void setTriangleBackgroundColor(@ColorInt int color) {
        backGroundColor = color;
        trianglePaint.setColor(backGroundColor);
    }

    public void setTriangleBackgroundColorResource(@ColorRes int colorResource) {
        backGroundColor = ContextCompat.getColor(getContext(), colorResource);
        trianglePaint.setColor(backGroundColor);
    }

    public int getTriangleBackGroundColor() {
        return backGroundColor;
    }

    public void setCorner(Corner corner) {
        this.corner = corner;
    }

    public Corner getCorner() {
        return corner;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        // translate
        if (corner.top()) {
            canvas.translate(0, (float) ((height * Math.sqrt(2)) - height));
        }

        // rotate
        if (corner.top()) {
            if (corner.left()) {
                canvas.rotate(DEGREES_LEFT, 0, height);
            } else {
                canvas.rotate(DEGREES_RIGHT, width, height);
            }
        } else {
            if (corner.left()) {
                canvas.rotate(DEGREES_RIGHT, 0, 0);
            } else {
                canvas.rotate(DEGREES_LEFT, width, 0);
            }
        }

        // draw triangle
        @SuppressLint("DrawAllocation")
        Path path = new Path();
        if (corner.top()) {
            path.moveTo(0, height);
            path.lineTo(width / 2, 0);
            path.lineTo(width, height);
        } else {
            path.moveTo(0, 0);
            path.lineTo(width / 2, height);
            path.lineTo(width, 0);
        }
        path.close();
        canvas.drawPath(path, trianglePaint);

        // draw secondaryText
        if (corner.top()) {
            canvas.drawText(secondary.text, (width) / 2, topPadding + secondary.height, secondary.paint);
            canvas.drawText(primary.text, (width) / 2, (topPadding + secondary.height + centerPadding + primary.height), primary.paint);
        } else {
            canvas.drawText(secondary.text, (width) / 2, bottomPadding + secondary.height + centerPadding + primary.height, secondary.paint);
            canvas.drawText(primary.text, (width) / 2, (bottomPadding + primary.height), primary.paint);
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = (int) (topPadding + centerPadding + bottomPadding + secondary.height + primary.height);
        width = 2 * height;
        int realHeight = (int) (height * Math.sqrt(2));
        setMeasuredDimension(width, realHeight);
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float sp2px(float spValue) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

}