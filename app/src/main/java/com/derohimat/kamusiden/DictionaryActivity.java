package com.derohimat.kamusiden;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;

import com.derohimat.kamusiden.adapter.WordAdapter;
import com.derohimat.kamusiden.db.DictionaryHelper;
import com.derohimat.kamusiden.model.WordDao;

import java.util.ArrayList;

import static com.derohimat.kamusiden.db.DbContract.TABLE_EN;
import static com.derohimat.kamusiden.db.DbContract.TABLE_ID;

/**
 * Created by denirohimat on 27/03/18.
 */

public class DictionaryActivity extends AppCompatActivity {

    private RadioButton rbEn, rbId;
    private EditText inpSearch;
    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private DictionaryHelper dictionaryHelper;
    private ArrayList<WordDao> list = new ArrayList<>();
    private boolean isEnglishSelected = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        rbEn = findViewById(R.id.rbEnId);
        rbId = findViewById(R.id.rbIdEn);
        inpSearch = findViewById(R.id.inpSearch);
        recyclerView = findViewById(R.id.recyclerView);

        dictionaryHelper = new DictionaryHelper(this);
        adapter = new WordAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        adapter.addItem(list);

        inpSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchData(s.toString());
            }
        });
    }

    private void searchData(String query) {
        String table;
        isEnglishSelected = rbEn.isChecked() && !rbId.isChecked();

        if (isEnglishSelected) {
            table = TABLE_EN;
        } else {
            table = TABLE_ID;
        }
        dictionaryHelper.open();
        list = dictionaryHelper.searchWord(table, query);
        dictionaryHelper.close();

        adapter.addItem(list);
    }

}
