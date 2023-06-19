package solid.icon.gitusers

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App: Application(), KodeinAware {
    override val kodein: Kodein
        get() = TODO("Not yet implemented")
}