package com.kotakurss.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.kotakurss.app.fragment.FeedListFragment;
import com.kotakurss.app.fragment.FragmentChangeListener;

public class MainActivity extends FragmentActivity implements FragmentChangeListener {

    private FragmentManager manager;
    private Toolbar toolbar;
    private FeedListFragment feedListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedListFragment = new FeedListFragment();

        initToolbar();
        initSearch();

        manager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            manager.beginTransaction().add(R.id.container,feedListFragment).commit();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_main);
    }

    private void initSearch() {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(toolbar.getMenu().findItem(R.id.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                feedListFragment.setSearchQuery(newText);
                return false;
            }
        });
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}
