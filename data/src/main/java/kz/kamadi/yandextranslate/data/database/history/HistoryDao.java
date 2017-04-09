package kz.kamadi.yandextranslate.data.database.history;

import java.util.List;

import kz.kamadi.yandextranslate.data.entity.History;

public interface HistoryDao {

    History create(History history);

    boolean update(History history);

    boolean delete(History history);

    int deleteAll(boolean isFavourite);

    List<History> getHistories(int offset,int limit,boolean isFavourite);

    List<History> search(String text, boolean isFavourite);
}
