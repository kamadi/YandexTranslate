package kz.kamadi.yandextranslate.domain.interactor.language;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class CreateLanguagesUseCase extends UseCase<Boolean> {
    private LanguageRepository repository;
    private List<LanguageEntity> entities;

    @Inject
    public CreateLanguagesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LanguageRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParam(List<LanguageEntity> entities) {
        this.entities = entities;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable() {
        return repository.create(entities);
    }
}
