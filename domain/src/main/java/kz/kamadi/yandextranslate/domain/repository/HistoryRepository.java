package kz.kamadi.yandextranslate.domain.repository;

import java.util.List;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;

public interface HistoryRepository {

    Observable<HistoryEntity> create(HistoryEntity entity);

    Observable<Boolean> delete(HistoryEntity entity);

    Observable<List<HistoryEntity>> getHistories(int offset, int limit);

    Observable<Boolean> deleteAll();

    Observable<List<HistoryEntity>> search(String text);
}
