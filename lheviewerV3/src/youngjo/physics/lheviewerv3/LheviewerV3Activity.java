package youngjo.physics.lheviewerv3;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class LheviewerV3Activity extends Activity {
    /** Called when the activity is first created. */
	EditText edit;
	//TextView text;
	String rss;

	FrameLayout main;
	int event, totevent;
	int width;
	int height;
	boolean job;
	CheckBox checkbox;
	ArrayList<String> list;
	Spinner spinner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        edit = (EditText) findViewById(R.id.editText1);
        edit.setText("1");
        //edit.setHint("insert Event Num.");
        event =0;
        
        //rss = "http://higgslab.korea.ac.kr/TPair_unweighted_events.lhe";
       
        WindowManager w = getWindowManager(); 
        Display d = w.getDefaultDisplay(); 
        width = d.getWidth(); 
        height = d.getHeight();
        		
        main = (FrameLayout) findViewById(R.id.frameLayout1);
        main.addView(new viewHelp(this,width,height,0));
        getEvent(0);
        job=true;
        
////////////////////////////        
        File[] listFiles =(new File("/sdcard/download/").listFiles()); 
        list = new ArrayList<String>(); 
        for( File file : listFiles ) {
          	if( file.getName().toString().endsWith(".lhe") ) list.add(file.getName().toString());
        }

    	spinner = (Spinner)findViewById(R.id.spinner1); 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        spinner.setAdapter(adapter);
//////////////////////////
        checkbox = (CheckBox) findViewById(R.id.checkBox1);
       
    }
    public void clickHome(View v) {
    	main.removeAllViewsInLayout();
    	main.addView(new viewHelp(this,width,height,0));
        event=0; 
    }
    public void clickSMlist(View v) {
    	main.removeAllViewsInLayout();

    	if(job==true) {
    		main.addView(new viewHelp(this,width,height,1));
    		job = false;
    	}
    	else  { 
    		changeView(getEvent( event));
    		job = true;
    	}
        //event=1; 
    }
    public void clickBefore(View v) {
    	event--;
    	if(event <= 1) event = totevent;
    	changeView(getEvent( event));
    }
    public void clickNext(View v) {
    	event++;
    	if(event >= totevent) event=1;
    	changeView(getEvent( event));
    }
    public void clickButton(View v) {

    	event = Integer.parseInt(edit.getText().toString());
    	changeView(getEvent( event));
    }  
    public void textEvent(ArrayList<Particle> particles) {
        String[] str1={"","","","","","","","","","","",""};
        //int step=1;
        for (int i = 0; i < particles.size(); i++) {
        	Particle particle = particles.get(i);
            if(((int)particle.m1 == 0 || (int)particle.m2 == 0 )) str1[1] += particle.label;
        	if(i==2 ) str1[1] += " ¡æ ";
           // if(((int)particle.m1 == 1 || (int)particle.m2 == 2 )) str1[1] += particle.label; 

            if((int) particle.status == 2) {
            //	step++;
            	str1[particle.PN] +=particle.label+" ¡æ ";
            }
            if( (int) particle.status != 0 ) {
            	str1[(int)particle.m1] +=particle.label;
            }
             
        }

        //String str = str1[1]+"\n"+str1[2]+"\n"+str1[3]+"\n"+str1[4]+"\n"+str1[5]+"\n";
    	main.addView(new textView(this, str1[1], 1, width, height));
    	main.addView(new textView(this, str1[2], 2, width, height));
    	main.addView(new textView(this, str1[3], 3, width, height));
    	main.addView(new textView(this, str1[4], 4, width, height));
    	main.addView(new textView(this, str1[5], 5, width, height));
    	main.addView(new textView(this, str1[6], 6, width, height));
    }
    
    public void changeView(ArrayList<Particle> particles) {        
        //int event = Integer.parseInt(edit.getText().toString());
       // ArrayList<Particle> particles = getEvent( event);
        int[] rgb = {0x99FF0000, 0x99550000, 0x9900FF00, 0x99005500, 0x990000FF, 0x99FFFF00, 0x9900FFFF};
        
        //main.addView(new myView(this,200,110,5, 0xFFFF00FF));
        main.removeAllViewsInLayout();
 
        float[] ppx = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, ppy={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

        textEvent(particles);

        int r=1;
        for(int i=0;i<particles.size();i++) {
        	Particle particle = particles.get(i);

        	if(i < 2) {
        		ppx[i]=(float)Math.cos(particle.phi)*(float)0.1;
        		ppy[i]=(float)Math.sin(particle.phi)*(float)0.1;
            }
        	else {
        		ppx[i]=(ppx[(int)particle.m1-1]+ppx[(int)particle.m2-1])/2 
        				+ (float)Math.cos(particle.phi);
        		ppy[i]=(ppy[(int)particle.m1-1]+ppy[(int)particle.m2-1])/2 
        				+ (float)Math.sin(particle.phi);
        	}
        	
        	if( Math.abs(particle.pid) < 10 ) r = 0;
        	else if( Math.abs(particle.pid) < 20 && Math.abs(particle.pid)%2==0 ) r=3;
        	else if( Math.abs(particle.pid) < 20 ) r = 2;
        	else if( Math.abs(particle.pid) == 21 ) r = 1;
        	else if( Math.abs(particle.pid) < 30 ) r=4;
        	else r=4;
 
      		main.addView(new myView(this, event , ppx[i], ppy[i],
                        (int) Math.round(particle.pt)/100+7, rgb[r],width,height));

        }
       
    }
    
    public ArrayList<Particle> getEvent(int theEvt) {
    	ArrayList<Particle> particles = new ArrayList<Particle>();
    	
    	try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();            

            Resources myResources = getResources();
            InputStream is;
            
            if(checkbox.isChecked()) {
                File file = new File("/sdcard/download/"+list.get(spinner.getSelectedItemPosition()).toString());
                xpp.setInput(new InputStreamReader(new FileInputStream(file)));

                //is = myResources.openRawResource(R.raw.tpair);
                //xpp.setInput(is,"euc-kr");
            }
            else {
               	is = myResources.openRawResource(R.raw.tpair);
                xpp.setInput(is,"UTF-8");
            }
            
            int eventType = xpp.getEventType();
            
            int eventNo=0;
            
            while (eventType != XmlPullParser.END_DOCUMENT) {                
                if (eventType == XmlPullParser.START_TAG) {
                	
                    if (xpp.getName().equals("event") && eventNo < 55){
                    	eventNo++;
                    
                    	if( eventNo == theEvt) {

                    		String title = xpp.nextText();
                        	String[] titleData = title.split("\n");
                        	int pN = titleData.length;

                        	for(int i=1; i<pN-1; i++){
                        		Particle ptr = Info(titleData[i+1].split("\\s+"));
                        		ptr.PN=i;
                            	particles.add(ptr);
                        	}
                    	}
                    }
                }
                eventType = xpp.next();
                
            } 
            totevent=eventNo;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return particles;
    }
    
    class Particle{
    	int PN, pid, status; 
    	double m1, m2, d1, d2, E, px, py, pz, m, s1, s2;
    	double pt, phi;
    	String label;
    }
    public Particle Info(String[] str) {
    
    	Particle particle = new Particle();
    	particle.pid=Integer.parseInt(str[1]);
    	particle.status=Integer.parseInt(str[2]);
    	particle.m1=Integer.parseInt(str[3]);
    	particle.m2=Integer.parseInt(str[4]);
    	particle.d1=Integer.parseInt(str[5]);
    	particle.d2=Integer.parseInt(str[6]);

    	particle.E=Double.parseDouble(str[7]);
    	particle.px=Double.parseDouble(str[8]);
    	particle.py=Double.parseDouble(str[9]);
    	particle.pz=Double.parseDouble(str[10]);
    	particle.m=Double.parseDouble(str[11]);
    	particle.s1=Double.parseDouble(str[12]);
    	particle.s2=Double.parseDouble(str[13]);

    	particle.pt = Math.sqrt(particle.px*particle.px + particle.py*particle.py);
    	particle.phi =  particle.px == 0.0 && particle.py == 0.0 ? 0.0 : Math.atan2(particle.py,particle.px);
    	
        String[] quark={"u","d","s","c","b","t"};
        String[] lepton={"e","nu_e","mu","nu_mu","tau","nu_tau"};
        String[] force={"g","A","Z","W","H"};

        if(Math.abs(particle.pid) < 10) {
        	particle.label=quark[Math.abs(particle.pid)-1];
        	if(particle.pid < 0) particle.label+="~";    
        }    
        else if(Math.abs(particle.pid) < 20 ) { 
        	particle.label=lepton[Math.abs(particle.pid)-11];
        	
        	if(particle.pid < 0 && Math.abs(particle.pid)%2==1 ) particle.label+="+";
        	else if( Math.abs(particle.pid)%2==1) particle.label+="-";
        	
        	if(particle.pid < 0 && Math.abs(particle.pid)%2==0 ) particle.label+="~";
        }
        else if(Math.abs(particle.pid) < 30) {
        	particle.label=force[Math.abs(particle.pid)-21];
        	
        	if(particle.pid < 0 && Math.abs(particle.pid) ==24) particle.label+="-";
        	else if( Math.abs(particle.pid) ==24) particle.label+="+";
        }
        else {
        	particle.label="no sm particle";
        }
    	
    	return particle; 
    }
}