package com.excella.smartparking;




import com.excella.smartparking.TCPClient;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends Activity
{

	private TCPClient tcpC;
	
	String bayId[] = {"0000", "0001", "0010", "0011"};//bays that can change for demo
    boolean states[] = {false, false, false, false};//initial states
    
    Animation anim;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        startTCP();
        
        setupLot();
        /**
        ImageButton btn = (ImageButton) findViewById(R.id.bay0000);
        btn.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View arg0) 
			{
				updateButton();
				
			}
		});
		**/
        
        
    }
    
   
    private void setupLot() //sync current state of the Parking Lot
    {
    	/**
    	LinearLayout lL = (LinearLayout) findViewById(R.id.layoutForButtons);
    	//setWidth and Height
    	Button btn = new Button(this);
    	btn.setWidth(500);
    	btn.setHeight(100);
    	btn.setText("Funny");
    	
    	//btn.setX(10);
    	//btn.setY(10);
    	
    	
    	lL.addView(btn);
    	
    	
    	Button btn2 = new Button(this);
    	
    	//btn2.setX(100);
    	//btn2.setY(100);
    	btn2.setWidth(30);
    	btn2.setHeight(100);
    	lL.addView(btn2);
    	
    	//btn2.refreshDrawableState();
    	 
    	
    	LinearLayout lL = (LinearLayout) findViewById(R.id.layoutForButtons);
    	//setWidth and Height
    	Button btn1 = new Button(this);
    	btn1.setWidth(500);
    	btn1.setHeight(100);
    	btn1.setText("Funny");
    	
    	//btn.setX(10);
    	//btn.setY(10);
    	
    	
    	lL.addView(btn1);
    	* 
    	 */
    	
    	sndMsg("REQUEST 0000");
    	//wait(500);
    	sndMsg("REQUEST 0001");
    	//wait(500);
    	sndMsg("REQUEST 0002");
    	
    	//ImageButton btn = (ImageButton) findViewById(R.id.bay0000);
    	ImageView iv = (ImageView) findViewById(R.id.imageView1);
    	
    	anim = new RotateAnimation(0, -45.0f, 0, 0);
    	anim.setFillAfter(true);
    	anim.setDuration(0);
    	//iv.setAnimation(anim);
    	iv = (ImageView) findViewById(R.id.imageView2);
    	//iv.setAnimation(anim);
    	
    	//btn.setAnimation(anim);
    	
    	//btn1.setAnimation(anim);
    
    
    	
	}
    
    //private void updateButton(String id, boolean state)
    private void updateBay(int indx, boolean newState)
    {	
    	ImageView iv;
    	if (indx == 0)
    		iv = (ImageView) findViewById(R.id.imageView1);
    		else if (indx == 1)
    			iv = (ImageView) findViewById(R.id.imageView2);
    			else if (indx ==2)
    				iv = (ImageView) findViewById(R.id.imageView1);
    			else iv = (ImageView) findViewById(R.id.imageView1);
    	
    	//ImageButton btn = (ImageButton) findViewById(R.id.bay0000);
    	//ImageView iv = (ImageView) findViewById(R.id.imageView1);
    	
    	if( states[indx] == false && newState == true)
    	{
        	//btn.setImageResource(R.drawable.occupied);
        	iv.setImageResource(R.drawable.occupied);
        	iv.animate();
        	states[indx] = true;
    	}
    	else if( states[indx] == true && newState == false)
    	{
        	//btn.setImageResource(R.drawable.avail);
        	iv.setImageResource(R.drawable.avail);
        	iv.animate();
        	//iv.
        	iv.refreshDrawableState();
        	states[indx] = false;
    	}
    	
    }
    
    public void sndMsg(String msg)
    {
    	if (tcpC != null) 
    	{
    		
    		tcpC.sendMessage(msg);
        }
    	else
    	{
    		Toast.makeText(this, "Not Connected to Server", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void startTCP()
    {
    	new ConnectTask().execute("");
    }

	@Override
    protected void onPause() 
    {
        super.onPause();

        if (tcpC != null) {
            // disconnect//will reconnect on resume
        	tcpC.stopClient();
        	tcpC = null;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) 
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public class ConnectTask extends AsyncTask<String, String, TCPClient> 
    {

        @Override
        protected TCPClient doInBackground(String... message) 
        {

            
        	tcpC = new TCPClient(new TCPClient.OnMessageReceived() 
        	{
                @Override
                //interface 
                public void messageReceived(String message)
                {
                    //onProgressUpdate
                    publishProgress(message);
                }
            });
        	tcpC.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String msg = values[0];
            boolean st;
            
            if(msg.contains("UPDATE"))
            {
            	if(msg.contains("TRUE"))
        		{
        			st = true;
        		}
        		else if(msg.contains("FALSE"))
        		{
        			st = false;
        		}
        		else
        		{
        			return;
        		}
            	
            	
            	
            	if(msg.contains("0"))
            	{
            		updateBay(0, st);
            	}
            	else if(msg.contains("1"))
            	{
            		updateBay(1, st);
            	}
            	else if(msg.contains("2"))
            	{
            		updateBay(2, st);
            	}
            	
            	//String bayID = msg.substring(0, 3);
            	char bayID = msg.charAt(0);
            	
            	//String bayState = msg.substring(5);
            	char bayState = msg.charAt(2);
            	/**Toast.makeText(ClientActivity.this, "Bay : " + bayID + ", State : " + bayState, Toast.LENGTH_LONG).show();
            	//ImageButton myButton = (ImageButton) findViewById(R.id.bay2);
            	ImageButton myButton;
            	switch(bayID)
            	{
	            	case '1' : myButton = (ImageButton) findViewById(R.id.bay1);
	            		break;
	            	case '2' : myButton = (ImageButton) findViewById(R.id.bay2);
	        		break;
	            	case '3' : myButton = (ImageButton) findViewById(R.id.bay3);
	        		break;
	            	case '4' : myButton = (ImageButton) findViewById(R.id.bay4);
	        		break;
	            	case '5' : myButton = (ImageButton) findViewById(R.id.bay5);
	        		break;
	            	case '6' : myButton = (ImageButton) findViewById(R.id.bay6);
	        		break;
	            	case '7' : myButton = (ImageButton) findViewById(R.id.bay7);
	        		break;
	            	case '8' : myButton = (ImageButton) findViewById(R.id.bay8);
	        		break;
	            	default : myButton = (ImageButton) findViewById(R.id.bay8); 
	            		break;
            	}
            	
            	if(bayState == '1')
            	{
            		myButton.setBackgroundResource(R.drawable.occupied);
            		myButton.setPadding(0, 0, 0, 0);
            		//myButton.refreshDrawableState();
            	}
            	else if(bayState == '0')
            	{
            		myButton.setBackgroundResource(R.drawable.available);
            		myButton.setPadding(0, 0, 0, 0);
            		//myButton.refreshDrawableState();
            	}
            	
            	
            	//myButton.setBackgroundResource(R.drawable.available);
            	 * 
            	 */
            }
            
        }
    }
}
