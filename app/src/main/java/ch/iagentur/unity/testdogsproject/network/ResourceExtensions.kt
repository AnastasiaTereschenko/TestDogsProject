package ch.iagentur.unity.testdogsproject.network


fun <T, K> Resource<T>.map(objectsMapper: ObjectsMapper<T, K>): Resource<K> {
    return Resource(status, objectsMapper(data), error)
}