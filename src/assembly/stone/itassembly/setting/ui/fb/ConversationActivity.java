package assembly.stone.itassembly.setting.ui.fb;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity.SimpleBaseActivity;
import assembly.stone.itassembly.util.NetworkUtil;
import assembly.stone.itassembly.util.ToastUtil;

import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;

/**
 * @author linhs
 * @date: 2015年5月12日 下午11:41:51 	
 * @Description: TODO(用一句话描述该文件做什么)
 */
public class ConversationActivity extends SimpleBaseActivity {
	public static final String TAG = ConversationActivity.class.getName();
	private FeedbackAgentqq agent;
	private Conversation defaultConversation;
	private ReplyListAdapter adapter;
	private ListView replyListView;
	private EditText userReplyContentEdit;
	private Button umeng_fb_send;
	private Timer timer = null;
	private TimerTask task = null;
	private final static int SYNC_NUM = 1001;
	private final static int TIMER_NUM = 3 * 1000;
	private boolean isSync = true;

	@SuppressLint("HandlerLeak")
	protected void sendHandleMessage(Message msg) {
		super.sendHandleMessage(msg);
		switch (msg.what) {
		case SYNC_NUM:
			sync();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle bundle) {
		setContentView(R.layout.umeng_fb_activity_conversation);
		super.onCreate(bundle);
		titleBanner = R.string.fb_tx;
		setView();
		try {
			agent = new FeedbackAgentqq(this);
			defaultConversation = agent.getDefaultConversation();
			replyListView = (ListView) findViewById(R.id.umeng_fb_reply_list);
			adapter = new ReplyListAdapter(this, defaultConversation);
			replyListView.setAdapter(adapter);
			sync();
		} catch (Exception e) {
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		startTimer();
	}

	private void startTimer() {
		task = new TimerTask() {
			@Override
			public void run() {
				Message message = handler.obtainMessage();
				message.what = SYNC_NUM;
				handler.sendMessage(message);
			}
		};
		timer = new Timer();
		timer.schedule(task, 0, TIMER_NUM);
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopTimer();
	}

	private void stopTimer() {
		timer.cancel();
		timer = null;
		task.cancel();
		task = null;
	}

	@Override
	protected void onStart() {
		super.onStart();
		textsize_btn.setVisibility(View.GONE);
	}

	@Override
	public void setView() {
		userReplyContentEdit = (EditText) findViewById(R.id.umeng_fb_reply_content);
		umeng_fb_send = (Button) findViewById(R.id.umeng_fb_send);
		umeng_fb_send.setOnClickListener(this);
	}

	private void sync() {
		Conversation.SyncListener listener = new Conversation.SyncListener() {
			@Override
			public void onSendUserReply(List<Reply> replyList) {
				if (replyList == null) {
					return;
				}
				if (isSync) {
					adapter.notifyDataSetChanged();
					isSync = false;
				}
				if (replyList.size() > 0) {
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onReceiveDevReply(List<DevReply> replyList) {
				if (replyList == null) {
					return;
				}
				if (replyList.size() > 0) {
					adapter.notifyDataSetChanged();
				}
			}
		};
		defaultConversation.sync(listener);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		try {
			switch (v.getId()) {
			case R.id.umeng_fb_send:
				if (NetworkUtil.checkNetworkState(this)) {
					String content = userReplyContentEdit.getEditableText().toString().trim();
					if (TextUtils.isEmpty(content))
						return;
					userReplyContentEdit.getEditableText().clear();
					defaultConversation.addUserReply(content);
					sync();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm != null)
						imm.hideSoftInputFromWindow(userReplyContentEdit.getWindowToken(), 0);
				} else {
					ToastUtil.show(this, getResources().getString(R.string.error_connet));
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.finish();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}