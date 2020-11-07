package tml.libs.cku.event

interface SimpleCallbackInterface<TData> {
    fun handle(data: TData)
}