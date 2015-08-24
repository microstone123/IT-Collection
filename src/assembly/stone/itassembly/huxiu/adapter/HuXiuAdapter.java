package assembly.stone.itassembly.huxiu.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import assembly.stone.itassembly.base.adapter.BaseListAdapter;
import assembly.stone.itassembly.huxiu.entity.HuXiuModel;
import assembly.stone.itassembly.util.DateUtils;

public class HuXiuAdapter extends BaseListAdapter {

	private List<HuXiuModel> huXiuModelModelList;

	public HuXiuAdapter(Context mContext, List<HuXiuModel> list) {
		super(mContext);
		this.huXiuModelModelList = list;
	}

	@Override
	public int getCount() {
		return huXiuModelModelList.size();
	}

	public void addHuXiuModel(HuXiuModel huXiuModelModel) {
		this.huXiuModelModelList.add(huXiuModelModel);
		notifyDataSetChanged();
	}

	public void setData(List<HuXiuModel> huXiuModelModelList) {
		this.huXiuModelModelList = huXiuModelModelList;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return huXiuModelModelList.get(position);
	}

	public HuXiuModel getData(int position) {
		return huXiuModelModelList.get(position);
	}

	public int getAid(int position) {
		return huXiuModelModelList.get(position).getAid();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		setConvertView(convertView);
		ViewHolder holder = getViewHolder();
		final HuXiuModel huXiuModelModel = huXiuModelModelList.get(position);
		holder.writer_name.setText(huXiuModelModel.getAuthor());
		holder.title_text.setText(huXiuModelModel.getTitle());
		holder.summary_text.setText(huXiuModelModel.getSummary());
		imageLoader.displayImage(huXiuModelModel.getImg(), holder.item_img, options);
		holder.create_time.setText(DateUtils.getStrTime(huXiuModelModel.getUpdateline(), "yyyy-MM-dd HH:mm") + " ");
		return getConvertView();
	}

}