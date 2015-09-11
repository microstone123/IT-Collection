package assembly.stone.itassembly.animetaste.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.animetaste.adapter.AnimentPopuAdapter;
import assembly.stone.itassembly.animetaste.entity.OnAnimetPopuWinItemClick;

import com.umeng.analytics.MobclickAgent;

public class AnimetPopuWin extends PopupWindow {
	private View mMenuView;
	private OnAnimetPopuWinItemClick onAnimetPopuWinItemClick;
	private AnimentPopuAdapter adapter;
	private ListView animet_list;
	private final static int POPU_ITEM_WIDTH = 200;
	private Context mContext;

	@SuppressWarnings("deprecation")
	@SuppressLint({ "InflateParams", "NewApi" })
	public AnimetPopuWin(Context context) {
		super(context);
		this.mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.animet_popu, null);
		animet_list = (ListView) mMenuView.findViewById(R.id.animet_list);
		this.setBackgroundDrawable(new BitmapDrawable());
		this.setOutsideTouchable(true);
		setAnimationStyle(R.style.AnimetPopuWinStyle);
		adapter = new AnimentPopuAdapter(context);
		animet_list.setAdapter(adapter);
		this.setContentView(mMenuView);
		this.setWidth(POPU_ITEM_WIDTH);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		animet_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
				int mode_code = 1;
				switch (i) {
				case 0:
					mode_code = 1;
					MobclickAgent.onEvent(mContext, "story");
					break;
				case 1:
					mode_code = 2;
					MobclickAgent.onEvent(mContext, "mv");
					break;
				case 2:
					mode_code = 10;
					MobclickAgent.onEvent(mContext, "record");
					break;
				case 3:
					mode_code = 11;
					MobclickAgent.onEvent(mContext, "theater");
					break;
				case 4:
					mode_code = 5;
					MobclickAgent.onEvent(mContext, "gameCG");
					break;
				case 5:
					mode_code = 3;
					MobclickAgent.onEvent(mContext, "advert");
					break;
				case 6:
					mode_code = 9;
					MobclickAgent.onEvent(mContext, "notice");
					break;
				default:
					break;
				}
				onAnimetPopuWinItemClick.onItemClick(adapter.getItemMode(i), mode_code);
				dismiss();
			}
		});
	}

	public OnAnimetPopuWinItemClick getOnAnimetPopuWinItemClick() {
		return onAnimetPopuWinItemClick;
	}

	public void setOnAnimetPopuWinItemClick(OnAnimetPopuWinItemClick onAnimetPopuWinItemClick) {
		this.onAnimetPopuWinItemClick = onAnimetPopuWinItemClick;
	}

}
