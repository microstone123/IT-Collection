package assembly.stone.itassembly.animetaste.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import assembly.stone.itassembly.animetaste.entity.AnimetasteModel;
import assembly.stone.itassembly.base.adapter.BaseListAdapter;

public class AnimentasteAdapter extends BaseListAdapter {

	private List<AnimetasteModel> list;

	public AnimentasteAdapter(Context mContext, List<AnimetasteModel> list) {
		super(mContext);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void addAnimetasteModel(AnimetasteModel model) {
		this.list.add(model);
		notifyDataSetChanged();
	}

	public void setData(List<AnimetasteModel> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	public AnimetasteModel getData(int position) {
		return list.get(position);
	}

/*	public int getTid(int position) {
		return list.get(position).getTid();
	}*/

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		setConvertView(convertView);
		ViewHolder holder = getViewHolder();
		final AnimetasteModel model = list.get(position);
		holder.writer_name.setText(model.getAuthor()+" ");
		holder.title_text.setText(model.getName()+" ");
		holder.summary_text.setText(model.getBrief()+" ");
//		String picImg = model.getCover().replace("210_140", "pic");
		imageLoader.displayImage(model.getHomePic(), holder.item_img, options);
//		holder.create_time.setText(model.getDate()+"  ");
		return getConvertView();
	}

}
