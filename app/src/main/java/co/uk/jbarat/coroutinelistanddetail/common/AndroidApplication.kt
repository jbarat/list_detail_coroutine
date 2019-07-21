package co.uk.jbarat.coroutinelistanddetail.common

import android.app.Application
import co.uk.jbarat.coroutinelistanddetail.BuildConfig
import co.uk.jbarat.coroutinelistanddetail.common.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@AndroidApplication)
            modules(koinModules)
        }
    }
}

