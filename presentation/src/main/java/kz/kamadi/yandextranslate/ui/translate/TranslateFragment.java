package kz.kamadi.yandextranslate.ui.translate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.Gson;

import butterknife.BindView;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Dictionary;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.widgets.DictionaryView;

public class TranslateFragment extends BaseFragment {

    @BindView(R.id.dictionary_view)
    DictionaryView dictionaryView;

    @Override
    protected int layoutId() {
        return R.layout.fragment_translate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String json = "{ \"head\": {}, \"def\": [ { \"text\": \"travel\", \"pos\": \"noun\", \"ts\": \"trævl\", \"tr\": [ { \"text\": \"путешествие\", \"pos\": \"noun\", \"gen\": \"ср\", \"syn\": [ { \"text\": \"поездка\", \"pos\": \"noun\", \"gen\": \"ж\" }, { \"text\": \"командировка\", \"pos\": \"noun\", \"gen\": \"ж\" } ], \"mean\": [ { \"text\": \"journey\" }, { \"text\": \"trip\" }, { \"text\": \"business trip\" } ], \"ex\": [ { \"text\": \"time travel\", \"tr\": [ { \"text\": \"путешествие во времени\" } ] }, { \"text\": \"purpose of his travels\", \"tr\": [ { \"text\": \"цель своего путешествия\" } ] }, { \"text\": \"travel authorization\", \"tr\": [ { \"text\": \"разрешение на поездку\" } ] }, { \"text\": \"international travel\", \"tr\": [ { \"text\": \"международная командировка\" } ] } ] }, { \"text\": \"перемещение\", \"pos\": \"noun\", \"gen\": \"ср\", \"syn\": [ { \"text\": \"ход\", \"pos\": \"noun\", \"gen\": \"м\" }, { \"text\": \"движение\", \"pos\": \"noun\", \"gen\": \"ср\" }, { \"text\": \"передвижение\", \"pos\": \"noun\", \"gen\": \"ср\" } ], \"mean\": [ { \"text\": \"move\" }, { \"text\": \"movement\" } ], \"ex\": [ { \"text\": \"direction of travel\", \"tr\": [ { \"text\": \"направление перемещения\" } ] }, { \"text\": \"full travel\", \"tr\": [ { \"text\": \"полный ход\" } ] }, { \"text\": \"freedom of travel\", \"tr\": [ { \"text\": \"свобода передвижения\" } ] } ] }, { \"text\": \"проезд\", \"pos\": \"noun\", \"gen\": \"м\", \"mean\": [ { \"text\": \"fare\" } ], \"ex\": [ { \"text\": \"free travel\", \"tr\": [ { \"text\": \"бесплатный проезд\" } ] } ] }, { \"text\": \"туризм\", \"pos\": \"noun\", \"gen\": \"м\", \"mean\": [ { \"text\": \"tourism\" } ], \"ex\": [ { \"text\": \"travel industry\", \"tr\": [ { \"text\": \"индустрия туризма\" } ] } ] }, { \"text\": \"выезд\", \"pos\": \"noun\", \"gen\": \"м\", \"mean\": [ { \"text\": \"departure\" } ], \"ex\": [ { \"text\": \"travel to europe\", \"tr\": [ { \"text\": \"выезд в европу\" } ] } ] }, { \"text\": \"подача\", \"pos\": \"noun\", \"gen\": \"ж\", \"mean\": [ { \"text\": \"feed\" } ] } ] }, { \"text\": \"travel\", \"pos\": \"verb\", \"ts\": \"trævl\", \"tr\": [ { \"text\": \"путешествовать\", \"pos\": \"verb\", \"asp\": \"несов\", \"syn\": [ { \"text\": \"объездить\", \"pos\": \"verb\", \"asp\": \"сов\" } ], \"mean\": [ { \"text\": \"tour\" } ], \"ex\": [ { \"text\": \"travel light\", \"tr\": [ { \"text\": \"путешествовать налегке\" } ] } ] }, { \"text\": \"ездить\", \"pos\": \"verb\", \"asp\": \"несов\", \"syn\": [ { \"text\": \"ехать\", \"pos\": \"verb\", \"asp\": \"несов\" }, { \"text\": \"поехать\", \"pos\": \"verb\", \"asp\": \"сов\" }, { \"text\": \"проехать\", \"pos\": \"verb\", \"asp\": \"сов\" } ], \"mean\": [ { \"text\": \"ride\" }, { \"text\": \"go\" }, { \"text\": \"drive\" } ], \"ex\": [ { \"text\": \"travel abroad\", \"tr\": [ { \"text\": \"ездить за границу\" } ] }, { \"text\": \"travel far\", \"tr\": [ { \"text\": \"ехать далеко\" } ] }, { \"text\": \"travel to england\", \"tr\": [ { \"text\": \"поехать в англию\" } ] }, { \"text\": \"travel nearly\", \"tr\": [ { \"text\": \"проехать почти\" } ] } ] }, { \"text\": \"перемещаться\", \"pos\": \"verb\", \"asp\": \"несов\", \"syn\": [ { \"text\": \"двигаться\", \"pos\": \"verb\", \"asp\": \"несов\" } ], \"mean\": [ { \"text\": \"move\" } ] }, { \"text\": \"распространяться\", \"pos\": \"verb\", \"asp\": \"несов\", \"mean\": [ { \"text\": \"apply\" } ] }, { \"text\": \"странствовать\", \"pos\": \"verb\", \"asp\": \"несов\", \"mean\": [ { \"text\": \"wander\" } ] }, { \"text\": \"перебирать\", \"pos\": \"verb\", \"asp\": \"несов\", \"mean\": [ { \"text\": \"move\" } ] } ] }, { \"text\": \"travel\", \"pos\": \"adjective\", \"ts\": \"trævl\", \"tr\": [ { \"text\": \"туристический\", \"pos\": \"adjective\", \"syn\": [ { \"text\": \"дорожный\", \"pos\": \"adjective\" } ], \"mean\": [ { \"text\": \"tourist\" }, { \"text\": \"road\" } ], \"ex\": [ { \"text\": \"own travel agency\", \"tr\": [ { \"text\": \"собственное туристическое агентство\" } ] }, { \"text\": \"travel management company\", \"tr\": [ { \"text\": \"туристическая компания\" } ] }, { \"text\": \"travel agent\", \"tr\": [ { \"text\": \"туристический агент\" } ] }, { \"text\": \"travel alarm clock\", \"tr\": [ { \"text\": \"дорожный будильник\" } ] } ] }, { \"text\": \"проездной\", \"pos\": \"adjective\", \"syn\": [ { \"text\": \"путевой\", \"pos\": \"adjective\" } ], \"mean\": [ { \"text\": \"itinerary\" } ], \"ex\": [ { \"text\": \"official travel document\", \"tr\": [ { \"text\": \"официальный проездной документ\" } ] }, { \"text\": \"travel order\", \"tr\": [ { \"text\": \"путевой лист\" } ] } ] } ] } ] }";
        Dictionary dictionary = new Gson().fromJson(json,Dictionary.class);
        dictionaryView.setDictionary(dictionary);
    }
}
