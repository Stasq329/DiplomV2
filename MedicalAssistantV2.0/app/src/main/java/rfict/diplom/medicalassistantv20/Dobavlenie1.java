package rfict.diplom.medicalassistantv20;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class Dobavlenie1 extends ActionBarActivity {
    public static enum TransitionType {SlideLeft}
    public static TransitionType transitionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        long userId=0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(userId))
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        EditText nameBox;
        EditText yearBox;

        Button saveButton;

        DatabaseHelper sqlHelper;
        SQLiteDatabase db;
        Cursor userCursor;

        public static PlaceholderFragment newInstance(long id) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args=new Bundle();
            args.putLong("id", id);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);

            sqlHelper = new DatabaseHelper(getActivity());
        }
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user, container, false);
            nameBox = (EditText) rootView.findViewById(R.id.name);
            yearBox = (EditText) rootView.findViewById(R.id.year);

            saveButton = (Button) rootView.findViewById(R.id.save);

            final long id = getArguments() != null ? getArguments().getLong("id") : 0;

            db = sqlHelper.getWritableDatabase();
            // кнопка удаления


            // кнопка сохранения
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());
                    cv.put(DatabaseHelper.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));

                    if (id > 0) {
                        db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(id), null);
                    } else {
                        db.insert(DatabaseHelper.TABLE, null, cv);
                    }
                    goHome();
                }
            });

            // если 0, то добавление
            if (id > 0) {
                // получаем элемент по id из бд
                userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                        DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
                userCursor.moveToFirst();
                nameBox.setText(userCursor.getString(1));
                yearBox.setText(String.valueOf(userCursor.getInt(2)));
                userCursor.close();
            }
            return rootView;
        }

        public void goHome(){
            // закрываем подключение
            db.close();
            // переход к главной activity
            Intent intent = new Intent(getActivity(), Pacient2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
    }
}