package kz.kamadi.yandextranslate.domain.interactor.language;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class DownloadLanguagesUseCase extends UseCase<List<LanguageEntity>> {

    private LanguageRepository repository;

    @Inject
    public DownloadLanguagesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,@Named("remote") LanguageRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<LanguageEntity>> buildUseCaseObservable() {
        return repository.getLanguages();
    }
}