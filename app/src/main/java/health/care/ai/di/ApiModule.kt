

package health.care.ai.di

import health.care.ai.api.PostsServices
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(PostsServices::class.java) }
}