package com.ns.greg.easyinputfilter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.widget.EditText;
import com.ns.greg.library.easy_input_filter.EasyInputFilter;

/**
 * @author Gregory
 * @since 2017/8/9
 */

public class DemoActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_demo);

    ((EditText) findViewById(R.id.digit_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(10).setFilterType(EasyInputFilter.DIGIT).build()
    });

    ((EditText) findViewById(R.id.letter_lc_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(10)
            .setFilterType(EasyInputFilter.LETTER)
            .setLetterType(EasyInputFilter.LOWERCASE).build()
    });

    ((EditText) findViewById(R.id.alphanumeric_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(20)
            .setFilterType(EasyInputFilter.ALPHANUMERIC).build()
    });

    ((EditText) findViewById(R.id.custom_et)).setFilters(new InputFilter[] {
        new EasyInputFilter.Builder().setMaxLength(20)
            .setFilterType(
                EasyInputFilter.DIGIT | EasyInputFilter.LETTER | EasyInputFilter.SPACE).build()
    });
  }
}
