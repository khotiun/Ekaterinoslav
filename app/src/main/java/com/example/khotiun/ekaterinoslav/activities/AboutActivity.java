package com.example.khotiun.ekaterinoslav.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.khotiun.ekaterinoslav.R;
import com.lb.material_preferences_library.PreferenceActivity;
import com.lb.material_preferences_library.custom_preferences.Preference;


/**
 * Created by hotun on 18.07.2017.
 */
//экран для отображения информации о приложении
public class AboutActivity extends PreferenceActivity
        implements Preference.OnPreferenceClickListener {

    public static Intent newIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_Dark);
        super.onCreate(savedInstanceState);
        //создаем ключи
        Preference prefShareKey = (Preference) findPreference(getString(R.string.pref_share_key));
        Preference prefRateReviewKey = (Preference) findPreference(getString(R.string.pref_rate_review_key));

        prefShareKey.setOnPreferenceClickListener(this);
        prefRateReviewKey.setOnPreferenceClickListener(this);
    }

    //соединяем преференс активити с преференс xml
    @Override
    protected int getPreferencesXmlId() {
        return R.xml.pref_about;
    }

    //нажатие на пункты меню с ключами
    @Override
    public boolean onPreferenceClick(android.preference.Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_share_key))) {
            //вызывается набор приложений поддерживающих шаринг им передается текст сообщения и ссылка на сообщение в маркете
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                    getString(R.string.subject));
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message) +
                    " " + getString(R.string.googleplay_url));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
            //реализуется поиск приложения в гугл плей по ссылке из ресурсов
        } else if (preference.getKey().equals(getString(R.string.pref_rate_review_key))) {
            Intent rateReviewIntent = new Intent(Intent.ACTION_VIEW);
            rateReviewIntent.setData(Uri.parse(
                    getString(R.string.googleplay_url)));
            startActivity(rateReviewIntent);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
