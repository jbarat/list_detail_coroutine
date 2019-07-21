package co.uk.jbarat.coroutinelistanddetail.common.di

import co.uk.jbarat.coroutinelistanddetail.data.author.AuthorRepository
import co.uk.jbarat.coroutinelistanddetail.data.comment.CommentRepository
import co.uk.jbarat.coroutinelistanddetail.data.network.JsonPlaceHolderService
import co.uk.jbarat.coroutinelistanddetail.data.network.webserviceUrl
import co.uk.jbarat.coroutinelistanddetail.data.post.PostRepository
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.author.AuthorGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.comment.CommentGateway
import co.uk.jbarat.coroutinelistanddetail.domain.gateway.post.PostGateway
import co.uk.jbarat.coroutinelistanddetail.domain.getpost.GetPostUseCase
import co.uk.jbarat.coroutinelistanddetail.domain.getpostlist.GetPostListUseCase
import co.uk.jbarat.coroutinelistanddetail.feature.postdetail.PostDetailFragmentArgs
import co.uk.jbarat.coroutinelistanddetail.feature.postdetail.PostDetailViewModel
import co.uk.jbarat.coroutinelistanddetail.feature.postlist.PostListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val presentationModule = module {
    viewModel { (args: PostDetailFragmentArgs) -> PostDetailViewModel(args.postId, get()) }
    viewModel { PostListViewModel(get()) }
}

val domainModule = module {
    factory { GetPostListUseCase(get(), get()) }
    factory { GetPostUseCase(get(), get(), get()) }
}

val dataModule = module {
    factory<CommentGateway> { CommentRepository(get()) }
    factory<AuthorGateway> { AuthorRepository(get()) }
    factory<PostGateway> { PostRepository(get()) }
    single {
        OkHttpClient.Builder()
                .callTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
    }
    single {
        Retrofit.Builder()
                .client(get())
                .baseUrl(webserviceUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }
    single<JsonPlaceHolderService> { get<Retrofit>().create(JsonPlaceHolderService::class.java) }
}

val koinModules = listOf(
        dataModule,
        domainModule,
        presentationModule
)
