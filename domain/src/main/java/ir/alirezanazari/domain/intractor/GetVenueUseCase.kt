package ir.alirezanazari.domain.intractor

import io.reactivex.Scheduler
import io.reactivex.Single
import ir.alirezanazari.domain.entity.VenueDetailEntity
import ir.alirezanazari.domain.repository.VenueRepository


class GetVenueUseCase(
    private val repository: VenueRepository,
    io: Scheduler,
    ui: Scheduler
) : UseCaseSingle<VenueDetailEntity, String>(io, ui) {

    override fun build(param: String): Single<VenueDetailEntity> {
        return repository.getVenueById(param)
    }

}