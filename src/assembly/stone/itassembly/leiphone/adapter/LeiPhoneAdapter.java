package assembly.stone.itassembly.leiphone.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import assembly.stone.itassembly.base.adapter.BaseListAdapter;
import assembly.stone.itassembly.leiphone.entity.LeiphoneModel;

public class LeiPhoneAdapter extends BaseListAdapter {

	private List<LeiphoneModel> leiphoneModelList;

	public LeiPhoneAdapter(Context mContext, List<LeiphoneModel> list) {
		super(mContext);
		this.leiphoneModelList = list;
	}

	@Override
	public int getCount() {
		return leiphoneModelList.size();
	}

	public void addLeiphoneModel(LeiphoneModel leiphoneModel) {
		this.leiphoneModelList.add(leiphoneModel);
		notifyDataSetChanged();
	}

	public void setData(List<LeiphoneModel> leiphoneModelList) {
		this.leiphoneModelList = leiphoneModelList;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return leiphoneModelList.get(position);
	}

	public LeiphoneModel getData(int position) {
		return leiphoneModelList.get(position);
	}

	public int getTid(int position) {
		return leiphoneModelList.get(position).getTid();
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
		final LeiphoneModel leiphoneModel = leiphoneModelList.get(position);
		holder.writer_name.setText(leiphoneModel.getUname());
		holder.title_text.setText(leiphoneModel.getTitle());
		holder.summary_text.setText(leiphoneModel.getSummary());
		String picImg = leiphoneModel.getCover().replace("210_140", "pic");
		imageLoader.displayImage(picImg, holder.item_img, options);
		holder.create_time.setText(leiphoneModel.getDate() + "  ");
		return getConvertView();
	}

}
