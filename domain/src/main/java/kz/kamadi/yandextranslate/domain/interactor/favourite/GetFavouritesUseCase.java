package kz.kamadi.yandextranslate.domain.interactor.favourite;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.FavouriteRepository;

public class GetFavouritesUseCase extends UseCase<List<HistoryEntity>> {

    private FavouriteRepository repository;
    private int offset;
    private int limit;

    @Inject
    public GetFavouritesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, FavouriteRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParams(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    protected Observable<List<HistoryEntity>> buildUseCaseObservable() {
        return repository.getFavourites(offset, limit);
    }
}
