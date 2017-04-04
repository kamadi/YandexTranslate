package kz.kamadi.yandextranslate.domain.interactor.history;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

public class CreateHistoryUseCase extends UseCase<HistoryEntity>{

    private HistoryRepository repository;
    private HistoryEntity entity;

    @Inject
    public CreateHistoryUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, HistoryRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParam(HistoryEntity entity) {
        this.entity = entity;
    }

    @Override
    public Observable<HistoryEntity> buildUseCaseObservable() {
        return repository.create(entity);
    }
}
