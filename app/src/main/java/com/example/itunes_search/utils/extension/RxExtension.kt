package com.example.itunes_search.utils.extension

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.reactivestreams.Subscriber
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.async(withDelay: Long = 0): Observable<T> =
    this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(
        AndroidSchedulers.mainThread()
    )

@Suppress("UNUSED_EXPRESSION")
fun <T> Observable<T>.autoHandlerSubscribeSuccess(successHandler: (model: T) -> Unit = { }): Observable<T> {
    this.subscribe({
        //Log.d("Anson RxExtension:", "autoHandlerSubscribeSuccess")
        successHandler
    }, { }, {}, {})
    return this
}

@Suppress("UNUSED_EXPRESSION")
fun <T> Observable<T>.autoHandlerSubscribeError(message: MutableLiveData<String>? = null, errorHandler: (throwable: Throwable) -> Unit = { it.printStackTrace() }): Observable<T> {
    this.subscribe({}, {
        // Log.e("Anson RxExtension:", "autoHandlerSubscribeError")
        if (message != null) {
            if (it is NetworkErrorException || it is ConnectException || it is SocketException || it is HttpException) {
                //message.value = BaseApplication.instance.getString(R.string.network_error)
            } else if (it is JSONException || it is NullPointerException) {
                message.value = "JSON data error!"
            } else {
                message.value = it.message
                it.printStackTrace()
            }
        } else {
            it.printStackTrace()
        }
    }, {}, {})
    return this
}

@Suppress("UNUSED_EXPRESSION")
fun <T> Observable<T>.autoHandlerObserveError(errorHandler: (throwable: Throwable) -> Unit = { it.printStackTrace() }): Observable<T> {
    this.doOnError {
        it.printStackTrace()
        errorHandler
        throw it
    }
    return this
}

@Suppress("UNUSED_EXPRESSION")
fun <T> Observable<T>.autoHandlerObserveError(
    message: MutableLiveData<String>,
    errorHandler: (throwable: Throwable) -> Unit = { it.printStackTrace() }
): Observable<T> {
    return this.doOnError {
        // Log.e("Anson RxExtension:", "doOnError")
        errorHandler
        if (it is NetworkErrorException || it is ConnectException) {
            message.value = "網絡連接錯誤！"
        } else if (it is JSONException || it is NullPointerException) {
            message.value = "JSON data error!"
        } else {
            message.value = it.message
            it.printStackTrace()
        }
    }
}

@Suppress("UNUSED_EXPRESSION")
private fun <T> Observable<T>.handlerOnError(errorHandler: (throwable: Throwable) -> Unit): Observable<T> {
    this.subscribe({}, {
        errorHandler
    }, {}, {})
    return this
}


fun <T> Observable<T>.uiSubscribe(subscriber: Subscriber<in T>): Disposable? {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()
}

fun <T> Observable<T>.uiSubscribe(observer: Observer<in T>) {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)
}


fun <T> Observable<T>.uiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null
): Disposable {

    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ next ->
            onNext(next)
        }, { throwable ->
            onError?.apply {
                this(throwable)
            }
        }, {
            onComplete?.apply {
                this()
            }
        })
}
