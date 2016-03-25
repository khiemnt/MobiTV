package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.RecyclerViewAdapter;
import com.viettel.vpmt.mobiletv.screen.videodetail.fragment.adapter.item.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhTD on 3/22/2016.
 */
public class VideoRelativeFragment extends Fragment
{
    public static VideoRelativeFragment newInstance()
    {
        return new VideoRelativeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_video_relative, container, false);
        List<ImageItem> imageItems = new ArrayList<>();
        ImageItem imageItem0 = new ImageItem("https://s7.postimg.org/fzx5tnbjf/741133_688523984507134_237700247_o.jpg", "First Video");
        ImageItem imageItem = new ImageItem("https://s7.postimg.org/fzx5tnbjf/741133_688523984507134_237700247_o.jpg", "Second video");
        ImageItem imageItem2 = new ImageItem("https://s7.postimg.org/fzx5tnbjf/741133_688523984507134_237700247_o.jpg", "Third video");
        ImageItem imageItem3 = new ImageItem("https://s7.postimg.org/fzx5tnbjf/741133_688523984507134_237700247_o.jpg", "Four video");
        ImageItem imageItem4 = new ImageItem("https://s7.postimg.org/fzx5tnbjf/741133_688523984507134_237700247_o.jpg", "Fifth video");
        imageItems.add(imageItem0);
        imageItems.add(imageItem);
        imageItems.add(imageItem2);
        imageItems.add(imageItem3);
        imageItems.add(imageItem4);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(imageItems, getActivity());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        return view;
    }
}
