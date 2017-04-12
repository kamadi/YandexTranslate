package kz.kamadi.yandextranslate.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class TranslateEditText extends AppCompatEditText {

    private EditTextImeBackListener mOnImeBack;

    public TranslateEditText(Context context) {
        super(context);
    }

    public TranslateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranslateEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            if (mOnImeBack != null)
                mOnImeBack.onImeBack();
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnEditTextImeBackListener(EditTextImeBackListener listener) {
        mOnImeBack = listener;
    }

    public interface EditTextImeBackListener {
        void onImeBack();
    }
}