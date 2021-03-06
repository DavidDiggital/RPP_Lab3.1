package com.example.rpp_lab_31

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private var sqlHelper: DatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqlHelper = DatabaseHelper(applicationContext)
        db = sqlHelper!!.getWritableDatabase()
        onUpdate()
    }

    private fun onUpdate() {
        cursor = db!!.rawQuery("select " + DatabaseHelper.COLUMN_ID.toString() + " from " + DatabaseHelper.TABLE_NAME, null)
        val idList = ArrayList<String>()
        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor!!.getString(0)
                idList.add(id)
            } while (cursor!!.moveToNext())
        }
        cursor!!.close()
        for (id in idList) {
            db!!.delete(DatabaseHelper.TABLE_NAME, "_id = ?", arrayOf(id))
        }

        sqlHelper!!.insertData(db!!)
    }

    fun onClickShow(view: View?) {
        val intent = Intent(this, StudentList::class.java)
        startActivity(intent)
    }

    fun onClickAdd(view: View?) {
        val values = ContentValues()
        values.put(DatabaseHelper.COLUMN_FULL_NAME, sqlHelper!!.getName(db!!))
        db!!.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    fun onClickChange(view: View?) {
        if(DatabaseUtils.queryNumEntries(db, DatabaseHelper.TABLE_NAME) > 0) {
            val values = ContentValues()
            values.put(DatabaseHelper.COLUMN_FULL_NAME, "Иванов Иван Иванович")
            cursor = db!!.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME
                    .toString() + " WHERE " + DatabaseHelper.COLUMN_ID.toString() + " = (SELECT MAX("
                        + DatabaseHelper.COLUMN_ID.toString() + ") FROM " + DatabaseHelper.TABLE_NAME.toString() + ");",
                null
            )
            cursor!!.moveToFirst()
            val id = cursor!!.getString(0)
            db!!.update(DatabaseHelper.TABLE_NAME, values, "_id = ?", arrayOf(id))
            cursor!!.close()
        }
        else
            Toast.makeText(this, "Для проведения операции в списке" + "\n" + "должен быть хотя бы один элемент", Toast.LENGTH_LONG).show()
    }

    fun onClickClear(view: View?) {
            db!!.execSQL("DELETE FROM " + DatabaseHelper.TABLE_NAME + ";")
    }
}
