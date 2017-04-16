package kz.kamadi.yandextranslate.ui.history;

import java.util.List;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public interface HistoryItemView extends BaseView {

    void showHistories(List<History> histories);

    void onHistoryDeleted(int position);
}
