package kz.kamadi.yandextranslate.domain.interactor.history;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

public class SearchHistoriesUseCase extends UseCase<List<HistoryEntity>> {

    private HistoryRepository repository;
    private String text;
    private boolean isFavourite;

    @Inject
    public SearchHistoriesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, HistoryRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParam(String text,boolean isFavourite) {
        this.text = text;
        this.isFavourite = isFavourite;
    }

    @Override
    protected Observable<List<HistoryEntity>> buildUseCaseObservable() {
        return repository.search(text,isFavourite);
    }
}
