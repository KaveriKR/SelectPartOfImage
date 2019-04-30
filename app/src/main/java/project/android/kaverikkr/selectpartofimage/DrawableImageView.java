package project.android.kaverikkr.selectpartofimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by abc on 06/03/2018.
 */

public class DrawableImageView extends android.support.v7.widget.AppCompatImageView {
    Bitmap imageBitmap,b;
    Path mPath,dots,filledDots;
    Paint paint,dotColor,pathColor;
    private Canvas canvas;
    Context con;
    int c=1,counter=1;
    ArrayList<PointF> pointFS = new ArrayList<>();

    public DrawableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        con=context;
        mPath= new Path();
        dots = new Path();
        setDrawingCacheEnabled(true);

        pathColor = new Paint();
        pathColor.setAntiAlias(true);
        pathColor.setColor(Color.BLUE);
        pathColor.setStyle(Paint.Style.FILL);
        filledDots  =new Path();
        filledDots.addCircle(0,0,3f, Path.Direction.CCW);
        filledDots.close();

        dotColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotColor.setColor(Color.WHITE);
        paint= new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(50);
        PathDashPathEffect dashPathEffect = new PathDashPathEffect(filledDots,10f,10f,PathDashPathEffect.Style.TRANSLATE);
        pathColor.setPathEffect(dashPathEffect);
        buildDrawingCache();

    }
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        imageBitmap=bm;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvas= new Canvas();

        if(imageBitmap!=null){
            // imageBitmap= Bitmap.createBitmap(w,h,mutabelBitmap);
            imageBitmap= Bitmap.createScaledBitmap(imageBitmap,w,h,true);
            setDrawingCacheEnabled(true);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);

        canvas.drawPath(dots,paint);

       if(pointFS.size()>1 & pointFS.size()<5) {
           for (int i = 0; i < pointFS.size(); i++) {

               if(i<(pointFS.size()-1)) {

                   mPath.moveTo(pointFS.get(i).x, pointFS.get(i).y);
                   mPath.lineTo(pointFS.get(i + 1).x, pointFS.get(i + 1).y);
                   canvas.drawPath(mPath, pathColor);
               }
               else if(i== 3) {

                   mPath.lineTo(pointFS.get(0).x,pointFS.get(0).y);

                   canvas.drawPath(mPath, pathColor);
                   canvas.save();

               }

           }
       }else if(pointFS.size()>0){
           Log.e("dot", "onDraw: "+1 );
           if(canvas.getSaveCount()>1)
           canvas.restore();

       }
        if(canvas.getSaveCount()>1)
            canvas.restore();

    }

    public Bitmap saveBitmap(){

        Bitmap bitmap = getDrawingCache();
        return  bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX =event.getX();
        float touchY=event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                dots.addCircle(touchX, touchY, 5f, Path.Direction.CCW);
                if(pointFS.size()==4){
                    pointFS.clear();
                    pointFS = null;
                    pointFS = new ArrayList<>();
                    pointFS.add(new PointF(touchX, touchY));
                    if(canvas.getSaveCount()>1){
                        canvas.restore();
                    }
                }else{
                    pointFS.add(new PointF(touchX, touchY));
                }

                     invalidate();

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
            default: return false;

        }
        invalidate();
        return true;
    }


}
