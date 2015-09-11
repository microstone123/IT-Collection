package assembly.stone.itassembly.wedget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import assembly.stone.itassembly.R;

public class AnimeRecomListView extends ListView {

	private View mHeadView;
	private TextView animetast_name, animetast_author, animetast_detail_tx;

	public AnimeRecomListView(Context context) {
		super(context);
		initHeadView(context);
	}
	
	public AnimeRecomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView(context);
	}

	@SuppressLint("InflateParams")
	public void initHeadView(Context context) {
		mHeadView = LayoutInflater.from(context).inflate(R.layout.animet_detail, null);
		animetast_name = (TextView) mHeadView.findViewById(R.id.animetast_name);
		animetast_author = (TextView) mHeadView.findViewById(R.id.animetast_author);
		animetast_detail_tx = (TextView) mHeadView.findViewById(R.id.animetast_detail_tx);
		addHeaderView(mHeadView, null, false);// 将初始好的ListView add进拖拽ListView
	}

	public void setDetail(String name, String auther, String detail) {
		animetast_name.setText(name);
		animetast_author.setText(auther);
		animetast_detail_tx.setText(detail);
	}

}
