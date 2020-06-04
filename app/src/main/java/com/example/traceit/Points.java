package com.example.traceit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Points extends View {

    private Paint paint, paint2, paint3;
    private List<PointF> points;

    private ArrayList<Integer>[] neighbors;
    private RectF[] touchRect;

    PointF lineFrom, lineTo;
    PointF prevPoint;

    int prevLabel;

    Line line;
    List<Line> lines = new ArrayList<Line>();

    boolean gameStarted = false;

    private Dialog nextlabeldialogue;
    private ImageButton nextButton, homeButton, retryButton;
    private TextView msg;

    private int whichLevel = 1;

    private DatabaseHelper dbhelp = new DatabaseHelper(getContext());

    public Points(Context context) {
        super(context);
    }

    public Points(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Points(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(50);

        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setDither(true);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeWidth(15);

        paint3 = new Paint();
        paint3.setColor(Color.BLACK);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setDither(true);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth(15);

        for(PointF p: points){
            canvas.drawPoint(p.x, p.y, paint);
        }

        if(lineFrom != null){
            canvas.drawLine(lineFrom.x, lineFrom.y, lineTo.x, lineTo.y, paint2);
        }

        for(Line l: lines){
            canvas.drawLine(l.x1, l.y1, l.x2, l.y2, paint3);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            lineTo = new PointF(x,y);

            int i=0;
            for(PointF p: points){
                if(touchRect[i].contains(x,y)){
                    if(!gameStarted){
                        lineFrom = p;
                        gameStarted = true;

                        prevPoint = p;
                        prevLabel = i;

                    }else if(gameStarted && neighbors[i].contains(prevLabel)){
                        line = new Line(prevPoint.x, prevPoint.y, p.x, p.y);
                        lines.add(line);
                        lineFrom = p;

                        neighbors[i].remove(new Integer(prevLabel));
                        neighbors[prevLabel].remove(new Integer(i));

                        prevPoint = p;
                        prevLabel = i;
                    }
                    break;
                }
                i++;
            }

            invalidate();


        }else if (event.getAction() == MotionEvent.ACTION_UP){
            retryNext();
            checkWin();
        }


        return true;
    }

    private void checkWin() {
        if(whichLevel == 1 && lines.size() == 8){
            showWinDialogue();
        }else if(whichLevel == 2 && lines.size() == 17){
            showWinDialogue();
        }else if(whichLevel == 3 && lines.size() == 8) showWinDialogue();
        else if(whichLevel == 4 && lines.size() == 12) showWinDialogue();
        else if(whichLevel == 5 && lines.size() == 10) showWinDialogue();
        else if(lines.size() != 0){
            showLoseDialogue();
        }
    }

    public void retryNext(){
        nextlabeldialogue = new Dialog(getContext());
        nextlabeldialogue.setContentView(R.layout.nextleveldialogue);
        nextlabeldialogue.setCanceledOnTouchOutside(false);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.70);

        nextlabeldialogue.getWindow().setLayout(width, height);

        nextButton = (ImageButton) nextlabeldialogue.findViewById(R.id.next);
        retryButton = (ImageButton) nextlabeldialogue.findViewById(R.id.retry);
        homeButton = (ImageButton) nextlabeldialogue.findViewById(R.id.home);
    }

    public void showLoseDialogue(){

        msg = (TextView) nextlabeldialogue.findViewById(R.id.msg);

        msg.setText("Game Over!!");
        //nextButton.setText("Retry");

        nextButton.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.VISIBLE);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), start_activity.class);
                getContext().startActivity(intent);
                //  nextlabeldialogue.dismiss();
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        });

        nextlabeldialogue.show();
    }

    public void showWinDialogue(){

        whichLevel++;
        updateDB(whichLevel);

        msg = (TextView) nextlabeldialogue.findViewById(R.id.msg);
        msg.setText("Awesome!!");

        nextButton.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.INVISIBLE);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), start_activity.class);
                getContext().startActivity(intent);
                //  nextlabeldialogue.dismiss();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        });

        nextlabeldialogue.show();

        lines.clear();
        points.clear();
        //neighbors = null;
        gameStarted = false;
    }

    public void createNeighbors(){
        int num = points.size();
        neighbors = new ArrayList[num];
        for(int i=0; i<num; i++){
            neighbors[i] = new ArrayList<>();
        }
    }

    public void setNeighbors(List<int[]> pAndn){

        int i=0;
        for(int[] pn: pAndn){
            for(int n: pn){
                neighbors[i].add(n);
            }
            i++;
        }

    }

    public void setTouchPoints(){
        float touchRectSize = 40f;
        touchRect = new RectF[points.size()];

        int i = 0;
        for(PointF p: points){
            touchRect[i] = new RectF(p.x - touchRectSize, p.y - touchRectSize,
                    p.x + touchRectSize, p.y + touchRectSize);
            i++;
        }
    }

    public void setPoint(List<PointF> ps){
        points = ps;
    }

    public void draw(){
        invalidate();
        requestLayout();
    }

    public void setWhichLevel(int l){
        whichLevel = l;
    }

    public void toastMe(String str){
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void updateDB(int l){
        boolean updatedata = dbhelp.updateData(l);

        if(updatedata) toastMe("Great!!");
        else toastMe("Something went wrong");
    }


}
