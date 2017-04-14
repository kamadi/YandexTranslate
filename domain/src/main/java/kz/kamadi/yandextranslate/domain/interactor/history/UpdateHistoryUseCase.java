package kz.kamadi.yandextranslate.domain.interactor.history;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

public class UpdateHistoryUseCase extends UseCase<Boolean> {

    private HistoryRepository historyRepository;
    private HistoryEntity historyEntity;

    @Inject
    public UpdateHistoryUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, HistoryRepository historyRepository) {
        super(threadExecutor, postExecutionThread);
        this.historyRepository = historyRepository;
    }

    public void setParam(HistoryEntity historyEntity) {
        this.historyEntity = historyEntity;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable() {
        return historyRepository.update(historyEntity);
    }
}
