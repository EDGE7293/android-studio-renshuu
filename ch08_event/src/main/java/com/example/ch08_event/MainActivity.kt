package com.example.ch08_event

import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch08_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   // 뒤로가기 버튼을 눌렀을 때의 시간을 저장하는 속성
    var initTime = 0L

    // 멈춘 시각을 저장하는 변수
    var pauseTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // START 버튼의 이벤트
        binding.Start.setOnClickListener{
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()

            // START버튼을 눌렀을 때 STOP버튼과 RESET 버튼 활성화
            binding.Start.isEnabled = false
            binding.Stop.isEnabled = true
            binding.Reset.isEnabled = true
        }
        // STOP 버튼의 이벤트
        binding.Stop.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.Stop.isEnabled = false
            binding.Start.isEnabled = true
            binding.Reset.isEnabled = true
        }
        // RESET 버튼의 이벤트
        binding.Reset.setOnClickListener {
            // RESET시 시간 0초로 만들기
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.Start.isEnabled = true
            binding.Stop.isEnabled = true
            binding.Reset.isEnabled = false
        }

    }
    // 뒤로가기 버튼 핸들러
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
       // 뒤로가기 버튼을 눌렀을 때 처리
        if (keyCode === KeyEvent.KEYCODE_BACK) {
            //뒤로가기 버튼을 처음 눌렀거나 누른 지 3초가 지났을 때 처리
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "종료하려면 한 번 더 누르세요.", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}