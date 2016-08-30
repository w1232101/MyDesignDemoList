package com.example.administrator.designdemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 16/8/7 21:18
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment {

    private View parentView;

    private FragmentActivity activity;

    private LayoutInflater inflater;

    public abstract int getLayoutResId();

    public abstract void finishCreateView(Bundle state);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

        this.inflater = inflater;
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {

        super.onDetach();
        this.activity = null;
    }

    public FragmentActivity getSupportActivity() {

        return (FragmentActivity) super.getActivity();
    }

    public android.app.ActionBar getSupportActionBar() {

        return getSupportActivity().getActionBar();
    }

    public Context getApplicationContext() {

        return this.activity == null ? (getActivity() == null ? null :
                getActivity().getApplicationContext()) : this.activity.getApplicationContext();
    }

    protected LayoutInflater getLayoutInflater() {

        return inflater;
    }

    protected View getParentView() {

        return parentView;
    }

    public <T extends View> T $(int id) {

        return (T) parentView.findViewById(id);
    }
}
