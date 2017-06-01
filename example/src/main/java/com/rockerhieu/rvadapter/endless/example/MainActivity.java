/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.rockerhieu.rvadapter.endless.example;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;
import com.rockerhieu.rvadapter.endless.example.adapter.SimpleStringAdapter;
import com.rockerhieu.rvadapter.endless.example.decorator.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements EndlessRecyclerViewAdapter.RequestToLoadMoreListener {
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private SimpleStringAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        adapter = new SimpleStringAdapter(null);
        endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(adapter, this);

        // Optional
        endlessRecyclerViewAdapter.setPendingViewId(R.layout.custom_pending_view);

        rv.setAdapter(endlessRecyclerViewAdapter);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onLoadMoreRequested() {
        new AsyncTask<Void, Void, List>() {
            @Override
            protected List doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Arrays.asList(
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese(),
                        randomCheese());
            }

            @Override
            protected void onPostExecute(List list) {
                adapter.appendItems(list);
                if (adapter.getItemCount() >= 50) {
                    // load 100 items for demo only
                    endlessRecyclerViewAdapter.onDataReady(false);
                } else {
                    // notify the data is ready
                    endlessRecyclerViewAdapter.onDataReady(true);
                }
            }
        }.execute();
    }

    public void onResetClicked(View view) {
        adapter.clear();
        endlessRecyclerViewAdapter.restartAppending();
    }

    static Random random = new Random();

    static String randomCheese() {
        return Cheeses.sCheeseStrings[random.nextInt(Cheeses.sCheeseStrings.length)];
    }
}
