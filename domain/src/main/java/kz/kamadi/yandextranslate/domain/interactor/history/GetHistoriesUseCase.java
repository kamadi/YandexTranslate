package kz.kamadi.yandextranslate.domain.interactor.history;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

public class GetHistoriesUseCase extends UseCase<List<HistoryEntity>> {

    private HistoryRepository repository;
    private int offset;
    private int limit;

    @Inject
    public GetHistoriesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, HistoryRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParams(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    protected Observable<List<HistoryEntity>> buildUseCaseObservable() {
        return repository.getHistories(offset, limit);
    }
}
