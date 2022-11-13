package com.example.qr_coder


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView


class ScaningQRcode : AppCompatActivity(), ZBarScannerView.ResultHandler {
    private lateinit var zbView: ZBarScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        zbView = ZBarScannerView(this)
        setContentView(zbView)

    }

    override fun onResume(){
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        val i = Intent(this, ScaningResult::class.java)
        i.putExtra("key", "Result:${result?.contents}")
        startActivity(i)

//        setContentView(R.layout.activity_main)
//        finish()
    }
}