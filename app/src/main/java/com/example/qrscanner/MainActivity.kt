package com.example.qrscanner

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startBarcodeReader(view: View) {
        IntentIntegrator(this).initiateScan()
    }

    fun startBarcodeReaderCustom(view: View) {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)   // 여러가지 바코드 규격중에 특정 규격만 지원하고자 할때 지정해줄수잇음
        integrator.setPrompt("QR 코드를 스캔하여 주세요")     // 스캔할때 하단의 문구를 커스텀
        integrator.setCameraId(0)   // 0은 후면카메라, 1은 전면카메라
        integrator.setBeepEnabled(true)     // 바코드 인식했을때 삡하는 소리
        integrator.setBarcodeImageEnabled(true) // onActivityResult 에 바코드에 대한 결과값뿐만아니라 인식한 사진도 비트맵형태로 함께 전달
        integrator.initiateScan()
    }

    fun startBarcodeReaderCustomActivity(view: View) {
        val integrator = IntentIntegrator(this)
        integrator.setBarcodeImageEnabled(true) // onActivityResult 에 바코드에 대한 결과값뿐만아니라 인식한 사진도 비트맵형태로 함께 전달
        integrator.captureActivity = MyBarcodeReaderActivity::class.java
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.contents != null) {
                Toast.makeText(this, "scanned: ${result.contents} format: ${result.formatName}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
            if (result.barcodeImagePath != null) {
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                scannedBitmap.setImageBitmap(bitmap)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}