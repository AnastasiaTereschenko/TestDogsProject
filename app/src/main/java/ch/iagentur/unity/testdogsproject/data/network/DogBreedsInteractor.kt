package ch.iagentur.unity.testdogsproject.data.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.network.BaseInteractor
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import javax.inject.Inject

//class DogBreedsInteractor @Inject constructor(val repositoryRetriever: RepositoryRetriever): BaseInteractor<List<DogBreed>> () {
//    suspend fun getDogBreeds(page:Int): Result<List<DogBreed>>? {
//       return execute{repositoryRetriever.getService().getBreeds(page)}
//    }
//}