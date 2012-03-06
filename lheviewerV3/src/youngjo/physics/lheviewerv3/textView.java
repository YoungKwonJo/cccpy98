package youngjo.physics.lheviewerv3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class textView extends View{

    private float width, height, bin, size;
    private float Num;
    private String text;
    private final Paint mP1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    
	public textView(Context context,String text, float Num, int width, int height) {
        super(context);

        this.text=text;
        this.width=width;
    	this.height=height;
    	this.bin=20;
    	this.size=height/30;
    	this.Num=Num;

    	mP1.setColor(0xFFFFFFFF); mP1.setTextSize(size);

	}
	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
        canvas.drawText(text,10, 100+height*Num/bin, mP1);
	} 
}
