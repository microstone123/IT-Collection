package assembly.stone.itassembly.wedget.swiptlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.util.DateUtils;

public class WallstreetcnSwipeListView extends ListView {
	private View mHeadView;
	private TextView system_date;

	public WallstreetcnSwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView(context);
	}

	@SuppressLint("InflateParams")
	public void initHeadView(Context context) {
		mHeadView = LayoutInflater.from(context).inflate(R.layout.wallstreetcn_item, null);
		system_date = (TextView) mHeadView.findViewById(R.id.system_date);
		addHeaderView(mHeadView, null, false);// 将初始好的ListView add进拖拽ListView
		system_date.setText(DateUtils.getSysDate(DateUtils.TIME_FROMATE_DEF));
	}
}
