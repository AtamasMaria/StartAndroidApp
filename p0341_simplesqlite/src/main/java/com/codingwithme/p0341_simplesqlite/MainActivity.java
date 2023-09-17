package com.codingwithme.p0341_simplesqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {
    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View view) {
        ContentValues cv = new ContentValues();

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (view.getId() == R.id.btnAdd) {
            Log.d(LOG_TAG, "--- Insert in mytable: ---");

            cv.put("name", name);
            cv.put("email", email);

            long rowId = db.insert("mytable", null, cv);
            Log.d(LOG_TAG, "row inserted, ID = " + rowId);
        } else if (view.getId() == R.id.btnRead) {
            Log.d(LOG_TAG, "--- Rows in mytable: ---");
            Cursor c = db.query("mytable", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex("id");
                int nameColIndex = c.getColumnIndex("name");
                int emailColIndex = c.getColumnIndex("email");

                do {
                    Log.d(LOG_TAG,
                            "ID = " + c.getInt(idColIndex) +
                                    ", name = " + c.getString(nameColIndex) +
                                    ", email = " + c.getString(emailColIndex));
                } while (c.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
                c.close();
            }
        } else if (view.getId() == R.id.btnClear) {
            Log.d(LOG_TAG, "--- Clear mytable: ---");
            int clearCount = db.delete("mytable", null, null);
            Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        } else if (view.getId() == R.id.btnUpd) {
            if (!id.equalsIgnoreCase("")) {
                Log.d(LOG_TAG, "--- Update mytable: ---");

                cv.put("name", name);
                cv.put("email", email);

                int updCount = db.update("mytable", cv, "id = ?",
                        new String[]{id});
                Log.d(LOG_TAG, "updated rows count = " + updCount);
            }
        } else if (view.getId() == R.id.btnDel) {
            if (!id.equalsIgnoreCase("")) {
                Log.d(LOG_TAG, "--- Delete from mytable: ---");

                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "deleted rows count = " + delCount);

            }
        }
        db.close();
    }
}
            