package com.ns.greg.easyinputfilter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.widget.EditText;
import com.ns.greg.library.easy_input_filter.CharacterType;
import com.ns.greg.library.easy_input_filter.EasyInputFilter;

/**
 * @author Gregory
 * @since 2017/8/9
 */

public class DemoActivity extends AppCompatActivity {

  private static final char[] CUSTOM_ACCEPTOR_SET = "AaBbCc".toCharArray();
  private static final char[] CUSTOM_FILTER_SET = new char[] {
      34 /* " */, 39 /* ' */, 47 /* / */, 92 /* \ */
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_demo);
    ((EditText) findViewById(R.id.digit_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(10).setAcceptorType(CharacterType.DIGIT).build()
    });
    ((EditText) findViewById(R.id.letter_lc_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(10)
            .setAcceptorType(CharacterType.LETTER_LOWERCASE).build()
    });
    ((EditText) findViewById(R.id.alphanumeric_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(20)
            .setAcceptorType(CharacterType.ALPHANUMERIC).build()
    });
    ((EditText) findViewById(R.id.custom_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(20)
            .setAcceptorType(CharacterType.CUSTOM)
            .setCustomAcceptedCharacters(CUSTOM_ACCEPTOR_SET)
            .setFilterType(CharacterType.CUSTOM)
            .setCustomFilteredCharacters(CUSTOM_FILTER_SET).build()
    });
  }
}
