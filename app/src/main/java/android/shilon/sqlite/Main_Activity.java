package android.shilon.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main_Activity extends AppCompatActivity
{
    private MyDatabaseHelper myDatabaseHelper;
    private StringBuffer line;
    //建立变量

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        myDatabaseHelper=new MyDatabaseHelper(this,"Cartoon.db",null,1);
        //传参
        Button createDatabase=findViewById(R.id.create_database);
        Button addData=findViewById(R.id.add_data);
        Button updateData=findViewById(R.id.update_data);
        Button deleteData=findViewById(R.id.delete_data);
        Button queryData=findViewById(R.id.query_data);
        //获取实例
        createDatabase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDatabaseHelper.getWritableDatabase();
                //创建数据库
            }
        });
        //点击监听
        addData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
                //获取对象
                ContentValues values=new ContentValues();
                //对要添加的数据进行组装
                values.put("name","TOUCH");
                values.put("year",1985);
                db.insert("Cartoon",null,values);
                //插入第一条数据
                values.clear();
                //清除
                values.put("name","Captain Tsubasa");
                values.put("year",1983);
                db.insert("Cartoon",null,values);
                //插入第二条数据
                Toast.makeText(Main_Activity.this,"添加数据成功！",Toast.LENGTH_SHORT).show();
                //提示信息
            }
        });
        //点击监听
        updateData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
                //获取对象
                ContentValues values=new ContentValues();
                //构建对象
                values.put("year",2018);
                db.update("Cartoon",values,"name=?",new String[]{"Captain Tsubasa"});
                //更新特定数据
                Toast.makeText(Main_Activity.this,"更新数据成功！",Toast.LENGTH_SHORT).show();
                //提示信息
            }
        });
        //点击监听
        deleteData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
                //获取对象
                db.delete("Cartoon","year>?",new String[]{"1985"});
                //删除特定数据
                Toast.makeText(Main_Activity.this,"删除数据成功！",Toast.LENGTH_SHORT).show();
                //提示信息
            }
        });
        //点击监听
        queryData.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
                //获取对象
                Cursor cursor=db.query("Cartoon",null,null,null,null,null,null);
                //查询表中所有数据
                line=new StringBuffer();
                //清空
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String year=cursor.getString(cursor.getColumnIndex("year"));
                        //取出数据
                        line.append("name is "+name+'\n'+"year is "+year+'\n');
                        //存储查询到的数据，以便显示
                    }while(cursor.moveToNext());
                }
                //遍历
                Toast.makeText(Main_Activity.this,line.toString(),Toast.LENGTH_SHORT).show();
                //显示查询到的数据
                cursor.close();
                //关闭
            }
        });
    }

    public class MyDatabaseHelper extends SQLiteOpenHelper
    {
        public static final String cartoon="create table Cartoon ("+"id integer primary key autoincrement,"+"name text,"+"year integer)";
        //建表元素
        private Context mContext;
        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version)
        {
            super(context, name, factory, version);
            mContext=context;
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(cartoon);
            //执行建表语句
            Toast.makeText(mContext,"创建成功！",Toast.LENGTH_SHORT).show();
            //提示信息
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}
        //升级数据库部分略
    }
    //构建实例
}
