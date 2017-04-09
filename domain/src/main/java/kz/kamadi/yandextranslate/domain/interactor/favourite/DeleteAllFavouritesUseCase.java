package kz.kamadi.yandextranslate.domain.interactor.favourite;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.FavouriteRepository;

public class DeleteAllFavouritesUseCase extends UseCase<Integer> {

    private FavouriteRepository repository;

    @Inject
    public DeleteAllFavouritesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, FavouriteRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    public Observable<Integer> buildUseCaseObservable() {
        return repository.deleteAll();
    }
}
