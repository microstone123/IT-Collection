package assembly.stone.itassembly.animetaste.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.adapter.BaseListAdapter;

public class AnimentPopuAdapter extends BaseListAdapter {

	private String[] list;

	public AnimentPopuAdapter(Context mContext) {
		super(mContext);
		this.list = new String[] { mContext.getResources().getString(R.string.story_mode),
				mContext.getResources().getString(R.string.mv_mode),
				mContext.getResources().getString(R.string.record_mode),
				mContext.getResources().getString(R.string.theater_mode),
				mContext.getResources().getString(R.string.cg_mode),
				mContext.getResources().getString(R.string.advert_mode),
				mContext.getResources().getString(R.string.notice_mode) };
	}

	@Override
	public int getCount() {
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		return list[position];
	}

	public String getItemMode(int position) {
		return list[position];
	}

	public String getData(int position) {
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.animet_popu_item, null);
			holder.animet_mode = (TextView) convertView.findViewById(R.id.animet_mode);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.animet_mode.setText(list[position]);
		return convertView;
	}

	protected class ViewHolder {
		public TextView animet_mode;
	}
}
