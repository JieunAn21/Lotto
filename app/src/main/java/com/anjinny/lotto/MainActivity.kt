package com.anjinny.lotto

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }

    private val btnAdd: Button by lazy {
        findViewById(R.id.btnAdd)
    }

    private val btnReset: Button by lazy {
        findViewById(R.id.btnReset)
    }

    private val btnRun: Button by lazy {
        findViewById(R.id.btnRun)
    }

    private val tvNumberList: List<TextView> by lazy {
        listOf(
                findViewById(R.id.tvNumber1),
                findViewById(R.id.tvNumber2),
                findViewById(R.id.tvNumber3),
                findViewById(R.id.tvNumber4),
                findViewById(R.id.tvNumber5),
                findViewById(R.id.tvNumber6)
        )
    }

    private var didRun = false

    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initBtnAdd()
        initBtnReset()
        initBtnRun()
    }

    private fun initBtnRun() {
        btnRun.setOnClickListener {
            didRun = true

            val numberList = getRandomNumber()
            numberList.forEachIndexed { index, number ->
                tvNumberList[index].apply {
                    this.text = number.toString()
                    this.isVisible = true
                }
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (!pickNumberSet.contains(i)) {
                    this.add(i)
                }
            }
        }

        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.take(6 - pickNumberSet.size)

        return newList.sorted()
    }

    private fun initBtnAdd() {
        btnAdd.setOnClickListener {
            val pickNumber = numberPicker.value

            if (didRun) {
                Toast.makeText(this, "초기화 후 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "숫자는 최대 5개 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(pickNumber)) {
                Toast.makeText(this, "이미 선택된 숫자입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pickNumberSet.add(pickNumber)

            tvNumberList[pickNumberSet.size].apply {
                isVisible = true
                text = pickNumber.toString()
            }
        }
    }

    private fun initBtnReset() {
        btnReset.setOnClickListener {
            didRun = false

            pickNumberSet.clear()
            tvNumberList.forEach {
                it.isVisible = false
            }
        }
    }
}