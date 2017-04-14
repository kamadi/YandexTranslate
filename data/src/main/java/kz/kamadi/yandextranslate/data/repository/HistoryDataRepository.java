package kz.kamadi.yandextranslate.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.database.history.HistoryDao;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

public class HistoryDataRepository implements HistoryRepository {

    private HistoryDao historyDao;
    private HistoryEntityMapper historyEntityMapper;
    private HistoryDataMapper historyDataMapper;

    @Inject
    public HistoryDataRepository(HistoryDao historyDao, HistoryEntityMapper historyEntityMapper, HistoryDataMapper historyDataMapper) {
        this.historyDao = historyDao;
        this.historyEntityMapper = historyEntityMapper;
        this.historyDataMapper = historyDataMapper;
    }

    @Override
    public Observable<HistoryEntity> create(HistoryEntity entity) {
        return Observable.fromCallable(() -> historyEntityMapper.transform(historyDao.create(historyDataMapper.transform(entity))));
    }

    @Override
    public Observable<Boolean> delete(HistoryEntity entity) {
        return Observable.fromCallable(() -> historyDao.delete(historyDataMapper.transform(entity)));
    }

    @Override
    public Observable<List<HistoryEntity>> getHistories(int offset, int limit, boolean isFavourite) {
        return Observable.fromCallable(() -> historyEntityMapper.transform(historyDao.getHistories(offset, limit, isFavourite)));
    }

    @Override
    public Observable<Integer> deleteAll(boolean isFavourite) {
        return Observable.fromCallable(() -> historyDao.deleteAll(isFavourite));
    }

    @Override
    public Observable<List<HistoryEntity>> search(String text, boolean isFavourite) {
        return Observable.fromCallable(() -> historyEntityMapper.transform(historyDao.search(text, isFavourite)));
    }
}
