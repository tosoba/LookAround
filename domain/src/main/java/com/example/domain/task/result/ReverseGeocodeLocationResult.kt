package com.example.domain.task.result

import com.example.domain.repo.RepositoryResult
import com.example.domain.repo.model.GeocodingInfo

sealed class ReverseGeocodeLocationResult {
    class Data(
        val result: RepositoryResult<GeocodingInfo>
    ) : ReverseGeocodeLocationResult()

    object GeocodingError : ReverseGeocodeLocationResult()
}