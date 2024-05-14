package com.kaanbalci.kaan_balci_20222425006

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val buttons: List<Button> by lazy {
        listOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8),
            findViewById(R.id.btn9), findViewById(R.id.btnTopla), findViewById(R.id.btnCikar),
            findViewById(R.id.btnBol), findViewById(R.id.btnCarp), findViewById(R.id.btnEsittir),
            findViewById(R.id.buttonSifirla), findViewById(R.id.btnVirgul), findViewById(R.id.btndel)
        )
    }

    private var ilkSayi: Double = 0.0
    private lateinit var hesapEkrani: TextView
    private var virgulDurum: Boolean = false
    private var islemDurum: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons.forEach { button ->
            button.setOnClickListener {
                when (button.id) {
                    R.id.btnEsittir -> TIKLA_SEMBOL("=")
                    R.id.btnTopla -> TIKLA_SEMBOL("+")
                    R.id.btnCikar -> TIKLA_SEMBOL("-")
                    R.id.btnCarp -> TIKLA_SEMBOL("*")
                    R.id.btnBol -> TIKLA_SEMBOL("/")
                    R.id.buttonSifirla -> TIKLA_SEMBOL("sifirla")
                    R.id.btnVirgul -> TIKLA_SEMBOL(".")
                    R.id.btndel -> TIKLA_DEL()
                    else -> TIKLA_NUMARA(button.text.toString().toInt())
                }
            }
        }

        hesapEkrani = findViewById(R.id.hesapEkrani)

        hesapEkrani.text = "0"
        ilkSayi = 0.0
        virgulDurum = false
        islemDurum = "0"
    }

    private fun TIKLA_NUMARA(sayi: Int) {
        if (hesapEkrani.text.toString() in arrayOf("0", "+", "*", "/", "-")) {
            hesapEkrani.text = ""
        }
        hesapEkrani.text = "${hesapEkrani.text}$sayi"
        virgulDurum = false
    }

    private fun TIKLA_SEMBOL(sembol: String) {
        when (sembol) {
            "=" -> {
                if (hesapEkrani.text.toString() in arrayOf("+", "*", "/", "-")) {

                } else {
                    when (islemDurum) {
                        "+" -> {
                            ilkSayi += hesapEkrani.text.toString().toDouble()
                            hesapEkrani.text = ilkSayi.toString()
                        }
                        "-" -> {
                            ilkSayi -= hesapEkrani.text.toString().toDouble()
                            hesapEkrani.text = ilkSayi.toString()
                        }
                        "/" -> {
                            val ikinciSayi = hesapEkrani.text.toString().toDouble()
                            if (ikinciSayi != 0.0) {
                                ilkSayi /= ikinciSayi
                                hesapEkrani.text = ilkSayi.toString()
                            } else {
                                hesapEkrani.text = "Hata"
                            }
                        }
                        "*" -> {
                            ilkSayi *= hesapEkrani.text.toString().toDouble()
                            hesapEkrani.text = ilkSayi.toString()
                        }
                    }
                }

                islemDurum = "0"
            }

            "sifirla" -> {
                ilkSayi = 0.0
                hesapEkrani.text = "0"
                islemDurum = "0"
            }

            "." -> {
                if (!virgulDurum) {
                    if (hesapEkrani.text.isNotEmpty()) {
                        hesapEkrani.text = "${hesapEkrani.text}."
                        virgulDurum = true
                    }
                }
            }

            else -> {
                if (hesapEkrani.text.toString() in arrayOf("+", "*", "/", "-")) {
                    if (islemDurum == "*" || islemDurum == "/") {
                        ilkSayi = -1 * ilkSayi
                    }
                    hesapEkrani.text = sembol
                    islemDurum = sembol
                } else {
                    ilkSayi = hesapEkrani.text.toString().toDouble()
                    hesapEkrani.text = sembol
                    islemDurum = sembol
                }
            }
        }
    }

    private fun TIKLA_DEL() {
        val currentText = hesapEkrani.text.toString()
        if (currentText.isNotEmpty()) {
            hesapEkrani.text = currentText.substring(0, currentText.length - 1)
        }
    }
}
