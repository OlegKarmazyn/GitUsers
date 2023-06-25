package solid.icon.gitusers

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import solid.icon.gitusers.data.database.UserDatabase
import solid.icon.gitusers.data.repositories.DetailsRepository
import solid.icon.gitusers.data.repositories.InternetConnection
import solid.icon.gitusers.data.repositories.UserRepository
import solid.icon.gitusers.data.view_models.MainViewModel
import solid.icon.gitusers.data.view_models.RepositoryViewModel

class App : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { InternetConnection(instance()) }

        bind() from singleton { UserDatabase(instance()) }

        bind() from singleton { UserRepository(instance()) }
        bind() from singleton { DetailsRepository(instance()) }

        bind() from singleton { MainViewModel(instance(), instance()) }
        bind() from singleton { RepositoryViewModel(instance()) }
    }
}