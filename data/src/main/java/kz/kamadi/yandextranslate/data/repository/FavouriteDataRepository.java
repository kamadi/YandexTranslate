package kz.kamadi.yandextranslate.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.database.history.HistoryDao;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.repository.FavouriteRepository;

public class FavouriteDataRepository implements FavouriteRepository {

    private HistoryDao historyDao;
    private HistoryEntityMapper historyEntityMapper;
    private HistoryDataMapper historyDataMapper;

    @Inject
    public FavouriteDataRepository(HistoryDao historyDao, HistoryEntityMapper historyEntityMapper, HistoryDataMapper historyDataMapper) {
        this.historyDao = historyDao;
        this.historyEntityMapper = historyEntityMapper;
        this.historyDataMapper = historyDataMapper;
    }

    @Override
    public Observable<List<HistoryEntity>> getFavourites(int offset, int limit) {
        return Observable.fromCallable(() -> historyEntityMapper.transform(historyDao.getHistories(offset, limit, true)));
    }

    @Override
    public Observable<Boolean> update(HistoryEntity entity) {
        return Observable.fromCallable(() -> historyDao.update(historyDataMapper.transform(entity)));
    }

    @Override
    public Observable<Boolean> delete(HistoryEntity entity) {
        return Observable.fromCallable(() -> historyDao.delete(historyDataMapper.transform(entity)));
    }

    @Override
    public Observable<Integer> deleteAll() {
        return Observable.fromCallable(() -> historyDao.deleteAll(true));
    }

    @Override
    public Observable<List<HistoryEntity>> search(String text) {
        return Observable.fromCallable(() -> historyEntityMapper.transform(historyDao.search(text, true)));
    }
}
