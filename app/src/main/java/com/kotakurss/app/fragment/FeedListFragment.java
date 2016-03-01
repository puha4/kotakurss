package com.kotakurss.app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.kotakurss.app.R;

public class FeedListFragment extends Fragment {
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedlist_fragment, container, false);

        // определяем массив типа String
        listView = (ListView) view.findViewById(R.id.rssListView);
        final String[] catnames = new String[] {
                "Рыжик", "Барсик", "Мурзик"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, catnames);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOtherFragment();
            }
        });


        return view;
    }

    public void showOtherFragment()
    {
        Fragment fr = new ViewerFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr);
    }
}
