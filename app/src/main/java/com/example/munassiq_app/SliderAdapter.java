package com.example.munassiq_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;

    }

    public int[] slide_images = {

            R.drawable.cogwheels,
            R.drawable.audit,
            R.drawable.yes,

    };

    public String[] slide_headings = {

            "تفعيل",
            "اظهار الكشف",
            "حفظ النتيجة"

    };

    public int[] slide_descs = {

//            "خانة جنب هذا الزر خاصة لإدخال رقم الوحدة. الوحد قبل العاشرة تكتب مع الصفر قبل رقم الوحدة. مثلا: الوحدة الأولى تكتب 01. بعد ضغط على الزر يتم اختيار اسم الطالب من صندوق التحرير أدناه.",
//            "إذا ترغب في معرفة أسماء الطلاب و أرقام غرفهم في الوحدة المعينة, يرجى كتابة رقم الوحدة و ضغط على هئا الزر.",
//            "بعد كتابة الحاجة أو الإقتراح أو إذغ كان طالب غائب أو لا يريد شيئا حاليا, يرجى ضغط على هذ الزر."

            R.string.descBtnDo,
            R.string.descBtnShow,
            R.string.descBtnSave


    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageBtn = view.findViewById(R.id.imageBtn);
        TextView nameBtn = view.findViewById(R.id.nameBtn);
        TextView descBtn = view.findViewById(R.id.descBtn);

        imageBtn.setImageResource(slide_images[position]);
        nameBtn.setText(slide_headings[position]);
        descBtn.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
