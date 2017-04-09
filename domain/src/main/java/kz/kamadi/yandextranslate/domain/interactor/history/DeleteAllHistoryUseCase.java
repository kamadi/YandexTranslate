package kz.kamadi.yandextranslate.domain.interactor.history;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

public class DeleteAllHistoryUseCase extends UseCase<Integer> {

    private HistoryRepository repository;

    @Inject
    public DeleteAllHistoryUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, HistoryRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    public Observable<Integer> buildUseCaseObservable() {
        return repository.deleteAll();
    }
}
