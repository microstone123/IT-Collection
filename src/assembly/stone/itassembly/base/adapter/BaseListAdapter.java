package assembly.stone.itassembly.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import assembly.stone.itassembly.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public abstract class  BaseListAdapter extends BaseAdapter {

	protected LayoutInflater inflater;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public View convertView;

	public BaseListAdapter(Context mContext) {
		setBaseList(mContext);
	}

	public View getConvertView() {
		return convertView;
	}

	public void setConvertView(View convertView) {
		this.convertView = convertView;
	}

	public void setBaseList(Context mContext) {
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ico_default)
				.showImageForEmptyUri(R.drawable.ico_default).showImageOnFail(R.drawable.ico_default)
				.cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(3)).build();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return convertView;
	}

	@SuppressLint("InflateParams")
	public ViewHolder getViewHolder() {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.various_item, null);
			holder.item_img = (ImageView) convertView.findViewById(R.id.item_img);
			holder.title_text = (TextView) convertView.findViewById(R.id.title_text);
			holder.summary_text = (TextView) convertView.findViewById(R.id.summary_text);
			holder.writer_name = (TextView) convertView.findViewById(R.id.writer_name);
			holder.create_time = (TextView) convertView.findViewById(R.id.create_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return holder;
	}

	protected class ViewHolder {
		public ImageView item_img;
		public TextView title_text, summary_text, writer_name, create_time;
	}
}