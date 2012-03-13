package youngjo.physics.lheviewerv3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class myView extends View {
    private final float x;
    private final float y;
    private final int r;
    private float cx, cy;
    private float width, height, bin, size;
    private int event_;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean grid;
 
    private final Paint mP1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP4 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP5 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP6 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP7 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP8 = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    public myView(Context context, int event, float x, float y, int r, int rgb, int width, int height, boolean grid) {
        super(context);
        event_ = event;

        this.x = x*(float) width/9;
        this.y = y*(float) height/9;
        this.r = r * (int) Math.sqrt(width*width+height*height)/500;
    	this.cx= (float) width/2;
    	this.cy= (float) height/(float)2.7;
    	this.width=width;
    	this.height=height;
    	this.bin=30;
    	this.size=height/30;
    	this.grid=grid;

        mPaint.setColor(rgb);        // mPaint.setTextSize(size);
        mPaint2.setColor(0xFFFFFFFF); mPaint2.setTextSize(size);
        mP1.setColor(0xFFFFFFFF); mP1.setTextSize(size);
        mP2.setColor(0xFFFF0000); mP2.setTextSize(size);
        mP3.setColor(0xFF00FF00); mP3.setTextSize(size);
        mP4.setColor(0xFF0000FF); mP4.setTextSize(size);
        mP5.setColor(0xFFFFFF00); mP5.setTextSize(size);
        mP6.setColor(0xFFFF00FF); mP6.setTextSize(size);
        mP7.setColor(0xFF00FFFF); mP7.setTextSize(size);
        mP8.setColor(0x55FFFFFF); mP8.setTextSize(size);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(grid ) {
        	canvas.drawText("Event "+event_, 10, 20, mPaint2);
        	canvas.drawText("quarks", 10, 20+height/bin, mP2);
        	canvas.drawText("leptons", 10, 20+height*2/bin, mP3);
        	canvas.drawText("W/Z boson", 10, 20+height*3/bin, mP4);
        
        	canvas.drawLine(cx,  cy-height/3, cx, cy+height/3, mPaint2);
        	canvas.drawLine(cx-width/3, cy, cx+width/3, cy, mPaint2);
        }
        else canvas.drawCircle(cx+x-r/2, cy+y, r, mPaint);
    }
}
