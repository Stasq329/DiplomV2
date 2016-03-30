package rfict.diplom.medicalassistantv20;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

public class Complaints extends AppCompatActivity {
    public static enum TransitionType {SlideLeft}
    public static TransitionType transitionType;

    Button btn6;
    final static int RQS_RECORDING = 1;

    Uri savedUri;


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints);

        btn6 = (Button) findViewById(R.id.button6);

        btn6.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent =
                        new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, RQS_RECORDING);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }});
    }






    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem action_settings2 = menu.findItem(R.id.action_settings2);
        action_settings2.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
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
                break;


        }

        return super.onOptionsItemSelected(item);
    }



}