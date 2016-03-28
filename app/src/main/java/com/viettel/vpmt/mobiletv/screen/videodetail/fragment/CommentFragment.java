package com.viettel.vpmt.mobiletv.screen.videodetail.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viettel.vpmt.mobiletv.R;

/**
 * Created by ThanhTD on 3/23/2016.
 */
public class CommentFragment extends Fragment
{
    public static CommentFragment newInstance()
    {
        return new CommentFragment();
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
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }
}
