////////////////영화 정보를 적는 앱////////////////////
package kr.co.company.newworld;
//여러 import들
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
//클래스 정의
public class MainActivity extends AppCompatActivity {
    //변수 설정
    private ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //activity_main.xml과 연결

        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllMovies();

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setAdapter(mAdapter);
        //클릭 리스터 함수 정의
        myListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int id = arg2 + 1;  //번호의 숫자 증가
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                //새로운 인텐트로 DisplayMovie 클래스를 띄운다.
                Intent intent = new Intent(getApplicationContext(), kr.co.company.newworld.DisplayMovie.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }
    //onResume 함수
    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getAllMovies());
        mAdapter.notifyDataSetChanged();
    }
    //버튼 클릭 함수
    public void onClick(View target) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        Intent intent = new Intent(getApplicationContext(), DisplayMovie.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
