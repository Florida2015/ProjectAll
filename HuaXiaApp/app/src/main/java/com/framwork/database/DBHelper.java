package com.framwork.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *它只是个工具类，真正的创建数据库或表只有调用了构造方法和onCreate（）方法才真正的创建了
 * 创建数据库
 * Created by houwen.lai on 2015/1/18.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static DBHelper dbHelper = null;

    private static final String DATABASE_NAME = "huaxia.db";//数据库的名字
    private static final int DATABASE_VERSION = 1;//数据库的版本号

    /**
     * 获得对象
     * @param context
     * @return
     */
    public synchronized static DBHelper getInstance(Context context) {
        return dbHelper == null ? new DBHelper(context) : dbHelper;
    }


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);//创建数据库
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 创建表
     */
    public static final String TABLE_SEARCH_KEYWORLD = "search_keyworld";//表的名字
    public class SearchInfo implements CommonColumns {
        public static final String CREATE_TIME = "create_time";
        public static final String KEYWORD = "keyword";

    }
    interface CommonColumns {
        /**
         * The unique ID for a row.
         * <P>
         * Type: INTEGER (long)
         * </P>
         */
        public static final String _ID = "_id";
    }


/*
	 * sqlite3支持的数据类型： NULL、INTEGER、REAL、TEXT、BLOB 但是，sqlite3也支持如下的数据类型 smallint
	 * 16位整数 integer 32位整数 decimal(p,s) p是精确值,s是小数位数 float 32位实数 double 64位实数
	 * char(n) n长度字符串，不能超过254 varchar(n) 长度不固定最大字符串长度为n，n不超过4000 graphic(n) 和
	 * char(n) 一样，但是单位是两个字符double-bytes，n不超过127(中文字) vargraphic(n) 可变长度且最大长度为n
	 * date 包含了年份、月份、日期 time 包含了小时、分钟、秒 timestamp 包含了年、月、日、时、分、秒、千分之一秒
	 */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 通过SQLiteDatabase对数据表进行创建
        // 直接通过db执行sqlite语句
        // 确定表名是stu，并且表不存在（if not exist）的时候才会创建
        // 字段名id int类型的并且是主键自增长（主键就是数据唯一的标识符，不能修改）
        // 字段名name String类型并且不能null
        //创建表
        //1.表 search_keyworld
        db.execSQL("CREATE TABLE if not exists " + TABLE_SEARCH_KEYWORLD + "(" + SearchInfo._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + SearchInfo.KEYWORD + " TEXT," + SearchInfo.CREATE_TIME
                + " TEXT" + ");");

        //2
        // 产品排行 表
        String sq5 = "create table if not exists producttable("
                + "Id integer primary key autoincrement,"
                + "proId varchar(10)," // 详情id
                + "prourl varchar(50)," // 产品头像
                + "companyname varchar(30)," // 公司名字
                + "proname varchar(30)," // 产品名字
                + "protext varchar(50)," // 产品介绍
                + ")";
        db.execSQL(sq5);

        //3.
        db.execSQL(CREATE_BOOK);

    }
    private String CREATE_BOOK = "create table book(bookId integer primarykey,bookName text,bookAuthor text);";
    private String CREATE_TEMP_BOOK = "alter table book rename to _temp_book";
    private String INSERT_DATA = "insert into book select *,'' from _temp_book";//"''"增加字段默认值
    private String DROP_BOOK = "drop table _temp_book";
    /**
     * 当app版本升级，添加了表的字段时，需要在这里说明，并修改
     * 数据库更新并保留原来数据的实现
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            db.execSQL(CREATE_TEMP_BOOK);//创建临时表
            db.execSQL(CREATE_BOOK);//创建表 增加 bookAuthor 字段
            db.execSQL(INSERT_DATA);//向表中中插入临时表中的数据
            db.execSQL(DROP_BOOK);//删除临时表
        }
    }

    /**
     * 删除数据库
     */

    public boolean DeleteDatabase(Context context){
        return context.deleteDatabase(DATABASE_NAME);
    }



}
