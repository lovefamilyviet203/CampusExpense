package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ExpenseDB.db";
    /* Inner class that defines the table contents */
    public static class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_EXPENSENAME = "name";
        public static final String COLUMN_NAME_EXPENSEDATE = "expenseDate";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_TYPE = "type";

    }
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";

        public static ContentValues createUserEntry(String username, String email, String password) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_USERNAME, username);
            values.put(COLUMN_NAME_EMAIL, email);
            values.put(COLUMN_NAME_PASSWORD, password);
            return values;
        }
    }
    private static final String SQL_CREATE_ENTRIES =
            " CREATE TABLE IF NOT EXISTS " + ExpenseEntry.TABLE_NAME + " (" +
                    ExpenseEntry._ID + " INTEGER PRIMARY KEY," +
                    ExpenseEntry.COLUMN_NAME_EXPENSENAME + " TEXT," +
                    ExpenseEntry.COLUMN_NAME_EXPENSEDATE + " TEXT," +
                    ExpenseEntry.COLUMN_NAME_AMOUNT + " TEXT," +
                    ExpenseEntry.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    UserEntry.COLUMN_NAME_PASSWORD + " TEXT)";
    private static final String SQL_DELETE_EXPENSE_TABLE =
            "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;

    private static final String SQL_DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES =
            SQL_DELETE_EXPENSE_TABLE + SQL_DELETE_USER_TABLE;
//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;
    private SQLiteDatabase database;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_USER_ENTRIES);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public long insertExpense(ExpenseEntity expense){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ExpenseEntry.COLUMN_NAME_EXPENSENAME, expense.expenseName);
        values.put(ExpenseEntry.COLUMN_NAME_EXPENSEDATE, expense.expenseDate.toString());
        values.put(ExpenseEntry.COLUMN_NAME_AMOUNT, expense.amount);
        values.put(ExpenseEntry.COLUMN_NAME_TYPE, expense.expenseType);

        // Insert the new row, returning the primary key value of the new row
        return database.insertOrThrow(ExpenseEntry.TABLE_NAME, null, values);
        // long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }
    public long insertUser(UserEntity user) {
        ContentValues values = UserEntry.createUserEntry(user.username, user.email, user.password);
        return database.insertOrThrow(UserEntry.TABLE_NAME, null, values);
    }
    public List<ExpenseEntity> getAllExpenses() {
        Cursor results = database.query(ExpenseEntry.TABLE_NAME, new String[] {ExpenseEntry._ID,ExpenseEntry.COLUMN_NAME_EXPENSENAME,ExpenseEntry.COLUMN_NAME_AMOUNT,ExpenseEntry.COLUMN_NAME_TYPE, ExpenseEntry.COLUMN_NAME_EXPENSEDATE},
                null, null, null, null, ExpenseEntry.COLUMN_NAME_EXPENSEDATE);

        results.moveToFirst();
        List<ExpenseEntity> expenseEntityList = new ArrayList<>();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            String amount = results.getString(2);
            String type = results.getString(3);
            String date = results.getString(4);
            ExpenseEntity expense = new ExpenseEntity();
            expense.Id = id;
            expense.expenseName = name;
            expense.amount = amount;
            expense.expenseType = type;
            expense.expenseDate = date;
            expenseEntityList.add(expense);
            results.moveToNext();
        }

        return expenseEntityList;

    }
    public UserEntity checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {UserEntry.COLUMN_NAME_USERNAME};
        String selection = UserEntry.COLUMN_NAME_EMAIL + " = ?" + " AND " + UserEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(UserEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        UserEntity userEntity = null;
        if (cursor.moveToFirst()) {
            int usernameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_NAME_USERNAME);
            if (usernameColumnIndex != -1) {
                String username = cursor.getString(usernameColumnIndex);
                userEntity = new UserEntity(username, email, password);
            }
        }

        cursor.close();
        return userEntity;
    }
    public boolean checkUserEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {UserEntry.COLUMN_NAME_EMAIL};
        String selection = UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(UserEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {UserEntry.COLUMN_NAME_EMAIL};
        String selection = UserEntry.COLUMN_NAME_EMAIL + " = ?" + " AND " +
                UserEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(UserEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public String getUsername(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {UserEntry.COLUMN_NAME_USERNAME};
        String selection = UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(UserEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        String username = null;
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_NAME_USERNAME));
        }

        cursor.close();
        return username;
    }
}
