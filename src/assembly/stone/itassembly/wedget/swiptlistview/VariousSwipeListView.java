package assembly.stone.itassembly.wedget.swiptlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import assembly.stone.itassembly.R;

public class VariousSwipeListView extends ListView {
	private View mHeadView;
	private LinearLayout various_head_layout;

	public VariousSwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView(context);
	}

	public void setHeadGone() {
		various_head_layout.setVisibility(View.GONE);
	}

	@SuppressLint("InflateParams")
	public void initHeadView(Context context) {
		mHeadView = LayoutInflater.from(context).inflate(R.layout.swipe_head, null);
		various_head_layout = (LinearLayout) mHeadView.findViewById(R.id.various_head_layout);
		addHeaderView(mHeadView, null, false);// 将初始好的ListView add进拖拽ListView
	}
}
