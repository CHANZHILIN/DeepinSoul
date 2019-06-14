package com.deepinsoul

import android.content.Context
import com.kotlin_baselib.base.BaseActivity
import com.kotlin_baselib.base.EmptyModelImpl
import com.kotlin_baselib.base.EmptyPresenterImpl
import com.kotlin_baselib.base.EmptyView
import com.kotlin_baselib.glide.GlideUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<EmptyView, EmptyModelImpl, EmptyPresenterImpl>(), EmptyView {
    override fun createPresenter(): EmptyPresenterImpl {
        return EmptyPresenterImpl(this)
    }


    override fun getResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        sample_text.text = stringFromJNI()
//        showLoading()
        GlideUtil.instance.loadBigImageWithProgress(
            mContext as Context,
            "http://img.mp.itc.cn/upload/20160713/2498549be77d471b8d29f1f232bffced.jpg",
            progress_image
        )
    }

    override fun initListener() {

    }

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
