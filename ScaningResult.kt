package com.example.qr_coder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel

class ScaningResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scaning_result)

        lateinit var textView3: TextView
        lateinit var button: Button

        val extras = intent.extras
        if (extras != null) {
            val result = extras.getString("key")
            textView3.text = result.toString();}

        // Отправка результата сканирования на сервер
        Fuel.post("https://8c6a-62-113-109-24.eu.ngrok.io")
            .body("{'phone': '78000000029',\n" +
                    "    'accountid': 'c3b4627e-7f5f-435f-ac5f-dfe4dc74d33c',\n" +
                    "    'paymenttoken' : 'ef5c368f-06ad-41c5-9c13-63c92b49d96b',\n" +
                    "    'amount' : '350000'}")
            .also { println(it) }
            .response { result ->
                // Проверить на успешность передачи
                Toast.makeText(this, "Отправлено на обработку", Toast.LENGTH_LONG).show()
            }


        button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}