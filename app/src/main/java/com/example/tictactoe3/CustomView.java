package com.example.tictactoe3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CustomView extends View {

    Canvas canvas1;
    Paint paint;
    int type = 0;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(@NonNull AttributeSet set) {

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth((float) 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas1 = canvas;
        super.onDraw(canvas1);

        switch (type) {
            case 1:
                Row1();
                break;
            case 2:
                Row2();
                break;
            case 3:
                Row3();
                break;
            case 4:
                Col1();
                break;
            case 5:
                Col2();
                break;
            case 6:
                Col3();
                break;
            case 7:
                DiagonalLeft();
                break;
            case 8:
                DiagonalRight();
                break;
            case 9:
                Clear();
        }


    }

    public void change(int p) {
        type = p;
        invalidate();
    }

    public void Clear() {
        canvas1.drawColor(Color.WHITE);
    }

    public void Row1() {
        canvas1.drawLine(0, getHeight() / 6, getWidth(), getHeight() / 6, paint);

    }

    public void Row2() {
        canvas1.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);
    }

    public void Row3() {
        canvas1.drawLine(0, (float) (getHeight() * 0.85), getWidth(), (float) (getHeight() * 0.85), paint);
    }

    public void Col1() {
        canvas1.drawLine(getWidth() / 6, 0, getWidth() / 6, getHeight(), paint);
    }

    public void Col2() {
        canvas1.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
    }

    public void Col3() {
        canvas1.drawLine((float) (getWidth() * 0.85), 0, (float) (getWidth() * 0.85), getHeight(), paint);
    }

    public void DiagonalRight() {
        canvas1.drawLine(getWidth(), getHeight(), 0, 0, paint);
    }

    public void DiagonalLeft() {
        canvas1.drawLine(0, getHeight(), getWidth(), 0, paint);

    }

}
