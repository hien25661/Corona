package app.free.corona.virus.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by martinsandstrom on 2014-04-21.
 */
public class KeyboardUtils {
    /**
     * Hide keyboard.
     *
     * <pre>
     * <code>KeyboardUtils.hideKeyboard(getActivity(), searchField);</code>
     * </pre>
     *
     * @param context
     * @param field
     */
    public static void hideKeyboard(Context context, EditText field) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
    }
    /**
     * Show keyboard with a 100ms delay.
     *
     * <pre>
     * <code>KeyboardUtils.showDelayedKeyboard(getActivity(), searchField);</code>
     * </pre>
     *
     * @param v
     */


    public static void hideSoftKeyboardView(View v) {

        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showSoftKeyboardView(View v) {

        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }
    public static void showDelayedKeyboard (Context context, View view) {
        showDelayedKeyboard(context, view, 100);
    }
    /**
     * Show keyboard with a custom delay.
     *
     * <pre>
     * <code>KeyboardUtils.showDelayedKeyboard(getActivity(), searchField, 500);</code>
     * </pre>
     *
     * @param context
     * @param view
     * @param delay
     */
    public static void showDelayedKeyboard (final Context context, final View view, final int delay) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }.execute();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    }


}
