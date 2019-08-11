package com.tylx.leasephone.test;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.tylx.leasephone.R;

/**
 * @描述 TODO
 * @项目名称 App_imooc
 * @包名 com.android.imooc.gallery
 * @类名 GalleryActivity
 * @author chenlin
 * @date 2012年6月5日 下午9:16:33
 * @version 1.0
 */

@SuppressWarnings("all")
public class GalleryActivity extends Activity {
	private GalleryView mGallery;
	private int mResIds[] = {
			R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		mGallery = (GalleryView) findViewById(R.id.galleryView);
		mGallery.setAdapter(new GalleryAdapter());
	}

	private class GalleryAdapter extends BaseAdapter {
		LruCache<String, Bitmap> mCache ;
		String key = "key";
		
		public GalleryAdapter(){
			if (mCache == null) {
	            // 最大使用的内存空间
	            int maxSize = (int) (Runtime.getRuntime().freeMemory() / 4);
	            mCache = new LruCache<String, Bitmap>(maxSize) {
	                @Override
	                protected int sizeOf(String key, Bitmap value) {
	                    return value.getRowBytes() * value.getHeight();
	                }
	            };
	        }
		}
		
		@Override
		public int getCount() {
			return mResIds.length;
		}

		@Override
		public Object getItem(int position) {
			return mResIds[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = null;
			if (convertView == null) {
				iv = new ImageView(GalleryActivity.this);
			}else {
				iv = (ImageView) convertView;
			}
			
			Bitmap bm = mCache.get(key);
	        if (bm == null) {
	        	bm =  ImageUtil.getReverseBitmapById(GalleryActivity.this, mResIds[position]);
	        }else {
				mCache.put(key, bm);
			}
			
			//去除锯齿
			BitmapDrawable bd = new BitmapDrawable(bm);
			bd.setAntiAlias(true);
			iv.setImageDrawable(bd);
			
			LayoutParams params = new LayoutParams(160, 260);
			iv.setLayoutParams(params);
			iv.setPadding(0, 0, 10, 0);
			iv.setScaleType(ScaleType.FIT_XY);
			return iv;
		}

	}
}
