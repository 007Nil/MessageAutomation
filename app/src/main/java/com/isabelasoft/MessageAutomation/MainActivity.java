package com.isabelasoft.MessageAutomation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.isabelasoft.MessageAutomation.Automation.DatabaseService;
import com.isabelasoft.MessageAutomation.Database.DatabaseClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Check Box Log";

//    private MsgAutomationDB appDB = MsgAutomationDB.getMsgAutomationDB(this);

//    private StateModel stateModel = new StateModel();

    private String yourVote;
    // These are the global variables
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    Button buttonSubmit;
//    Button button;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);

        // layout instances
        buttonSubmit = (Button) findViewById(R.id.done);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        result = (TextView) findViewById(R.id.stateText);
        DatabaseService dbClient = new DatabaseService(this.getApplicationContext());
        String stateResult = dbClient.findState();
        if (stateResult == null) {
            result.setText(" ");
        }
        else{
            result.setText(stateResult);
        }

//        button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startService(new Intent(getApplicationContext(),ApplicationService.class));
//            }
//        });
        /*
            Submit Button
        */
        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get the selected RadioButton of the group
                selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                //get RadioButton text
                 yourVote = selectedRadioButton.getText().toString();
//                stateModel.setState(yourVote);
                // display it as Toast to the user
                Toast.makeText(MainActivity.this, "Selected Radio Button is:" + yourVote , Toast.LENGTH_LONG).show();
                saveTask(yourVote);
            }
        });
    }

//    public void minimizeApp(View view) {
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//    }
//
   private void saveTask(String yourVote) {
       class SaveTask extends AsyncTask<Void, Void, Void> {

           @Override
           protected Void doInBackground(Void... voids) {
//               StateModel stateModel = new StateModel();

               String currentState = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                       .stateDao().findState();
//               System.out.println("currentState is ="+currentState);


//               Log.d(TAG, "doInBackground: "+stateModel.toString());
               if (currentState == null) {
                   DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                           .stateDao()
                           .insetState(yourVote);

               } else {
                   DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                           .stateDao()
                           .updateState(yourVote);
               }
               return null;
           }

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               finish();
               startActivity(new Intent(getApplicationContext(), MainActivity.class));
               Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

               result = (TextView) findViewById(R.id.stateText);
               DatabaseService dbClient = new DatabaseService(getApplicationContext());
               String stateResult = dbClient.findState();
               if (stateResult == null) {
                   result.setText(" ");
               }
               else{
                   result.setText(stateResult);
               }

           }


       }
       SaveTask st = new SaveTask();
       st.execute();

   }
}
