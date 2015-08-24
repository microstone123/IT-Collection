package assembly.stone.itassembly.baiduvarious.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import assembly.stone.itassembly.baiduvarious.entity.VariousModel;
import assembly.stone.itassembly.base.adapter.BaseListAdapter;

public class VariousAdapter extends BaseListAdapter {

	private List<VariousModel> variousModelList;

	public VariousAdapter(Context mContext, List<VariousModel> list) {
		super(mContext);
		this.variousModelList = list;
	}

	@Override
	public int getCount() {
		return variousModelList.size();
	}

	public void addVariousModel(VariousModel variousModel) {
		this.variousModelList.add(variousModel);
		notifyDataSetChanged();
	}

	public void setData(List<VariousModel> variousModelList) {
		this.variousModelList = variousModelList;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return variousModelList.get(position);
	}

	public VariousModel getData(int position) {
		return variousModelList.get(position);
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
		final VariousModel variousModel = variousModelList.get(position);
		holder.writer_name.setText(variousModel.getM_writer_name());
		holder.create_time.setText(variousModel.getM_create_time());
		holder.title_text.setText(variousModel.getM_title());
		holder.summary_text.setText(variousModel.getM_summary());
		imageLoader.displayImage(variousModel.getM_image_url(), holder.item_img, options);
		return getConvertView();
	}
}
