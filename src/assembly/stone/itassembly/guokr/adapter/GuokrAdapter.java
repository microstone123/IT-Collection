package assembly.stone.itassembly.guokr.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import assembly.stone.itassembly.base.adapter.BaseListAdapter;
import assembly.stone.itassembly.guokr.entity.GuoKrModel;
import assembly.stone.itassembly.util.DateUtils;

public class GuokrAdapter extends BaseListAdapter {

	private List<GuoKrModel> guoKrModelModelList;

	public GuokrAdapter(Context mContext, List<GuoKrModel> list) {
		super(mContext);
		this.guoKrModelModelList = list;
	}

	@Override
	public int getCount() {
		return guoKrModelModelList.size();
	}

	public void addGuoKrModel(GuoKrModel guoKrModelModel) {
		this.guoKrModelModelList.add(guoKrModelModel);
		notifyDataSetChanged();
	}

	public void setData(List<GuoKrModel> guoKrModelModelList) {
		this.guoKrModelModelList = guoKrModelModelList;
		notifyDataSetChanged();
	}

	public String getDatePicked() {
		return String.valueOf(guoKrModelModelList.get(this.getCount() - 1).getDate_picked());
	}

	@Override
	public Object getItem(int position) {
		return guoKrModelModelList.get(position);
	}

	public GuoKrModel getData(int position) {
		return guoKrModelModelList.get(position);
	}

	public int getAid(int position) {
		return guoKrModelModelList.get(position).getId();
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
		final GuoKrModel guoKrModelModel = guoKrModelModelList.get(position);
		holder.writer_name.setText(guoKrModelModel.getAuthor());
		holder.title_text.setText(guoKrModelModel.getTitle());
		holder.summary_text.setText(guoKrModelModel.getSummary());
		imageLoader.displayImage(guoKrModelModel.getHeadline_img(), holder.item_img, options);
		holder.create_time.setText(DateUtils.getStrTime(String.valueOf(guoKrModelModel.getDate_picked()),
				"yyyy-MM-dd HH:mm") + " ");
		return getConvertView();
	}

}
