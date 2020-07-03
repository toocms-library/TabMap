package com.toocms.tab.map.choosing.poi.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.toocms.tab.map.R;
import com.toocms.tab.map.TabMapApi;
import com.toocms.tab.map.choosing.poi.PoiItemDecoration;
import com.toocms.tab.map.choosing.poi.PoiListAdapter;
import com.toocms.tab.ui.BaseActivity;
import com.toocms.tab.ui.BasePresenter;

/**
 * 根据关键字搜索poi
 * <p>
 * Author：Zero
 * Date：2020/6/29 11:02
 *
 * @version v1.0
 */

public class ObtainSearchPoiAty extends BaseActivity implements TextWatcher, View.OnKeyListener, View.OnClickListener {

    private LinearLayout titlebar;
    private ImageView imgvBack;
    private EditText etxtSearch;
    private RecyclerView recyclerView;
    private PoiListAdapter adapter;

    private int backResId;
    private int titlebarBgColor;
    private int searchResId;
    private String cityCode;

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        // 标题栏
        mActionBar.hide();
        // 初始化控件
        initControls();
        // 初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        PoiItemDecoration decoration = new PoiItemDecoration(this);
        recyclerView.addItemDecoration(decoration);
        adapter = new PoiListAdapter(this, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_obtain_search_poi;
    }

    @Override
    protected void initialized() {
        Bundle bundle = getIntent().getExtras();
        backResId = bundle.getInt("BackResId");
        titlebarBgColor = bundle.getInt("TitlebarBgColor");
        searchResId = bundle.getInt("SearchResId");
        cityCode = bundle.getString("cityCode");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void requestData() {
    }

    private void initControls() {
        titlebar = findViewById(R.id.obtain_search_poi_titlebar);
        titlebar.setBackgroundResource(titlebarBgColor);
        imgvBack = findViewById(R.id.obtain_search_poi_back);
        imgvBack.setImageResource(backResId);
        imgvBack.setOnClickListener(this);
        etxtSearch = findViewById(R.id.obtain_search_poi_search);
        etxtSearch.setCompoundDrawablesWithIntrinsicBounds(searchResId, 0, 0, 0);
        etxtSearch.addTextChangedListener(this);
        etxtSearch.setOnKeyListener(this);
        recyclerView = findViewById(R.id.obtain_search_poi_list);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        doSearch(s.toString());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                doSearch(etxtSearch.getText().toString());
                // 隐藏键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
        return false;
    }

    private void doSearch(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            adapter.setData(null);
            return;
        }
        TabMapApi.getPoiApi(this).doSearchPoi(
                keyword,
                null,
                cityCode,
                1,
                40,
                new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        adapter.setData(poiResult.getPois());
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {
                    }
                });
    }
}
