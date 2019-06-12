package com.deepinsoul

import com.kotlin_baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun initListener() {

    }

    override fun getResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        sample_text.text = stringFromJNI()
        showLoading()
    }


    /*  override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)

          // Example of a call to a native method
          sample_text.text = stringFromJNI()
      }
  */
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

}
