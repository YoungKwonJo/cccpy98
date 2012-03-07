package youngjo.physics.lheviewerv3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class viewHelp extends View{

    private float width, height, bin, size;
    private int id;

    private final Paint mP1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP4 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP5 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP6 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP7 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mP8 = new Paint(Paint.ANTI_ALIAS_FLAG);
    
	public viewHelp(Context context, int width, int height, int id) {
        super(context);
      
    	this.width=width;
    	this.height=height;
    	this.bin=20;
    	this.id=id;
    	this.size=height/30;

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
        if((int)id == 0) {
        	String[] str ={"This is a event viewer for LHEfile.","", 
        				   "1: Click the Button \"OK\", if you insert a No. ",
        				   "  of a event among default data.",
        				   "2: Click the Button \"next\" or \"before\".",
        				   "3: Check the checklist and select a file in the",
        				   "  dropdown menu. The files are located in ",
        				   "  \"/sdcard/\" (Plese, save a lhe file in the ",
        				   "  location!!) ","",
        				   "And then you may see the event in ",
        				   "2-dimesion(X-Y) as a view in transverse",
        				   "directon."};
   
        
        	for(int i=0;i<str.length;i++) {
        		canvas.drawText(str[i], 10, 40+height*i/bin, mP1);               	
        	}
        }
        if((int)id ==1 ) {
        	Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.smep);
        	int iheight = bitmap.getHeight();
        	int iwidth = bitmap.getWidth();
            float ratio1 = iheight/(height-height/14);
            float ratio2 = iwidth/width;
        	
        	if(ratio1 > ratio2 ) {
        		iheight=(int) (height-height/14);
        		iwidth=(int) (width/ratio1);
        	} 
        	else {
        		iheight=(int) ((height-height/14)/ratio2);
        		iwidth=(int) (width);        		
        	}
        	
        	Bitmap nbitmap = Bitmap.createScaledBitmap(bitmap, iwidth, iheight, true);
        	
        	canvas.drawBitmap(nbitmap, 0,30, null);
        	bitmap.recycle(); 
        	nbitmap.recycle();
        }
    }    
}
