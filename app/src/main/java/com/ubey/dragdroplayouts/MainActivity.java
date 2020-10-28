package com.ubey.dragdroplayouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         View dragView = findViewById(R.id.dragView);

        MyDragListener myDragListener = new MyDragListener();

        MyTouchListener myTouchListener = new MyTouchListener();
        dragView.setOnTouchListener(myTouchListener);
        LinearLayout top= findViewById(R.id.topLayout);
        LinearLayout bottom= findViewById(R.id.bottomLayout);
        top.setOnDragListener(myDragListener);
        bottom.setOnDragListener(myDragListener);

    }
    class MyDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.invalidate();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.invalidate();
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View)dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.invalidate();
                    break;
                default:break;

            }

            return true;
        }
    }
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }
}