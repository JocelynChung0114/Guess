package com.toplogis.guess

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val TAG = MaterialActivity::class.java.simpleName
    var secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
        Log.d(TAG, "Secret: ${secretNumber.secret}")

        fab.setOnClickListener { view ->
            if (secretNumber.count > 0) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.replay_game))
                    .setMessage(getString(R.string.are_you_sure))
                    .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                        secretNumber.reset()
                        tv_count.text = secretNumber.count.toString()
                        ed_number.setText("")
                    }
                    .setNeutralButton(getString(R.string.cancel), null)
                    .show()
            }
        }

        tv_count.text = secretNumber.count.toString()
    }

    @SuppressLint("StringFormatInvalid")
    fun check(v: View) {
        val number = ed_number.text.toString()
        if (number.trim().isNotEmpty()) {
            val diff = secretNumber.validate(number.toInt())
            var msg = getString(R.string.you_got_it)

            if (diff > 0) {
                msg = getString(R.string.bigger)
            } else if (diff < 0) {
                msg = getString(R.string.smaller)
            } else {
                if (secretNumber.count < 3) {
                    msg = getString(R.string.excellent, secretNumber.secret)
                    secretNumber.reset()
                    ed_number.setText("")
                }
            }

            tv_count.text = secretNumber.count.toString()

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_title))
                .setPositiveButton(getString(R.string.ok), null)
                .setMessage(msg)
                .show()
        }

    }

}
