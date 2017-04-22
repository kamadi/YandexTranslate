package kz.kamadi.yandextranslate.domain.interactor.language;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class GetTranslateLanguagesUseCase extends UseCase<Map<String, LanguageEntity>> {

    public static final String SOURCE = "source";
    public static final String TARGET = "target";

    private LanguageRepository repository;
    private String sourceLanguageCode, targetLanguageCode;

    @Inject
    public GetTranslateLanguagesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, @Named("local")LanguageRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void setParams(String sourceLanguageCode, String targetLanguageCode) {
        this.sourceLanguageCode = sourceLanguageCode;
        this.targetLanguageCode = targetLanguageCode;
    }

    @Override
    protected Observable<Map<String, LanguageEntity>> buildUseCaseObservable() {
        return Observable.zip(repository.getLanguageEntity(sourceLanguageCode), repository.getLanguageEntity(targetLanguageCode), new BiFunction<LanguageEntity, LanguageEntity, Map<String, LanguageEntity>>() {
            @Override
            public Map<String, LanguageEntity> apply(@NonNull LanguageEntity source, @NonNull LanguageEntity target) throws Exception {
                Map<String,LanguageEntity>map = new HashMap<>();
                map.put(SOURCE,source);
                map.put(TARGET,target);
                return map;
            }
        });
    }
}
