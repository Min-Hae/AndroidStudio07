package kr.co.company.newworld;
//여러 import들
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
//클래스 정의
public class DBHelper extends SQLiteOpenHelper {
    //변수 설정
    public static final String DATABASE_NAME = "MyMovies.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_NAME = "name";
    public static final String MOVIES_COLUMN_DIRECTOR = "director";
    public static final String MOVIES_COLUMN_YEAR = "year";
    public static final String MOVIES_COLUMN_NATION = "nation";
    public static final String MOVIES_COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL문장을 실행하기 위해
        db.execSQL("create table movies " + "(id integer primary key,name text, director text, year text, nation text, rating text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");//SQL문장을 실행하기 위해
        onCreate(db);
    }
    //영화를 추가하는 함수
    public boolean insertMovie(String name, String director, String year, String nation, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //정보들 입력
        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);

        db.insert("movies", null, contentValues);
        return true;
    }
    //커서를 이용해 데이타를 얻는다.
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies where id=" + id + "", null);
        return res;
    }
    //영화 정보를 수정하는 함수
    public boolean updateMovie(Integer id, String name, String director, String year, String nation, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //정보들 입력
        contentValues.put("name", name);
        contentValues.put("director", director);
        contentValues.put("year", year);
        contentValues.put("nation", nation);
        contentValues.put("rating", rating);
        db.update("movies", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }
    //영화를 삭제하는 함수
    public Integer deleteMovie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies", "id = ? ", new String[]{Integer.toString(id)});
    }
    //arrayList 함수
    public ArrayList getAllMovies() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {  //while문을 이용해 번호와 영화 제목으로 추가
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_ID))+" "+res.getString(res.getColumnIndex(MOVIES_COLUMN_NAME)));
            res.moveToNext(); //다음으로 이동
        }
        return array_list;
    }
}
