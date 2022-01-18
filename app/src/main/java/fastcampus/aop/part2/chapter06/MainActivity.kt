package fastcampus.aop.part2.chapter06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }

    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindView()
    }

    private fun bindView() {
        seekBar
            .setOnSeekBarChangeListener(
                object: SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        if (fromUser) {
                            updateRemainTime(progress * 60 * 1000L)
                        }

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        currentCountDownTimer?.cancel()
                        currentCountDownTimer = null
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        seekBar ?: return

                        currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
                        currentCountDownTimer?.start()
                    }
                }
            )
    }

    private fun createCountDownTimer(initialMillis: Long) =
         object: CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekbar(millisUntilFinished)
            }

            override fun onFinish() {
                updateRemainTime(0)
                updateSeekbar(0)
            }

    }

    private fun updateRemainTime(remainMillis: Long) {

        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekbar(remainMillis: Long) {
        seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}