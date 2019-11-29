package com.deepinsoul

import android.Manifest
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin_baselib.api.Constants
import com.kotlin_baselib.base.BaseActivity
import com.kotlin_baselib.base.EmptyModelImpl
import com.kotlin_baselib.base.EmptyPresenterImpl
import com.kotlin_baselib.base.EmptyView
import com.kotlin_baselib.utils.PermissionUtils
import com.kotlin_baselib.utils.SnackbarUtil
import com.soul_music.MusicFragment
import com.soul_picture.PictureFragment
import com.soul_video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_drawer.*
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.util.Log
import com.meituan.android.walle.WalleChannelReader


/**
 *  Created by CHEN on 2019/6/12
 *  Email:1181785848@qq.com
 *  Package:com.deepinsoul
 *  Introduce:
 **/
class MainActivity : BaseActivity<EmptyView, EmptyModelImpl, EmptyPresenterImpl>(), EmptyView, RadioGroup.OnCheckedChangeListener {

    private val mFragments = SparseArray<Fragment>()
    private val rbIdList = intArrayOf(R.id.rb_picture, R.id.rb_music, R.id.rb_video)

    private var mFirstTime: Long = 0


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
//        showLoading()
        PermissionUtils.permission(Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).callBack(object : PermissionUtils.PermissionCallBack {
            override fun onGranted(permissionUtils: PermissionUtils) {
                setFragments()
            }

            override fun onDenied(permissionUtils: PermissionUtils) {
                setFragments()
                SnackbarUtil.ShortSnackbar(
                        window.decorView,
                        "拒绝了权限，将无法使用部分功能",
                        SnackbarUtil.WARNING
                ).show()
            }
        }).request()

        WalleChannelReader.getChannel(this.getApplicationContext())?.let { SnackbarUtil.ShortSnackbar(window.decorView, it,SnackbarUtil.CONFIRM).show() }
    }


    override fun initListener() {
        btn_friends_planet.setOnClickListener { ARouter.getInstance().build(Constants.FRIENDS_PLANNET_ACTIVITY_PATH).navigation() }
    }

    private fun setFragments() {
        mFragments.put(0, PictureFragment.newInstance("picture"))
        mFragments.put(1, MusicFragment.newInstance("music"))
        mFragments.put(2, VideoFragment.newInstance("video"))
        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(i: Int): Fragment {
                return mFragments.get(i)
            }

            override fun getCount(): Int {
                return mFragments.size()
            }
        }
        rg_main.setOnCheckedChangeListener(this)
        scrollViewPager.adapter = adapter
        scrollViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                var i = 0
                for (id in rbIdList) {
                    if (position == i) {
                        rg_main.check(id)
                    }
                    i++
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        scrollViewPager.offscreenPageLimit = 2
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        var i = 0
        for (id in rbIdList) {
            if (id == checkedId) {
                scrollViewPager.setCurrentItem(i, true)
                findViewById<RadioButton>(id).textSize = 18f
            } else {
                findViewById<RadioButton>(id).textSize = 16f
            }
            i++
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.drawer ->//弹出DrawerLayout菜单，参数为弹出的方式
                drawer.openDrawer(GravityCompat.START)
            R.id.first ->
                SnackbarUtil.ShortSnackbar(
                        window.decorView,
                        item.title.toString(),
                        SnackbarUtil.INFO
                ).show()
            R.id.second ->
                SnackbarUtil.ShortSnackbar(
                        window.decorView,
                        item.title.toString(),
                        SnackbarUtil.CONFIRM
                ).show()
            R.id.third ->
                SnackbarUtil.ShortSnackbar(
                        window.decorView,
                        item.title.toString(),
                        SnackbarUtil.WARNING
                ).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.picture_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mFirstTime > 2000) {
                mFirstTime = System.currentTimeMillis()
                SnackbarUtil.ShortSnackbar(
                        window.decorView,
                        getString(R.string.click_one_more_time_to_exit),
                        SnackbarUtil.ALERT
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

