package com.example.domain.task.result

import com.example.domain.repo.RepositoryResult
import com.example.domain.repo.model.SimplePOI

sealed class FindNearbyPOIsResult {
    class Data(
        val result: RepositoryResult<List<SimplePOI>>
    ) : FindNearbyPOIsResult()

    // different types of errors inform the view layer what went wrong
    // (for example which phase of data retrieval failed)
    // can be used when repository executes multiple API requests and one of them fails
    object NoPOIsFound : FindNearbyPOIsResult()
}