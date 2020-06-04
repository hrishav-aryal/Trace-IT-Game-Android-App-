package com.example.traceit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<PointF> points = new ArrayList<>();

    private Points pointsDraw;
    private LineDraw lineDraw;

    private int width;
    private int height;

    private DatabaseHelper dbhelper;

    private TextView showLevel;

    //private Dialog alertBox;
    //private Button nextButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointsDraw = (Points) findViewById(R.id.point);
        lineDraw = (LineDraw) findViewById(R.id.line);

        showLevel = (TextView) findViewById(R.id.print);

        width = (int) (getResources().getDisplayMetrics().widthPixels);
        height = (int) (getResources().getDisplayMetrics().heightPixels);

        dbhelper = new DatabaseHelper(this);
        Cursor cursor = dbhelper.getData();

        int level = getLevel(cursor);
        showLevel.setText("Level " + level);

        startGame(level);

    }

    private void startGame(int level) {
        if(level == 1) makeLevel1();
        else if(level == 2) level2();
        else if(level == 3) level3();
        else if(level == 4) level4();
        else if(level == 5) level5();

    }

    public int getLevel(Cursor cur){
        int level = 1;

        while(cur.moveToNext()){
            level = cur.getInt(1);
        }

        return level;
    }

    private void makeLevel1() {

        int marginFromCentreWidth = 182;
        int marginFromCentreHeight = 190;

        PointF point1 = new PointF((width/2) - marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point2 = new PointF((width/2) - marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point3 = new PointF((width/2) + marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point4 = new PointF((width/2) + marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point5 = new PointF((width/2),(height/2) - 500);


        points.clear();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);

        pointsDraw.setPoint(points);
        pointsDraw.setTouchPoints();
        pointsDraw.createNeighbors();

        int[] p1 = {1,2,3,4};
        int[] p2 = {0,2,3};
        int[] p3 = {0,1,3,4};
        int[] p4 = {0,1,2};
        int[] p5 = {0,2};

        List<int[]> pointsAndNeighbors = Arrays.asList(p1,p2,p3,p4,p5);

        lineDraw.setPoints(points);
        lineDraw.setLines(pointsAndNeighbors);
        lineDraw.draw();

        pointsDraw.setNeighbors(pointsAndNeighbors);
        pointsDraw.draw();
        pointsDraw.setWhichLevel(1);

    }

    public void level2(){

        int marginFromCentreWidth = 250;
        int marginFromCentreHeight = 320;

        int x = 145;
        int y = 155;

        PointF point1 = new PointF((width/2) - marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point2 = new PointF((width/2) - marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point3 = new PointF((width/2) + marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point4 = new PointF((width/2) + marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point5 = new PointF((width/2) - x,(height/2) - y);
        PointF point6 = new PointF((width/2) - x,(height/2) + y);
        PointF point7 = new PointF((width/2) + x,(height/2) - y);
        PointF point8 = new PointF((width/2) + x,(height/2) + y);
        PointF point9 = new PointF((width/2),(height/2) - 600);
        PointF point10 = new PointF((width/2),(height/2) + 600);
        PointF point11 = new PointF((width/2),(height/2) - marginFromCentreHeight);
        PointF point12 = new PointF((width/2),(height/2) + marginFromCentreHeight);
        PointF point13 = new PointF((width/2),(height/2) - y);
        PointF point14 = new PointF((width/2),(height/2) + y);



        points.clear();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);
        points.add(point7);
        points.add(point8);
        points.add(point9);
        points.add(point10);
        points.add(point11);
        points.add(point12);
        points.add(point13);
        points.add(point14);


        //lineDraw.setPoints(points);
        //lineDraw.draw();

        pointsDraw.setPoint(points);
        pointsDraw.setTouchPoints();
        pointsDraw.createNeighbors();

        int[] p1 = {1,10};
        int[] p2 = {0,11};
        int[] p3 = {3,10};
        int[] p4 = {2,11};
        int[] p5 = {5,12};
        int[] p6 = {4,13};
        int[] p7 = {7,12};
        int[] p8 = {6,13};
        int[] p9 = {10};
        int[] p10 = {11};
        int[] p11 = {0,2,8,12};
        int[] p12 = {1,3,9,13};
        int[] p13 = {4,6,10,13};
        int[] p14 = {5,7,11,12};

        List<int[]> pointsAndNeighbors = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14);

        lineDraw.setPoints(points);
        lineDraw.setLines(pointsAndNeighbors);
        lineDraw.draw();

        pointsDraw.setNeighbors(pointsAndNeighbors);
        pointsDraw.draw();
        pointsDraw.setWhichLevel(2);

    }

    public void level3(){

        int marginFromCentreWidth = 250;
        int marginFromCentreHeight = 400;

        PointF point1 = new PointF((width/2) - marginFromCentreWidth,(height/2));
        PointF point2 = new PointF((width/2) + marginFromCentreWidth,(height/2));
        PointF point3 = new PointF((width/2) - marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point4 = new PointF((width/2) + marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point5 = new PointF((width/2),(height/2) - 200);
        PointF point6 = new PointF(((width/2) - 125),(height/2) + 100);
        PointF point7 = new PointF((width/2),(height/2) + 200);


        points.clear();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);
        points.add(point7);


        pointsDraw.setPoint(points);
        pointsDraw.setTouchPoints();
        pointsDraw.createNeighbors();

        int[] p1 = {1,5};
        int[] p2 = {0,6};
        int[] p3 = {5,6};
        int[] p4 = {6,4};
        int[] p5 = {3,5};
        int[] p6 = {0,2,4};
        int[] p7 = {1,2,3};

        List<int[]> pointsAndNeighbors = Arrays.asList(p1,p2,p3,p4,p5,p6,p7);

        lineDraw.setPoints(points);
        lineDraw.setLines(pointsAndNeighbors);
        lineDraw.draw();

        pointsDraw.setNeighbors(pointsAndNeighbors);
        pointsDraw.draw();
        pointsDraw.setWhichLevel(3);

    }

    private void level4() {

        int marginFromCentreWidth = 182;
        int marginFromCentreHeight = 190;

        PointF point1 = new PointF((width/2) - marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point2 = new PointF((width/2) - marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point3 = new PointF((width/2) + marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point4 = new PointF((width/2) + marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point5 = new PointF((width/2),(height/2) - 500);
        PointF point6 = new PointF((width/2) - 500,(height/2));
        PointF point7 = new PointF((width/2) + 500,(height/2));


        points.clear();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);
        points.add(point7);

        pointsDraw.setPoint(points);
        pointsDraw.setTouchPoints();
        pointsDraw.createNeighbors();

        int[] p1 = {1,2,3,4,5};
        int[] p2 = {0,2,3,5};
        int[] p3 = {0,1,3,4,6};
        int[] p4 = {0,1,2,6};
        int[] p5 = {0,2};
        int[] p6 = {0,1};
        int[] p7 = {2,3};

        List<int[]> pointsAndNeighbors = Arrays.asList(p1,p2,p3,p4,p5,p6,p7);

        lineDraw.setPoints(points);
        lineDraw.setLines(pointsAndNeighbors);
        lineDraw.draw();

        pointsDraw.setNeighbors(pointsAndNeighbors);
        pointsDraw.draw();
        pointsDraw.setWhichLevel(4);

    }

    private void level5() {

        int marginFromCentreWidth = 300;
        int marginFromCentreHeight = 300;

        PointF point1 = new PointF((width/2),(height/2));
        PointF point2 = new PointF((width/2),(height/2) - marginFromCentreHeight);
        PointF point3 = new PointF((width/2),(height/2) + marginFromCentreHeight);
        PointF point4 = new PointF((width/2) + marginFromCentreWidth,(height/2) + marginFromCentreHeight);
        PointF point5 = new PointF((width/2) + marginFromCentreWidth,(height/2));
        PointF point6 = new PointF((width/2) - marginFromCentreWidth,(height/2) - marginFromCentreHeight);
        PointF point7 = new PointF((width/2) - marginFromCentreWidth,(height/2));


        points.clear();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);
        points.add(point7);

        pointsDraw.setPoint(points);
        pointsDraw.setTouchPoints();
        pointsDraw.createNeighbors();

        int[] p1 = {1,2,3,4,5,6};
        int[] p2 = {0,5};
        int[] p3 = {0,3};
        int[] p4 = {0,2,4};
        int[] p5 = {0,3};
        int[] p6 = {0,1,6};
        int[] p7 = {0,5};

        List<int[]> pointsAndNeighbors = Arrays.asList(p1,p2,p3,p4,p5,p6,p7);

        lineDraw.setPoints(points);
        lineDraw.setLines(pointsAndNeighbors);
        lineDraw.draw();

        pointsDraw.setNeighbors(pointsAndNeighbors);
        pointsDraw.draw();
        pointsDraw.setWhichLevel(5);

    }

    public void toastMe(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
