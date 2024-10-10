package umc.com.mobile.threadtesttimer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import umc.com.mobile.threadtesttimer.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding : ActivityMainBinding

    private var isRunning = false       // 실행 여부 확인용 변수 false로 초기화
    private var timer : Timer? = null   // timer 변수 추가
    private var time = 0                // time 변수 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener(this)
        binding.btnClear.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnStart -> { // 클릭 이벤트 시 뷰id가 R.id.btn_start 이며
                if(isRunning) { // 스톱워치가 동작 중이라면
                    pause()     // 일시정지 메서드를 실행하고
                } else {        // 동작 중이 아니라면
                    start()     // 시작 메서드를 실행한다.
                }
            }
            R.id.btnClear -> {   // 뷰id가 R.id.btn_refresh이면
                clear()       // 초기화 메서드를 실행한다.
            }
        }
    }

    private fun start() {

        binding.btnStart.text = "정지"

        isRunning = true

        // 스톱워치를 시작하는 로직
        timer = timer(period = 10) {

            time++  // 10밀리초 단위 타이머

            // 시간 계산
            val millis = String.format("%02d", time % 100)
            val second = String.format("%02d", (time % 6000) / 100)
            val minute = String.format("%02d", time / 6000)

            runOnUiThread {     // UI 스레드 생성
                if (isRunning){ // UI 업데이트 조건 설정
                    binding.textView.text = "${minute}:${second}.${millis}"
                }
            }
        }
    }

    private fun pause() {
        binding.btnStart.text = "시작"
        isRunning = false
        timer?.cancel()
    }

    private fun clear() {
        isRunning = false
        timer?.cancel()
        time = 0
        binding.textView.text = "00:00.00"
    }
}