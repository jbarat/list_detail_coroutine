## Goal
I was asked to show a sample code of my work. I have nothing on
hand, which is not bearing the scars of years of experimenting and
architecture changes. 

So I have decided to write something new and to spice it up a bit I will
do it in a way I never done it before. 

**The challenge**: Implement a small
app in clean architecture without being reactive, using coroutines and
without using Rx Java.

The app is an implementation of the Babylon Health interview test. I
have done this test 2 years age in Java. Link:
https://github.com/Babylonpartners/android-playbook/blob/master/recruitment/code_challenge.md

## Some thoughts on the implementation:

Models are called like:
- Post 
- PostEntity 
- PostModel

No special reason for these names. It helps to differentiate the models of
different layers.

#### Domain:
The usecases are using coroutines. What I was wondering if they should
have a suspend-able function or they should have a parameter of a scope.

`override fun invoke(scope: CoroutineScope, params: GetPostListRequest)`

`override suspend fun invoke(params: GetPostListRequest)`

Either way, the consumer can't use them without using coroutines
itself. I have decided to go with the second option. It is more concise.

Other options are channels(https://kotlinlang.org/docs/reference/coroutines/channels.html)
and Flow(https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/).
But these would force the client to be reactive, and the original goal of this exercise is not to be reactive.

Every usecase returns a sealed class with the possible options (only one
success and one error in this app's case). The nice effect of this that
we force the consumer to handle all the cases.

#### Presentation:
The presentation layer uses MVVM, which is reactive, so I violate the original rule a bit. But MVVM with ViewModels have a few considerable advantages.
- We can scope our usecase to the viewmodel. This way the cleanup and
  cancellation of the coroutines is handled automatically (In this app there are no usecases which need a longer life than the viewmodel which spawned them).
- Another advantage is that the ViewModel never calls the View, so I don't need to be careful to update the view when it's not ready (i.e. rotating).

The UI itself is pretty simple. Didn't spend too much time on it.

#### Data:
I had no time adding any form of caching, but I would add them to the
repositories. I consider caching an implementation detail of the
repositories, so usecases no nothing about caching.

#### Navigation:
I have used the new navigation component to handle it. The app is a
single activity, and the view logic is in fragments. 

Navigation should be started from the ViewModels to achieve this I had
to do a bit of juggling with a BaseFragment and a BaseViewModel.

#### Test:
I usually use Spek for unit tests (https://spekframework.org/). It was
challenging to use it with suspended functions. To save time, I decided
to use simple jUnit testing instead.

I wrote tests for the domain layer. If I would have more time I would
have written unit tests to every ViewModel and Repository.

## Conclusions:
Using modern tools (i.e. ViewModel and coroutines), it is much easier to
write an app without rxjava than before.

I didn't know about Coroutines Flow before. It is almost like Rx java implemented natively. I will keep my eye on it.
It would solve the problem of putting rx into your domain. Using Flow, you could properly follow the clean architecture and still be fully reactive.

