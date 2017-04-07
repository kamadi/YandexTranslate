package kz.kamadi.yandextranslate.data.database.history;

import java.util.List;

import kz.kamadi.yandextranslate.data.entity.History;

public interface HistoryDao {

    History create(History entity);

    boolean update(History entity);

    boolean delete(History history,boolean isFavourite);

    boolean deleteAll(boolean isFavourite);

    List<History> getHistories(int offset,int limit,boolean isFavourite);

    List<History> search(String text, boolean isFavourite);
}
