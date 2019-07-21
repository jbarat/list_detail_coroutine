I was asked to show a sample code of my work. I have nothing on hand which is not bearing the scars of years of experimenting and architecture changes.
So I decided to write something new and to spice it up a bit I will do it in a way I never done it before.
The challenge: Implement a small app in clean architecture without being reactive, using coroutines and without using Rx Java.

The app is an implementation of the Babylon Health interview test: https://github.com/Babylonpartners/android-playbook/blob/master/recruitment/code_challenge.md
I have done this test 2 years age in Java.

Some thoughts on the implementation:
Domain:
The usecases are using coroutines. What I was wondering if they should have a suspend-able function or they should have a a parameter of scope.
Either way the consumer can't use them without using coroutines itself.

override fun invoke(scope: CoroutineScope, params: GetPostListRequest)

override suspend fun invoke(params: GetPostListRequest)

Other options are channels(https://kotlinlang.org/docs/reference/coroutines/channels.html)
and Flow(https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/).
But these would force the client to be reactive and the original goal of this exercise is to not be reactive.

Presentation:
The presentation layer uses MVVM which is reactive so I violate the original rule a bit. But MVVM with ViewModels have a few considerable advantages.
We can scope our usecase to the viewmodel. This way the cleanup and cancellation of the coroutines are handled automatically
(In this app there are no usecases which need a longer life than the viewmodel which spawned them).
Another advantage is that the ViewModel never calls the View, so I don't need to be careful to update the view when it's not ready (ie rotating).

Data:
I had no time adding any form of cashing but I would add them to the positories.
I consider caching an implementation detail of the repositories, so usecases no nothing about caching.

Navigation:
I used the new navigation component to handle it. The app is a single activity and the view logic is in fragments.

Test:

Conclusions:


I didn't know about Coroutines Flow before. It is almost like Rx java implemented natively. I will keep my eye on it.
It would solve the problem of putting rx into your domain. Using Flow you could properly follow clean architecture and still be fully reactive.

