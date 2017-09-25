package com.example.gurjeetsingh.testing.activities;

/**
 * Created by gurjeetsingh on 2017-08-10.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    private Rect rect;
    private Paint paint;

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int baseline = getBaseline();
        for (int i = 0; i < getLineCount(); i++) {
            canvas.drawText((Integer.toString(i+1)), rect.width(), baseline, paint);
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }

}