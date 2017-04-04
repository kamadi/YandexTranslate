package kz.kamadi.yandextranslate.domain.interactor.language;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class GetLanguagesUseCase extends UseCase<List<LanguageEntity>> {

    private LanguageRepository repository;

    @Inject
    public GetLanguagesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LanguageRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<LanguageEntity>> buildUseCaseObservable() {
        return repository.getLanguages();
    }
}