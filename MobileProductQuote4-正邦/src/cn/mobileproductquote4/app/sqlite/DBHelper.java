package cn.mobileproductquote4.app.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import junit.runner.Version;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cn.mobileproductquote4.app.main.MyApplication;

/**
 * 数据库管理
 * 
 * @author Administrator
 * 
 */
@SuppressLint("SdCardPath")
public class DBHelper extends SQLiteOpenHelper {
	/* 默认字段类型 */
	public final static String datatype = "varchar(255)";
	/* db */
	private static DBHelper db;

	/* url */
	private final static String URL = "/data/data/cn.ytf.merchants.app/databases/";
	/* db地址 */
	private final static String DB_FILE_NAME = "ytfsqlite.db";

	/* 获得实例 */
	public static synchronized DBHelper getInstance(Context context) {
		if (db == null) {
			copyDB(context);
			db = new DBHelper(context);
		}
		return db;
	}

	public DBHelper(Context context){
		super(context, DB_FILE_NAME, null,1);
	}

	/* 拷贝 */
	private static synchronized void copyDB(Context context) {
		boolean isHava = checkDataBase();
		if (!isHava) {
			try {
				File dired = new File(URL);
				if (!dired.exists()) {
					dired.mkdirs();
				}
				File file = new File(URL, DB_FILE_NAME);
				if (file.exists()) {
					file.delete();
				}
				copyBigDataBase();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	private static void copyBigDataBase() throws IOException {
		InputStream myInput;
		String outFileName = URL + DB_FILE_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);

		myInput = MyApplication.getAppContext().getAssets().open(DB_FILE_NAME);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myInput.close();

		myOutput.close();

	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

	}

	/* 数据库升级 */
	public void OnUpgrade(int version) {
		//creatable(getSdb(false), "ccc", "id", new String[] { "c1", "c2", "c3" });
	}

	public synchronized Cursor query(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit) {
		SQLiteDatabase sdb = getSdb(true);
		Cursor cs = sdb.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);

		return cs;

	}

	public synchronized SQLiteDatabase getSdb(boolean isRead) {
		SQLiteDatabase adb = null;
		try {
			if (isRead) {
				adb = getReadableDatabase();
			} else {
				adb = getWritableDatabase();
			}
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}
		return adb;

	}

	/**
	 * 
	 * 插入
	 * 
	 * @param key
	 * @param date
	 */
	public synchronized long insert(String tablename, String key[],
			String date[]) {
		SQLiteDatabase sdb = getSdb(false);
		ContentValues values = new ContentValues();
		for (int i = 0; i < key.length; i++) {
			values.put(key[i], date[i]);
		}
		long id = -1;
		id = sdb.insert(tablename, null, values);
		sdb.close();
		return id;

	}

	/* 更新 */
	public synchronized void update(String tablename, String key[],
			String date[], String where, String whereData[]) {
		try {
			SQLiteDatabase sdb = getSdb(false);

			ContentValues values = new ContentValues();
			for (int i = 0; i < key.length; i++) {
				values.put(key[i], date[i]);
			}
			sdb.update(tablename, values, where, whereData);
			sdb.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/* 数量 */
	public synchronized long getCount(String tableName, String[] where,
			String[] whereData) {
		String sql = "select count(*) from " + tableName;

		if (where != null) {
			StringBuffer sqlWhereData = new StringBuffer();
			for (int i = 0; i < where.length; i++) {
				String wh = where[i];
				String whd = whereData[i];
				sqlWhereData.append(wh);
				sqlWhereData.append("=");
				sqlWhereData.append(whd);
				if (i != where.length - 1) {
					sqlWhereData.append(" ");
					sqlWhereData.append("AND");
					sqlWhereData.append(" ");
				}

			}
			sql = "select count(*) from " + tableName + " " + "where" + " "
					+ sqlWhereData.toString();
		}

		SQLiteDatabase sdb = getSdb(false);

		Cursor c = sdb.rawQuery(sql, null);
		c.moveToFirst();
		long length = c.getLong(0);
		c.close();
		sdb.close();
		return length;
	}

	public synchronized Cursor getAllItems(int firstResult, int maxResult,
			String tableName) {
		SQLiteDatabase sdb = getSdb(false);
		String sql = "select * from " + tableName + " limit ?,?";
		Cursor mCursor = sdb.rawQuery(
				sql,
				new String[] { String.valueOf(firstResult),
						String.valueOf(maxResult) });
		sdb.close();
		return mCursor;
	}

	public synchronized static boolean closeCursor(Cursor c) {
		if (c != null) {
			c.close();
			return true;
		}
		return false;
	}

	/**
	 * @param tableName
	 * @param where
	 * @param whereData
	 */
	public synchronized int delect(String tableName, String where,
			String[] whereData) {
		int count = 0;
		SQLiteDatabase sdb = getSdb(true);
		count = sdb.delete(tableName, where, whereData);
		sdb.close();
		return count;
	}

	/**
	 * 
	 * @return
	 */
	private static boolean checkDataBase() {
		SQLiteDatabase checkDB = getDataBase();

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private static SQLiteDatabase getDataBase() {
		SQLiteDatabase checkDB = null;
		String myPath = URL + DB_FILE_NAME;
		try {
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception e) {
			Log.e("DBHelper", "database does't exist yet" + myPath);
		}
		return checkDB;
	}

	/**
	 * 
	 * @param db
	 * @param table
	 *            表
	 * @param id
	 *            主键
	 * @param key
	 *            字段
	 */
	public void creatable(SQLiteDatabase sdb, String table, String id,
			String[] key) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(table);

		sql.append("(");
		sql.append(String.valueOf(id));
		sql.append(" ");
		sql.append("integer primary key autoincrement");
		sql.append(",");
		for (int i = 0; i < key.length; i++) {
			sql.append(key[i]);
			sql.append(" ");
			sql.append("varchar(255)");

			if (i != key.length - 1) {
				sql.append(",");
			}
		}
		sql.append(")");

		sdb.execSQL(sql.toString());
		sdb.close();
	}

	/**
	 * 向表中加入字段
	 * 
	 * @param tableName
	 *            表名
	 * @param colName
	 *            例名
	 * @param datatype
	 *            数据类型
	 */
	public void addColumn(SQLiteDatabase sdb, String tableName, String colName,
			String datatype) {
		StringBuffer sql = new StringBuffer();
		sql.append("ALTER TABLE ");
		sql.append(tableName);

		sql.append(" ADD ");
		sql.append(colName);
		sql.append(" ");
		sql.append(datatype);

		sdb.execSQL(sql.toString());
		sdb.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
