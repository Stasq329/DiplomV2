package rfict.diplom.medicalassistantv20;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.app.Activity;
import android.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.FilterQueryProvider;
import android.net.Uri;
import android.provider.MediaStore;


public class Pacient2 extends ActionBarActivity implements View.OnClickListener {
    public static enum TransitionType {SlideLeft}
    public static TransitionType transitionType;
    ListView mList;
    TextView header;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    EditText userFilter;
    SimpleCursorAdapter userAdapter;
    Button btn3;
    Button btn4;
    final static int RQS_RECORDING = 1;

    Uri savedUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        userFilter = (EditText)findViewById(R.id.userFilter);
        header = (TextView)findViewById(R.id.header);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(this);


        btn3.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent =
                        new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, RQS_RECORDING);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }});





        mList = (ListView)findViewById(R.id.list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Karta.class);
                intent.putExtra("id", id);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

            }
        });
        sqlHelper = new DatabaseHelper(getApplicationContext());



    }





    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = sqlHelper.getReadableDatabase();

        //получаем данные из бд
        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText("Найдено элементов: " + String.valueOf(userCursor.getCount()));
        mList.setAdapter(userAdapter);


        userFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // при изменении текста выполняем фильтрацию
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userAdapter.getFilter().filter(s.toString());
            }
        });
// устанавливаем провайдер фильтрации
        userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {

                if (constraint == null || constraint.length() == 0) {

                    return db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
                } else {
                    return db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                            DatabaseHelper.COLUMN_NAME + " like ?", new String[]{"%" + constraint.toString() + "%"});
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
        db.close();
        userCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            case R.id.action_settings2:
                intent = new Intent(getApplicationContext(), Dobavlenie1.class);
                startActivity(intent);
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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn4:
                intent = new Intent(this, Recognition.class);
                startActivity(intent);
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
        }
    }
}