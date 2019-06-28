package com.toplogis.guess

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val count = intent.getIntExtra("COUNTER", -1)
        bt_count.text = "猜數字次數： $count"

        bt_save.setOnClickListener { view ->
            val name = ed_name.text.toString()
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNT", count)
                .putString("REC_NAME", name)
                .apply()
            val intent = Intent()
            intent.putExtra("NAME", name)
            setResult(Activity.RESULT_OK, intent)
            finish()
         }
    }
}
