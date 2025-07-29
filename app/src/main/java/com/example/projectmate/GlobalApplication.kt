package com.example.projectmate

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

//카카오 SDK 초기화 담당 클래스
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key)) // Kakao SDK 초기화
    }
}