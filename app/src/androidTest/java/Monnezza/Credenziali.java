package Monnezza;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class Credenziali extends SQLiteOpenHelper implements  MyDatabaseHelper {

    private Context context;

    public Credenziali(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE credenziali(" +
                "email varchar(30) PRIMARY KEY,"+
                "password varchar(50)"+
                ")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS credenziali");
        onCreate(sqLiteDatabase);
    }

    public void addUser(String email, String pw) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email",email);
        cv.put("password",pw);
        long result = db.insert("credenziali",null,cv);
        if (result == 1) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            throw new Exception("failed");
        }
        else
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
    }
}
