package assembly.stone.itassembly.base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import assembly.stone.itassembly.R;

public class ErrorConnetFragment extends Fragment implements OnClickListener {

	private RelativeLayout err_relat;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.err_connet, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		err_relat = (RelativeLayout) getActivity().findViewById(R.id.err_relat);
		err_relat.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.err_relat:
			startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
			break;
		default:
			break;
		}
	}
}
