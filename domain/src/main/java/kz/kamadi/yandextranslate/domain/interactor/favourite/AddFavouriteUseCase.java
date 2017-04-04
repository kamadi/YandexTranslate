package kz.kamadi.yandextranslate.domain.interactor.favourite;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.FavouriteRepository;

public class AddFavouriteUseCase extends UseCase<Boolean>{

    private FavouriteRepository repository;
    private HistoryEntity entity;

    @Inject
    public AddFavouriteUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, FavouriteRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParam(HistoryEntity entity) {
        this.entity = entity;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable() {
        return repository.add(entity);
    }
}
