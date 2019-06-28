package com.toplogis.guess

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECODE = 100
    private val TAG: String = MaterialActivity::class.java.simpleName
    var secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
        Log.d(TAG, "Secret: ${secretNumber.secret}")

        fab_reset.setOnClickListener { view ->
            replay()
        }

        tv_count.text = secretNumber.count.toString()

    }

    private fun replay() {
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

    @SuppressLint("StringFormatInvalid")
    fun check(v: View) {
        val number = ed_number.text.toString()
        if (number.trim().isNotEmpty()) {
            val diff = secretNumber.validate(number.toInt())
            val msg = when {
                diff > 0 -> getString(R.string.bigger)
                diff < 0 -> getString(R.string.smaller)
                diff == 0 && secretNumber.count < 3 -> getString(R.string.excellent, secretNumber.secret)
                else -> getString(R.string.you_got_it)}

            tv_count.text = secretNumber.count.toString()

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alert_title))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    if(diff == 0) {
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("COUNTER", secretNumber.count)
                        startActivityForResult(intent, REQUEST_RECODE)
                    }
                }
                .setMessage(msg)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECODE) {
            if ( resultCode == Activity.RESULT_OK) {
                Log.d("name", data?.getStringExtra("NAME"))
                replay()
            }
        }
    }
}
