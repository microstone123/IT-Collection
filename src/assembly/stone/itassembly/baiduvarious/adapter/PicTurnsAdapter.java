package assembly.stone.itassembly.baiduvarious.adapter;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.baiduvarious.entity.VariousHeadModel;
import assembly.stone.itassembly.baiduvarious.entity.VariousModel;
import assembly.stone.itassembly.baiduvarious.ui.VariousDetailActivity;

public class PicTurnsAdapter extends BaseAdapter {

	private List<VariousHeadModel> picTurnsInfoList;
	private LayoutInflater mInflater;
	private Context mContext;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public PicTurnsAdapter(Context context, List<VariousHeadModel> picTurnsInfoList) {
		this.picTurnsInfoList = picTurnsInfoList;
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.various_black)
				.showImageForEmptyUri(R.drawable.various_black).showImageOnFail(R.drawable.various_black)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE; // 返回很大的值使得getView中的position不断增大来实现循环
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	public void setData(List<VariousHeadModel> picTurnsInfoList) {
		this.picTurnsInfoList = picTurnsInfoList;
		notifyDataSetChanged();
	}

	public VariousHeadModel getVariousHeadModel(int position) {
		return picTurnsInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = mInflater.inflate(R.layout.viewflow_item, null);
			listItemView.viewflow_image = (ImageView) convertView.findViewById(R.id.viewflow_image);
			listItemView.title_text = (TextView) convertView.findViewById(R.id.viewflow_text);
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		if (picTurnsInfoList.size() != 0) {
			VariousHeadModel variousHeadModel = picTurnsInfoList.get(position % picTurnsInfoList.size());
			if (StringUtils.isNotEmpty(variousHeadModel.getSrc())) {
				listItemView.title_text.setVisibility(View.VISIBLE);
				imageLoader.displayImage(variousHeadModel.getSrc(), listItemView.viewflow_image, options);
				listItemView.title_text.setText(variousHeadModel.getTitle());
			}
		} else {
			listItemView.title_text.setVisibility(View.GONE);
		}
		listItemView.viewflow_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent();
					String url = picTurnsInfoList.get(position % picTurnsInfoList.size()).getHref();
					VariousModel variousModel = new VariousModel();
					variousModel.setM_title(picTurnsInfoList.get(position % picTurnsInfoList.size()).getTitle());
					intent.putExtra("detail_url", url);
					intent.putExtra("various_Model", variousModel);
					intent.setClass(mContext, VariousDetailActivity.class);
					mContext.startActivity(intent);
					((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		return convertView;
	}

	public final class ListItemView { // 自定义控件集合
		public ImageView viewflow_image;
		public TextView title_text;
	}

}
