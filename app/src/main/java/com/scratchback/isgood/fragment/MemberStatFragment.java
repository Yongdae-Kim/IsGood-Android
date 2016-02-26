package com.scratchback.isgood.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.common.collect.Lists;
import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MainActivity;
import com.scratchback.isgood.activity.MemberDetailActivity;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Element;
import com.scratchback.isgood.vo.Member;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.github.mikephil.charting.animation.Easing.EasingOption;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberStatFragment extends Fragment {

    private View view;

    @Bind(R.id.member_stats_chart)
    RadarChart memberStatsChart;

    public MemberStatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_member_stat, container, false);
        }
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle args = getArguments();
        if (args != null) {
            Member member = (Member) args.getSerializable(MemberDetailActivity.ARGUMENT_MEMBER);
            initMemberStatsChart(member);
        }
    }

    private void initMemberStatsChart(Member member) {
        memberStatsChart.setDescription("");
        // 항목 구분 선 굵기
        memberStatsChart.setWebLineWidth(1.5f);
        memberStatsChart.setWebLineWidthInner(0.75f);

        setData(member);

        memberStatsChart.animateXY(
                1400, 1400,
                EasingOption.EaseInOutQuad,
                EasingOption.EaseInOutQuad);

        // 항목 관련
        XAxis xAxis = memberStatsChart.getXAxis();
        xAxis.setTextSize(15f);

        // 항목 수치 관련
        YAxis yAxis = memberStatsChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(10f);
        yAxis.setAxisMinValue(0f);
        yAxis.setAxisMaxValue(100f);

        Legend l = memberStatsChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    public void setData(Member member) {
        ArrayList<Entry> elementPointAvgs = Lists.newArrayList();
        ArrayList<String> elementNames = Lists.newArrayList();

        List<Element> elements = member.getElements();

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            elementPointAvgs.add(new Entry(element.getPointAvg(), i));
            elementNames.add(element.getSpecifiedName());
        }

        RadarDataSet set1 = new RadarDataSet(elementPointAvgs, member.getName());
        set1.setColor(Color.parseColor(MainActivity.SUCCESS_COLOR));
        set1.setFillColor(Color.parseColor(MainActivity.SUCCESS_COLOR));
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        ArrayList<IRadarDataSet> sets = Lists.newArrayList();
        sets.add(set1);

        RadarData data = new RadarData(elementNames, sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);

        memberStatsChart.setData(data);
        memberStatsChart.invalidate();
    }

    @OnClick(R.id.register_stats_btn)
    public void notWorkingBtn() {
        ToastSystem.getInstacne().show(getActivity(), ToastSystem.NOT_READY_FUNCTION);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
