package com.kouelaa.informe.framework.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by kheirus on 2020-03-09.
 */

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        return chain.proceed(req)
    }
}