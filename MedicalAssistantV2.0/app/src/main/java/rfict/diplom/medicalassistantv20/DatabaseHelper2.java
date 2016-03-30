package rfict.diplom.medicalassistantv20;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.app.Activity;
import android.content.ContentValues;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME2 = "userstore1.db"; // название бд
    private static final int SCHEMA2 = 1; // версия базы данных
    static final String TABLE2 = "users1"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID2 = "_id";
    public static final String COLUMN_NAME2 = "name";
    public static final String COLUMN_YEAR2 = "year";

    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME2, null, SCHEMA2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users1 (" + COLUMN_ID2
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME2
                + " TEXT, " + COLUMN_YEAR2 + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE2 +" (" + COLUMN_NAME2
                + ", " + COLUMN_YEAR2  + ") VALUES ('Орешsdasdsaко Станислав', 1994) , ('Шмарловсsaddasdsкий Кирилл', 1993) , ('Урбанasddsadsaвич Андрей', 1993) , ('Соasddsadsaболь Денис', 1993), ('Редько Галина', 1993), ('Пашкевич Юлия', 1993), ('Ореdasdasшко Дилана', 1994), ('Сурadsadsхов Азим', 1996) ;");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        onCreate(db);
    }
}