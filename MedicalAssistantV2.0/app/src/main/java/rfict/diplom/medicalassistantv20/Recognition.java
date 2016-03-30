package rfict.diplom.medicalassistantv20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Recognition extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    public static enum TransitionType {SlideLeft}
    public static TransitionType transitionType;
    Button btn5;
    //variable for checking Voice Recognition support on user device
    private static final int VR_REQUEST = 999;

    //variable for checking TTS engine data on user device
    private int MY_DATA_CHECK_CODE = 0;

    //Text To Speech instance
    private TextToSpeech repeatTTS;

    //ListView for displaying suggested words
    private ListView wordList;

    //Log tag for output information
    private final String LOG_TAG = "SpeechRepeatActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognition);
        btn5 = (Button)findViewById(R.id.button5);
        btn5.setOnClickListener(this);

        //gain reference to speak button
        Button speechBtn = (Button) findViewById(R.id.speech_btn);
        //gain reference to word list
        wordList = (ListView) findViewById(R.id.word_list);

        //find out whether speech recognition is supported
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> intActivities = packManager.queryIntentActivities
                (new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (intActivities.size() != 0) {
            //speech recognition is supported - detect user button clicks
            speechBtn.setOnClickListener(this);
            //prepare the TTS to repeat chosen words
            Intent checkTTSIntent = new Intent();
            //check TTS data
            checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            //start the checking Intent - will retrieve result in onActivityResult
            startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        }
        else
        {
            //speech recognition not supported, disable button and output message
            speechBtn.setEnabled(false);
            Toast.makeText(this, "Oops - Speech recognition not supported!", Toast.LENGTH_LONG).show();
        }

        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //click listener for items within list
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //cast the view
                TextView wordView = (TextView)view;
                //retrieve the chosen word
                String wordChosen = (String) wordView.getText();
                //output for debugging
                Log.v(LOG_TAG, "chosen: " + wordChosen);
                //speak the word using the TTS
                repeatTTS.speak("You said: "+wordChosen, TextToSpeech.QUEUE_FLUSH, null);
                //output Toast message
                Toast.makeText(Recognition.this, "You said: "+wordChosen, Toast.LENGTH_SHORT).show();//**alter for your Activity name***



            }
        });
            }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case R.id.Vithov:
              intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:12345"));
               startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);



        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem action_settings2 = menu.findItem(R.id.action_settings2);
        action_settings2.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button5:
                intent = new Intent(this, Complaints.class);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
        }

        if (v.getId() == R.id.speech_btn) {
            //listen for results
            listenToSpeech();
        }

    }

    private void listenToSpeech() {

        //start the speech recognition intent passing required data
        Intent listenIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //indicate package
        listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        //message to display while listening
        listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word!");
        //set speech model
        listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //specify number of results to retrieve
        listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

        //start listening
        startActivityForResult(listenIntent, VR_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check speech recognition result
        if (requestCode == VR_REQUEST && resultCode == RESULT_OK)
        {
            //store the returned word list as an ArrayList
            ArrayList<String> suggestedWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //set the retrieved list to display in the ListView using an ArrayAdapter
            wordList.setAdapter(new ArrayAdapter<String>(this, R.layout.word, suggestedWords));
        }

        //returned from TTS data check
        if (requestCode == MY_DATA_CHECK_CODE)
        {
            //we have the data - create a TTS instance
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
                repeatTTS = new TextToSpeech(this, this);
                //data not installed, prompt the user to install it
            else
            {
                //intent will take user to TTS download page in Google Play
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }

        //call superclass method
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onInit(int status) {
        //if successful, set locale
       // if (initStatus == TextToSpeech.SUCCESS)
            repeatTTS.setLanguage(Locale.UK);//***choose your own locale here***

    }
}
