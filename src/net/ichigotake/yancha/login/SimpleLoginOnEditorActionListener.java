package net.ichigotake.yancha.login;

import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

class SimpleLoginOnEditorActionListener implements OnEditorActionListener {

	final private SendSimpleLogin mLogin;
	
	SimpleLoginOnEditorActionListener(FragmentActivity activity) {
		mLogin = new SendSimpleLogin(activity);
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		mLogin.send();
		return false;
	}
}