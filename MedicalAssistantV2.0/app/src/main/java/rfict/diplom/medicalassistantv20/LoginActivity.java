package rfict.diplom.medicalassistantv20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static enum TransitionType {SlideLeft}
    public static TransitionType transitionType;

    EditText editText;
    EditText editText2;
    Button btnLogin;
    SharedPreferences NameSave;
    SharedPreferences PasswordSave;
    final String SAVED_TEXT = "saved_text";
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        btnLogin = (Button) findViewById(R.id.button);

        btnLogin.setOnClickListener(this);
        flipper.setFlipInterval(2000);// setting the interval 1500 milliseconds
        flipper.startFlipping(); // views flipping starts.
        load();

    }

    @Override
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
MenuItem action_settings2 = menu.findItem(R.id.action_settings2);
        action_settings2.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                String Name = "Oreshko";
                String Name2 = "Soroka";
                String Password = "12345";

                if (editText.getText().toString().equals(Name) && editText2.getText().toString().equals(Password))
                {
                    save();
                    Intent intent = new Intent(this, Pacient1.class);
                    startActivity(intent);
                    transitionType = TransitionType.SlideLeft;
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                    break;
                }
                else if (editText.getText().toString().equals(Name2) && editText2.getText().toString().equals(Password))
            {
                save();
                Intent intent = new Intent(this, Pacient2.class);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            }
                else if (editText.getText().toString().equals("") && editText2.getText().toString().equals(""))
                {
                    Toast temp = Toast.makeText(LoginActivity.this , "Имя пользователя и пароль не введены!" , Toast.LENGTH_SHORT);
                    temp.show();
                    break;
                }
                else if (editText.getText().toString().equals(""))
                {
                    Toast temp = Toast.makeText(LoginActivity.this , "Имя пользователя не введено!" , Toast.LENGTH_SHORT);
                    temp.show();
                    break;
                }
                else if (editText2.getText().toString().equals(""))
                {
                    Toast temp = Toast.makeText(LoginActivity.this , "Пароль не введен!" , Toast.LENGTH_SHORT);
                    temp.show();
                    break;
                }
                else
                {
                    Toast temp = Toast.makeText(LoginActivity.this , "Имя пользователя и пароль введены не верно!" , Toast.LENGTH_SHORT);
                    temp.show();
                    break;
                }

        }
    }



    private void save() {

        NameSave = getSharedPreferences("NameSave", MODE_PRIVATE);
        SharedPreferences.Editor ed1 = NameSave.edit();
        PasswordSave = getSharedPreferences("PasswordSave", MODE_PRIVATE);
        SharedPreferences.Editor ed2 = PasswordSave.edit();
        ed1.putString(SAVED_TEXT, editText.getText().toString());
        ed2.putString(SAVED_TEXT, editText2.getText().toString());
        ed1.commit();
        ed2.commit();


    }

    private void load() {



        NameSave = getSharedPreferences("NameSave", MODE_PRIVATE);
        String savedText1 = NameSave.getString(SAVED_TEXT, "");
        editText.setText(savedText1);

        PasswordSave = getSharedPreferences("PasswordSave", MODE_PRIVATE);
        String savedText2 = PasswordSave.getString(SAVED_TEXT, "");
        editText2.setText(savedText2);

    }


}