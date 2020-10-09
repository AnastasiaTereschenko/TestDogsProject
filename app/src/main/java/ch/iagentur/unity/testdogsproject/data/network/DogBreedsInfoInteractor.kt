package ch.iagentur.unity.testdogsproject.data.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.network.BaseInteractor
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import javax.inject.Inject

class DogBreedsInfoInteractor @Inject constructor(val repositoryRetriever: RepositoryRetriever): BaseInteractor<List<DogBreedInfo>> () {
    suspend fun getDogBreedInfo(id:Int): Result<List<DogBreedInfo>>? {
       return execute {repositoryRetriever.getService().getBreedInfo(id)}
    }
}