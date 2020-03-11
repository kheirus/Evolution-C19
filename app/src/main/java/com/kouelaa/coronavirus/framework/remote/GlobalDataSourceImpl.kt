package com.kouelaa.coronavirus.framework.remote

import com.kouelaa.coronavirus.data.datasources.remote.GlobalDataSource
import com.kouelaa.coronavirus.domain.entities.Global
import okhttp3.ResponseBody

/**
 * Created by kheirus on 2020-03-09.
 */
class GlobalDataSourceImpl(private val apiService: ApiService) :
    GlobalDataSource {

    override suspend fun get(): Global {

        // TODO-(11/03/20)-kheirus: check null
       return apiService.getGlobal().body()!!
    }

}