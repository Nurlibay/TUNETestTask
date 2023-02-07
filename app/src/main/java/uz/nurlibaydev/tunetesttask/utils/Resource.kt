package uz.nurlibaydev.tunetesttask.utils

open class Resource<out T>(val status: ResourceState, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> = Resource(ResourceState.SUCCESS, data, null)

        fun <T> error(message: String?): Resource<T> = Resource(ResourceState.ERROR, null, message)

        fun <T> loading(): Resource<T> = Resource(ResourceState.LOADING, null, null)

        fun <T> networkError(): Resource<T> = Resource(ResourceState.NETWORK_ERROR, null, null)
    }
}

enum class ResourceState {
    LOADING, SUCCESS, ERROR, NETWORK_ERROR
}