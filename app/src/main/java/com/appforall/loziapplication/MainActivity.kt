package com.appforall.loziapplication

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var adsUtil : AdsUtil
    lateinit var mInterstitial : InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        adsUtil = AdsUtil(this)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        webview.webChromeClient = WebChromeClient()
        webview.webViewClient = WebViewClient()

        webview.settings.javaScriptEnabled = true
        webview.loadUrl("https://lozi.vn")
    }

    override fun onStart(){
        super.onStart()
        mInterstitial = adsUtil.loadAdsNotShow()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if(webview.canGoBack())
                webview.goBack()
            else
                super.onBackPressed()
        }
    }

    fun loadUrl(url : String){
        if(mInterstitial.isLoaded)
            mInterstitial.show()
        webview.loadUrl(url)
        mInterstitial = adsUtil.loadAdsNotShow()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.do_an -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/do-an?utm_campaign=copy")
            R.id.do_an_ship -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/do-an-ship?utm_campaign=copy")
            R.id.goc_con_gai -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/goc-con-gai?utm_campaign=copy")
            R.id.do_con_trai -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/do-con-trai?utm_campaign=copy")
            R.id.my_pham -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/my-pham?utm_campaign=copy")
            R.id.do_dien_tu -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/do-dien-tu?utm_campaign=copy")
            R.id.do_gia_dung -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/do-gia-dung?utm_campaign=copy")
            R.id.me_be ->loadUrl("https://lozi.vn/a/ho-chi-minh/photos/me-va-be?utm_campaign=copy")
            R.id.idol_han_quoc -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/fan-han-quoc?utm_campaign=copy")
            R.id.xe_co -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/xe-co?utm_campaign=copy")
            R.id.phu_kien_thoi_trang -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/phu-kien-thoi-trang?utm_campaign=copy")
            R.id.giay_sneaker -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/sneaker?utm_campaign=copy")
            R.id.toc_mong_lamdep -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/toc-spa?utm_campaign=copy")
            R.id.thu_cung -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/thu-cung?utm_campaign=copy")
            R.id.sach_truyen -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/sach-va-truyen?utm_campaign=copy")
            R.id.dochoi_sothich -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/do-choi-va-so-thich?utm_campaign=copy")
            R.id.am_nhac -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/dung-cu-am-nhac?utm_campaign=copy")
            R.id.trang_tri_nha_cua -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/vat-dung-trang-tri?utm_campaign=copy")
            R.id.nha_phongtro -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/nha-phong-tro?utm_campaign=copy")
            R.id.vesukien_giamgia -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/coupon-giam-gia-give-away?utm_campaign=copy")
            R.id.hang_order -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/hang-order?utm_campaign=copy")
            R.id.khac -> loadUrl("https://lozi.vn/a/ho-chi-minh/photos/khac?utm_campaign=copy")
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
