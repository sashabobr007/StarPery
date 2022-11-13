package com.example.qr_coder


import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import android.widget.ImageView
import android.widget.Toast
import com.github.kittinunf.fuel.core.Headers
import com.google.zxing.WriterException


class GenerateQRcode : AppCompatActivity() {

    private lateinit var bBack: Button
    private lateinit var textResult2: TextView
    private lateinit var imageQR: ImageView
    var string1 = ""
    var string2 = ""
    var stringResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qrcode)

        bBack = findViewById(R.id.bBack)
        textResult2 = findViewById(R.id.textView2)
        imageQR = findViewById(R.id.ivQRCode)

        // Случай нажатия кнопки "Назад"
        bBack.setOnClickListener {
            finish()
        }

        // получение телефона и запуск функции
        val extras = intent.extras
        if (extras != null) {
            val telephone_nomber = extras.getString("key")
            //The key argument here must match that used in the other activity
            telephone_nomber?.let { getToken(it) }
        }
    }

     // функция по получению токена
    private fun getToken(phone: String) {
        Fuel.post("https://8c6a-62-113-109-24.eu.ngrok.io")
            .body("{'phone': '78000000029'}")
            .also { println(it) }
            .response { result ->
                textResult2.text = result.toString();
            }
             //Потом прописать, что при ошибке код не генерировать
             GenerateQr()
    }

    // генерация QR-кода
    private fun GenerateQr() {
        val writer = QRCodeWriter()
        val data = textResult2.toString().trim()
        try {
            val bitMatrix = writer.encode(data as String?, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            imageQR.setImageBitmap(bmp)    // Вот она, заветная строка
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}