package clickretina.assasement.skillforge.di

import clickretina.assasement.skillforge.network.ApiService
import clickretina.assasement.skillforge.network.RetrofitProvider
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appKoinModule = module {

    single<Json> {
        Json {
            encodeDefaults = false
            ignoreUnknownKeys = true
        }
    }

    single<ApiService>{
        RetrofitProvider.api
    }

}
