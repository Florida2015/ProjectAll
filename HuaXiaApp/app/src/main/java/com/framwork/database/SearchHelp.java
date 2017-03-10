package com.framwork.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对表 search_keyworld 进行增删改查的操作
 *
 * Created by houwen.lai on 2015/1/18.
 */
public class SearchHelp {

    private SQLiteDatabase db;
    private static final String table = DBHelper.TABLE_SEARCH_KEYWORLD;
    private Context mContext;
    private DBHelper database;

    public SearchHelp(Context mContext) {
        this.mContext = mContext;
        database = DBHelper.getInstance(mContext);
        db = database.getReadableDatabase();
    }

    /**
     * 增
     */
    public long insertKeyword(String time,String keyword){
        openDB();
        /* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.SearchInfo.KEYWORD, keyword);
        cv.put(DBHelper.SearchInfo.CREATE_TIME, String.valueOf(System.currentTimeMillis()));
        long row = db.insert(table, null, cv);
        closeDB();
        return row;
    }

    public void addKeyWord(String keyword) {
        openDB();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.getCount() >= 20) {
                if (cursor.moveToLast()) {
                    String _id = cursor.getString(0);
                    String whereClause = DBHelper.SearchInfo._ID + " = ?";
                    String[] whereArgs = new String[] { _id };
                    db.delete(table, whereClause, whereArgs);
            }
            }
        }

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.SearchInfo.KEYWORD, keyword);
        cv.put(DBHelper.SearchInfo.CREATE_TIME, String.valueOf(System.currentTimeMillis()));
        String whereClause= DBHelper.SearchInfo.KEYWORD+" = ?";
        String[] whereArgs=new String[]{keyword};
        if (db.query(table, null, whereClause, whereArgs, null, null, null).getCount()>0) {
            db.update(table, cv, whereClause, whereArgs);
        }else {
            db.insert(table, null, cv);
        }
        closeDB();
    }


    /**
     * 删
     *
     */
    public void deleteKeyword(){
        openDB();
        String _id ="10";//删除某行
        String whereClause = DBHelper.SearchInfo._ID + " = ?";
        String[] whereArgs = new String[] { _id };
        db.delete(table, whereClause, whereArgs);
        closeDB();
    }

    /**
     * 改
     */
    public void updateKeyword(String keyword){
        openDB();
        ContentValues cv = new ContentValues();//需要更新的数据
        cv.put(DBHelper.SearchInfo.KEYWORD, keyword);
        cv.put(DBHelper.SearchInfo.CREATE_TIME, String.valueOf(System.currentTimeMillis()));
        String whereClause= DBHelper.SearchInfo.KEYWORD+" = ?";//更新条件
        String[] whereArgs=new String[]{keyword};
        db.update(table, cv, whereClause, whereArgs);
        closeDB();
    }


    /**
     * 查
     */
    public ArrayList<SearchKeyWord> getKeyWordHistory() {
        openDB();
        //db.query(table, null, null, null, null, null, null);//查询所有数据
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        ArrayList<SearchKeyWord> searchKeyWords = new ArrayList<SearchKeyWord>();
        while (cursor.moveToNext()) {
            int index_id = cursor.getColumnIndexOrThrow(DBHelper.SearchInfo._ID);
            int index_keyword = cursor.getColumnIndexOrThrow(DBHelper.SearchInfo.KEYWORD);
            int index_create_time = cursor.getColumnIndexOrThrow(DBHelper.SearchInfo.CREATE_TIME);
            SearchKeyWord searchKeyWord = new SearchKeyWord();
            searchKeyWord.id = cursor.getString(index_id);
            searchKeyWord.keyWord = cursor.getString(index_keyword);
            searchKeyWord.createTime = cursor.getString(index_create_time);
            searchKeyWords.add(0,searchKeyWord);
        }
        closeDB();
        return searchKeyWords;
    }



    public Cursor getKeyWordHistoryC() {
        openDB();
        Cursor cursor = db.query(table, null, null, null, null, null, DBHelper.SearchInfo.CREATE_TIME+" desc");
        return cursor;
    }

    /**
     * execSQL 执行命令 进行操作
     *
     *
     */
    /**
     * 增加
     */
    public void insert(Map<String, String> map) {
        openDB();
        String sql = "insert into "+table+" (id,keyWord,createTime) values (?,?,?)";
        db.execSQL(sql, new Object[] {"","",""});
        closeDB();
    }

    //根据条件查询
    public List<Map<String, String>> queryById(String id) {
        openDB();
        List<Map<String, String>> bList = new ArrayList<Map<String, String>>();
        String sql = "select id,keyWord,createTime from "+table+" where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            String Id = cursor.getString(cursor.getColumnIndex("id"));
            String keyWord = cursor.getString(cursor.getColumnIndex("keyWord"));
            String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
            map.put("id", Id);
            map.put("keyWord", keyWord);
            map.put("createTime", createTime);
            bList.add(map);
        }
        closeDB();
        return bList;
    }
    //查询所有数据
    public List<Map<String, String>> queryAll() {
        openDB();
        List<Map<String, String>> bList = new ArrayList<Map<String, String>>();
        String sql = "select id,keyWord,createTime from "+table;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            String Id = cursor.getString(cursor.getColumnIndex("id"));
            String keyWord = cursor.getString(cursor.getColumnIndex("keyWord"));
            String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
            map.put("id", Id);
            map.put("keyWord", keyWord);
            map.put("createTime", createTime);
            bList.add(map);
        }
        closeDB();
        return bList;
    }
    //根据条件删除数据
    public void deletById(String iid) {
        openDB();
        String sql = "delete from "+table +"where id=?";//
        db.execSQL(sql,new Object[]{"id"});
        closeDB();
    }
    //删除表里的数据
    public void deletAll() {
        openDB();
        String sql = "delete table "+table;//
        db.execSQL(sql,null);
        closeDB();
    }
    /**
     * 删除表
     */
    public void deletTable() {
        openDB();
        String sql = "drop table "+table;//
        db.execSQL(sql,null);
        closeDB();
    }

    //修改数据
    public void UpdateById(Map<String, String> map, String id) {
        openDB();//id,content,time,send_user_name,send_user_photo
        String sql = "update "+table+" set id=?,keyWord=?,createTime=? where id=?";
        db.execSQL(sql,new Object[] {map.get("id"),map.get("keyWord"), map.get("createTime"), id });
        closeDB();
    }

    /**
     * execSQL 执行命令 进行操作
     *
     *
     */



    public void clearHistory() {
        openDB();
        db.delete(table, null, null);
        closeDB();
    }

    /**
     * getWritableDatabase 即可以写人也可以读取
     * getReadableDatabase 即可以写人也可以读取
     */
    public void openDB() {
        if (db != null) {
            if (!db.isOpen()) {
                database = DBHelper.getInstance(mContext);
                db = database.getReadableDatabase();
            }
        }
    }

    public void closeDB() {
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
        }
    }


    class SearchKeyWord {
        String id;
        String keyWord;
        String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "SearchKeyWord [id=" + id + ", keyWord=" + keyWord + ", createTime=" + createTime + "]";
        }
    }

}
