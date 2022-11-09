

package health.data.ai.proacdoc.di

import health.data.ai.proacdoc.api.PostsServices
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(PostsServices::class.java) }
}