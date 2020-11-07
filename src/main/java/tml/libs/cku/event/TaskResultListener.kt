package tml.libs.cku.event

interface TaskResultListener<TSuccess, TError> {
    fun onSuccess(data: TSuccess)
    fun onError(data: TError?)
}