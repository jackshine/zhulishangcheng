package com.example.ddm.appui.adapter;

import java.util.List;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.example.ddm.R;


public class FunnyGridAdapter extends CommonBaseAdapter<String> {


	public FunnyGridAdapter(Context context, List<String> dataList) {
		super(context, dataList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view= (View) convertView.inflate(context, R.layout.default_img, null);
		 ImageView imageView= (ImageView) view.findViewById(R.id.image);
//		imageLoader.displayImage(datas.get(position), imageView, options);
		Glide.with(context).load(mDataList.get(position)).placeholder(R.drawable.ic_launcher).crossFade().into(imageView);

//		imageLoader.getInstance().loadImage(NetUtils.IMAGE_BASE_URL + datas.get(position),new SimpleImageLoadingListener(){
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				super.onLoadingComplete(imageUri, view, loadedImage);
//				imageView.setImageBitmap(ImageUtils.ratio(loadedImage,240f,120f));
//			}
//		});
//
//		ImageLoader.getInstance().displayImage(NetUtils.IMAGE_BASE_URL+datas.get(position),
//				imageView, new ImageLoaderPicture(context).getOptions(),
//				new SimpleImageLoadingListener());
		return view;
	}
		

}
