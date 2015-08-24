package assembly.stone.itassembly.wedget.viewflow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import assembly.stone.itassembly.base.ui.MainActivity;

public class TurnsViewFlow extends ViewFlow {

	public TurnsViewFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (getContext() instanceof MainActivity) {
				MainActivity m = (MainActivity) getContext();
				m.pager.requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (getContext() instanceof MainActivity) {
				MainActivity m = (MainActivity) getContext();
				m.pager.requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			if (getContext() instanceof MainActivity) {
				MainActivity m = (MainActivity) getContext();
				m.pager.requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (getContext() instanceof MainActivity) {
				MainActivity m = (MainActivity) getContext();
				m.pager.requestDisallowInterceptTouchEvent(true);
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
}