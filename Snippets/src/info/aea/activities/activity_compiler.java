package info.aea.activities;


import info.aea.compiler.activities.Ideone;
import info.aea.compiler.activities.RunThread;
import info.aea.compiler.adapters.AdapterLanguages;
import info.aea.database.SnippetsDB_Helper;
import info.aea.snippets.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class activity_compiler extends Activity {
	
ProgressDialog progressDialog;
	
	TextView textOutput;
	TextView textResult;
	
	SQLiteDatabase db;
	SnippetsDB_Helper logindb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compile);
		
		System.out.println("starting activity");
		
		logindb=new SnippetsDB_Helper(this);
		
		String codeid, codelang; // get from fragment bundle
		//Extract the bundle from the intent to use variables
	    Bundle bundle = getIntent().getExtras();
	    //Extract each value from the bundle for usage
	    codeid = bundle.getString("codeid");
	    codelang = bundle.getString("codelang");
	 	System.out.println("code id-------" + codeid + "codelang--------" + codelang);
	    
		
		String str = logindb.getrow_lang(codeid, codelang);
		EditText editText = (EditText)findViewById(R.id.edittextSource);
		editText.setText(str);

				 
	   			 
	
		// progress dialog
        progressDialog = ProgressDialog.show(this, "", "Loading. Please wait...", false);
		progressDialog.hide();
        
		final Spinner spinnerLanguage = (Spinner)this.findViewById(R.id.spinnerLanguage);
		final EditText edittextSource = (EditText)this.findViewById(R.id.edittextSource);
		final EditText edittextInput = (EditText)this.findViewById(R.id.edittextInput);
		final Activity activity = this;
		textOutput = (TextView)this.findViewById(R.id.textOutput);
		textResult = (TextView)this.findViewById(R.id.textResult);
		
        // languages
        spinnerLanguage.setAdapter(new AdapterLanguages(this, 1));
        
        // execute button
        Button btnExecute = (Button)this.findViewById(R.id.btnExecute);
        btnExecute.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				Ideone ideone = new Ideone(activity);
				
				try{
	        	   RunThread runThread = new RunThread(activity, handler);
	        	   String _s = edittextSource.getText().toString();
	        	   runThread.setSource(_s);
	        	   runThread.setInput(edittextInput.getText().toString());
	        	   runThread.setLang(ideone.getLanguageIdByName(spinnerLanguage.getSelectedItem().toString()));
	        	   runThread.start();
	        	   

	           	
        	   } catch(Exception e){
        		   AlertDialog.Builder errb = new AlertDialog.Builder(activity);
        		   errb.setMessage("Error: " + e.toString());
        		   AlertDialog ad = errb.create();
        		   ad.show();
        	   }
			}
		});
    }
	
	
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	try{
        		        		
        		String command = msg.getData().getString("command");
        		String text = msg.getData().getString("text");
        		
	            if( command.equals("open") ){
	            	progressDialog.show();
	            	textResult.setText("");
	            	
	            } else if( command.equals("close") ){
	            	progressDialog.hide();
	            	
	            } else if( command.equals("echo") ){
	            	progressDialog.setMessage(text);
	            	
	            } else if( command.equals("echo2") ){
	            	textOutput.setText(text);
	            
	            } else if( command.equals("result") ){
	            	textResult.setText(text);           	
	            	//Toast.makeText(getApplicationContext(), "Authorization Error", Toast.LENGTH_LONG).show();	
	            } else if( command.equals("error")){
	            	textOutput.setText(text);
	            	progressDialog.hide();
	            }
        	} catch( Exception e ){
        		//int a = 0;
        	}
        	
        	System.out.println("time-space tradeoff 2");
            System.out.println("compile time=======" + Ideone.time);
	        System.out.println("compile memory========" + Ideone.memory);

	
    
        }
    
    };
    
    
}