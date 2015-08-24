package assembly.stone.itassembly.wedget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import assembly.stone.itassembly.R;

public class BaseListView extends ListView {
	private View mHeadView;

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView(context);
	}

	@SuppressLint("InflateParams")
	public void initHeadView(Context context) {
		mHeadView = LayoutInflater.from(context).inflate(R.layout.head_layout, null);
		addHeaderView(mHeadView, null, false);// 将初始好的ListView add进拖拽ListView
	}
}
