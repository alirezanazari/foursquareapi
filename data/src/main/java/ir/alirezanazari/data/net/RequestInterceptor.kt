package ir.alirezanazari.data.net

import okhttp3.Interceptor
import okhttp3.Response


class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val CLIENT_ID = "MWABYZWKPYWC3OPL3ZDL3N0TIVSCI5JC2QN3BYHLIIQKGOGE"
        val CLIENT_SECRET = "NLXLP2OBZSE2LCIGVTCJFDJIDD01MERGGAIUXUZNXDKZFLI4"
        val DEVELOPMENT_DATE = "20200202"

        val url =  chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("client_id" , CLIENT_ID)
            .addQueryParameter("client_secret" , CLIENT_SECRET)
            .addQueryParameter("v" , DEVELOPMENT_DATE)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

}