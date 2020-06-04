package com.example.traceit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LineDraw extends View {

    private Paint paint;
    private List<PointF> points;

    private ArrayList<Integer>[] lines;


    public LineDraw(Context context) {
        super(context);
    }

    public LineDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setPathEffect(new DashPathEffect(new float[] {4,2,4,2}, 0));
        //paint.setStrokeWidth(15);

        int i=0;
        for(List<Integer> line: lines){
            for(int p: line){
                canvas.drawLine(points.get(i).x, points.get(i).y, points.get(p).x, points.get(p).y, paint);
            }
            i++;
        }


/*
        canvas.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y, paint);
        canvas.drawLine(points.get(0).x, points.get(0).y, points.get(2).x, points.get(2).y, paint);
        canvas.drawLine(points.get(0).x, points.get(0).y, points.get(3).x, points.get(3).y, paint);
        canvas.drawLine(points.get(0).x, points.get(0).y, points.get(4).x, points.get(4).y, paint);
        canvas.drawLine(points.get(2).x, points.get(2).y, points.get(4).x, points.get(4).y, paint);
        canvas.drawLine(points.get(2).x, points.get(2).y, points.get(3).x, points.get(3).y, paint);
        canvas.drawLine(points.get(2).x, points.get(2).y, points.get(1).x, points.get(1).y, paint);
        canvas.drawLine(points.get(1).x, points.get(1).y, points.get(3).x, points.get(3).y, paint);
*/

    }

    public void setLines(List<int[]> pAndn){

        int num = points.size();
        lines = new ArrayList[num];
        for(int i=0; i<num; i++){
            lines[i] = new ArrayList<>();
        }

        int i=0;
        for(int[] pn: pAndn){
            for(int n: pn){
                lines[i].add(n);
            }
            i++;
        }

    }


    public void setPoints(List<PointF> ps){
        points = ps;
    }

    public void draw(){
        invalidate();
        requestLayout();
    }
}
