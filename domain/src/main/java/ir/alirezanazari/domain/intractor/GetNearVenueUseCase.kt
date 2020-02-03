package ir.alirezanazari.domain.intractor

import io.reactivex.Scheduler
import io.reactivex.Single
import ir.alirezanazari.domain.entity.VenueEntity
import ir.alirezanazari.domain.entity.VenueInputParams
import ir.alirezanazari.domain.repository.VenueRepository

class GetNearVenueUseCase(
    private val repo: VenueRepository,
    io: Scheduler,
    ui: Scheduler
) : UseCaseSingle<List<VenueEntity>, VenueInputParams>(io, ui) {

    override fun build(param: VenueInputParams): Single<List<VenueEntity>> {
        return repo.getNearVenues(param)
    }

}