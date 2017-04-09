package kz.kamadi.yandextranslate.domain.repository;

import java.util.List;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;

public interface FavouriteRepository {

    Observable<List<HistoryEntity>> getFavourites(int offset, int limit);

    Observable<Boolean> add(HistoryEntity entity);

    Observable<Boolean> delete(HistoryEntity entity);

    Observable<Integer> deleteAll();

    Observable<List<HistoryEntity>> search(String text);
}
