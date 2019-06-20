package com.deepinsoul

import android.Manifest
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import com.kotlin_baselib.base.BaseActivity
import com.kotlin_baselib.base.EmptyModelImpl
import com.kotlin_baselib.base.EmptyPresenterImpl
import com.kotlin_baselib.base.EmptyView
import com.kotlin_baselib.utils.SnackbarUtil
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity<EmptyView, EmptyModelImpl, EmptyPresenterImpl>(), EmptyView,
        EasyPermissions.PermissionCallbacks {

    /*   private val mFragments = SparseArray<Fragment>()
       private val rbIdList = intArrayOf(R.id.rb_picture, R.id.rb_music, R.id.rb_video)*/

    private var mFirstTime: Long = 0

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        Toast.makeText(mContext, "权限拒绝", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
/*        GlideUtil.instance.loadImageWithProgress(
                mContext as Context,
//            "https://www.4hou.com/uploads/20180818/1534558145264870.png",
                "http://k.zol-img.com.cn/sjbbs/7692/a7691515_s.jpg",
                progress_image
        )*/

//        hideLoading()
    }

    override fun createPresenter(): EmptyPresenterImpl {
        return EmptyPresenterImpl(this)
    }


    override fun getResId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val actionBarDrawerToggle =
                ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        actionBarDrawerToggle.syncState()
        drawer.setDrawerListener(actionBarDrawerToggle)
//        sample_text.text = stringFromJNI()
        showLoading()
        if (!EasyPermissions.hasPermissions(
                        this,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
        ) {
            EasyPermissions.requestPermissions(
                    this,
                    "网络权限",
                    1001,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            );
        } else {
            /* GlideUtil.instance.loadImageWithProgress(
                     mContext as Context,
 //            "https://www.4hou.com/uploads/20180818/1534558145264870.png",
                     "http://k.zol-img.com.cn/sjbbs/7692/a7691515_s.jpg",
                     progress_image
             )*/
        }
    }

    override fun initListener() {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //弹出DrawerLayout菜单，参数为弹出的方式
        drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mFirstTime > 2000) {
                mFirstTime = System.currentTimeMillis()
                SnackbarUtil.ShortSnackbar(
                        window.decorView,
                        getString(R.string.click_one_more_time_to_exit),
                        SnackbarUtil.Alert
                ).show()
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

/*    */
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     *//*
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }*/

}

