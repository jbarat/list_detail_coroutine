package co.uk.jbarat.coroutinelistanddetail.common.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.uk.jbarat.coroutinelistanddetail.common.navigation.NavigationCommand
import co.uk.jbarat.coroutinelistanddetail.common.navigation.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    protected val mutableNavigation = SingleLiveEvent<NavigationCommand>()
    val navigation: LiveData<NavigationCommand> = mutableNavigation
}