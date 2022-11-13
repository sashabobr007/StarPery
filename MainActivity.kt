package com.example.qr_coder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel

class MainActivity : AppCompatActivity() {

    private lateinit var bBuyer: Button
    private lateinit var bSeller: Button
    private lateinit var editTextPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bBuyer = findViewById(R.id.bBuyer)
        bSeller = findViewById(R.id.bSeller)
        editTextPhone = findViewById(R.id.editTextPhone)


        // Случай нажатия кнопки "Для бизнеса"
        bSeller.setOnClickListener {
            startActivity(Intent(this, ScaningQRcode::class.java))
        }

        // Случай нажатия кнопки "Сгенерировать код"
        bBuyer.setOnClickListener {
//            startActivity(Intent(this, GenerateQRcode::class.java))
            val telephone_nomber = editTextPhone.text.toString().trim()
            var back_response = ""



            if (telephone_nomber.isEmpty()) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_LONG).show()
            } else {
                // отправка номера номера телефона в бэкэнд
                Fuel.post("https://8c6a-62-113-109-24.eu.ngrok.io")
                    .body("{'phone': '78000000029'}")
                    .also { println(it) }
                    .response { result ->
                        // Проверить на успешность передачи
                        back_response = result.toString();
                        Toast.makeText(this, back_response, Toast.LENGTH_LONG).show()
                    }


                val i = Intent(this, GenerateQRcode::class.java)
                i.putExtra("key", "Result:${telephone_nomber}")
                startActivity(i)
            }
        }
    }
}
//    private fun send_telephone_nomber(accessToken: String) {
//        Fuel.post("https://af7b-62-113-109-24.eu.ngrok.io")
//            .body("{'phone': '78000000029'}")
//            .also { println(it) }
//            .response { result ->
//                textResult2.text = result.toString();
//            }
//    }
