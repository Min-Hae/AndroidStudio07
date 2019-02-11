package kr.co.company.newworld;
//여러 import들
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//클래스 정의
public class DisplayMovie extends AppCompatActivity {
    //변수 설정
    private DBHelper mydb;
    TextView name;
    TextView director;
    TextView year;
    TextView nation;
    TextView rating;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaymovie);  //displaymovie.xml과 연결
        //xml에 있는 EditText들과 연결
        name = (TextView) findViewById(R.id.editTextName);
        director = (TextView) findViewById(R.id.editTextDirector);
        year = (TextView) findViewById(R.id.editTextYear);
        nation = (TextView) findViewById(R.id.editTextNation);
        rating = (TextView) findViewById(R.id.editTextRating);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {     //extras가 비어있지않으면 실행
            int v = extras.getInt("id");
            if (v > 0) {
                Cursor rs = mydb.getData(v);
                id = v;
                rs.moveToFirst();
                //정보들을 문자열로 저장한다.
                String n = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_NAME));
                String d = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_DIRECTOR));
                String y = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_YEAR));
                String na = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_NATION));
                String r = rs.getString(rs.getColumnIndex(DBHelper.MOVIES_COLUMN_RATING));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1); //버튼 설정
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) n);
                director.setText((CharSequence) d);
                year.setText((CharSequence) y);
                nation.setText((CharSequence) na);
                rating.setText((CharSequence) r);
            }
        }
    }
    //추가하는 함수
    public void insert(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) { //extras가 비어있지 않으면 실행
            int Value = extras.getInt("id");
            if (Value > 0) { //수정 되었을 때 토스트 메시지 출력
                if (mydb.updateMovie(id, name.getText().toString(), director.getText().toString(), year.getText().toString(), nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), kr.co.company.newworld.MainActivity.class);
                    startActivity(intent);
                } else {  //수정되지 않았을 때 토스트 메시지 출력
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            } else {  //추가 되었을 때 토스트 메시지 출력
                if (mydb.insertMovie(name.getText().toString(), director.getText().toString(), year.getText().toString(), nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
                } else { //추가 되지 않았을 때 토스트 메시지 출력
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }
    //삭제하는 함수
    public void delete(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) { //extras가 비어있지 않으면 실행
            int Value = extras.getInt("id");
            if (Value > 0) { //삭제 되었을 때 토스트 메시지 출력
                mydb.deleteMovie(id);
                Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_SHORT).show();
                finish();
            } else {  //삭제 되지 않았을 때 토스트 메시지 출력
                Toast.makeText(getApplicationContext(), "삭제되지 않았음", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void edit(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            if (value > 0) {
                if (mydb.updateMovie(id, name.getText().toString(), director.getText().toString(), year.getText().toString(), nation.getText().toString(), rating.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

