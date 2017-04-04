package kz.kamadi.yandextranslate.domain.interactor.translate;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.domain.repository.DictionaryRepository;
import kz.kamadi.yandextranslate.domain.repository.TranslateRepository;

public class GetTranslationUseCase extends UseCase<TranslationEntity> {

    private TranslateRepository translateRepository;
    private DictionaryRepository dictionaryRepository;
    private String text;
    private String lang;

    protected GetTranslationUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, TranslateRepository translateRepository, DictionaryRepository dictionaryRepository) {
        super(threadExecutor, postExecutionThread);
        this.translateRepository = translateRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    public void setParams(String text, String lang) {
        this.text = text;
        this.lang = lang;
    }

    @Override
    protected Observable<TranslationEntity> buildUseCaseObservable() {
        return Observable.zip(translateRepository.getTranslate(text, lang), dictionaryRepository.getDictionary(text, lang), new BiFunction<TranslateEntity, DictionaryEntity, TranslationEntity>() {
            @Override
            public TranslationEntity apply(TranslateEntity translateEntity, DictionaryEntity dictionaryEntity) throws Exception {
                return new TranslationEntity(translateEntity, dictionaryEntity);
            }
        });
    }
}
