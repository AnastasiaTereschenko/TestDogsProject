package ch.iagentur.unity.testdogsproject.misc.exception

class NetworkException(val errorBodyString: String?, val code: Int) : Exception() {
}
