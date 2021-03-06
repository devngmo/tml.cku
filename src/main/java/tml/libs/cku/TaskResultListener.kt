package tml.libs.cku

interface TaskResultListener<TSuccess, TError> {
    fun onSuccess(data: TSuccess)
    fun onError(data: TError?)
}