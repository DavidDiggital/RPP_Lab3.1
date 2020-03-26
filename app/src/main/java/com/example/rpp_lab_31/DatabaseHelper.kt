package com.example.rpp_lab_31

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        SCHEMA
    ) {
    private var studentsList: ArrayList<String>? = null
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FULL_NAME + " TEXT, " +
                    COLUMN_TIME_TO_ADD + " DATETIME DEFAULT CURRENT_TIME" +
                    ");"
        )
        insertData(db)
    }

    fun insertData(db: SQLiteDatabase) {
        val random = Random()
        val randomNumbers = ArrayList<Int>()
        var pos: Int
        while (randomNumbers.size != 5) {
            pos = random.nextInt(studentsList!!.size)
            if (randomNumbers.indexOf(pos) == -1) {
                randomNumbers.add(pos)
            }
        }
        val values = ContentValues()
        for (i in randomNumbers) {
            val name = studentsList!![i]
            values.put(COLUMN_FULL_NAME, name)
            db.insert(TABLE_NAME, null, values)
        }
    }

    fun getName(db: SQLiteDatabase): String {
        val cursor = db.rawQuery(
            "SELECT $COLUMN_FULL_NAME FROM $TABLE_NAME;",
            null
        )
        val names = ArrayList<String>()
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0)
                names.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        for (name in studentsList!!) {
            if (names.indexOf(name) == -1) {
                return name
            }
        }
        return studentsList!![0]
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun deleteStudent(db: SQLiteDatabase, studentId: String) {
        db.delete(TABLE_NAME, "_id ?", arrayOf(studentId))
    }

    private fun fillArrayList() {
        studentsList = ArrayList()
        studentsList!!.add("Алексеев Никита Евгеньевич")
        studentsList!!.add("Баранников Вадим Сергеевич")
        studentsList!!.add("Булыгин Андрей Генадьевич")
        studentsList!!.add("Геденидзе Давид Темуриевич")
        studentsList!!.add("Горак Никита Сергеевич")
        studentsList!!.add("Грачев Александр Альбертович")
        studentsList!!.add("Гусейнов Илья Алексеевич")
        studentsList!!.add("Жарикова Екатерина Сергеевна")
        studentsList!!.add("Журавлев Владимир Евгеньевич")
        studentsList!!.add("Загребельный Александр Русланович")
        studentsList!!.add("Иванов Алексей Дмитриевич")
        studentsList!!.add("Карипова Лейсан Вильевна")
        studentsList!!.add("Копотов Михаил Алексеевич")
        studentsList!!.add("Копташкина Татьяна Алексеевна")
        studentsList!!.add("Косогоров Кирилл Станиславович")
        studentsList!!.add("Кошкин Артём Сергеевич")
        studentsList!!.add("Логецкая Светлана Александровна")
        studentsList!!.add("Магомедов Мурад Магамедович")
        studentsList!!.add("Миночкин Антон Андреевич")
        studentsList!!.add("Опарин Иван Алексеевич")
        studentsList!!.add("Паршаков Никита Алексеевич")
        studentsList!!.add("Самохин Олег Романович")
        studentsList!!.add("Сахаров Владислав Игоревич")
        studentsList!!.add("Смирнов Сергей Юрьевич")
        studentsList!!.add("Трошин Дмитрий Вадимович")
        studentsList!!.add("Чехуров Денис Александрович")
        studentsList!!.add("Эльшейх Самья Ахмед")
        studentsList!!.add("Юров Илья Игоревич")
    }

    companion object {
        private const val DATABASE_NAME = "students.db"
        const val TABLE_NAME = "student"
        private const val SCHEMA = 1
        const val COLUMN_ID = "_id"
        const val COLUMN_FULL_NAME = "name"
        const val COLUMN_TIME_TO_ADD = "time"
    }

    init {
        fillArrayList()
    }
}