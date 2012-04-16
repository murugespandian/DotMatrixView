package com.escapeindustries.numericedisplay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class NumericalDisplayActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock);
		ViewGroup digitsGroup = (ViewGroup) findViewById(R.id.digits);
		// animateAllLightableDots(digits);

		ViewGroup[] digitsOnly = new ViewGroup[4];
		for (int i = 0; i < digitsGroup.getChildCount(); i++) {
			// Dirty hack to ensure we only get the digits and not
			// the colon between digits 2 and 3
			// AND ignore the spacer columns!
			if (i % 2 == 0) {
				if (i < 4) {
					digitsOnly[i / 2] = (ViewGroup) digitsGroup.getChildAt(i);
				} else if (i > 4) {
					digitsOnly[(i / 2) - 1] = (ViewGroup) digitsGroup
							.getChildAt(i);
				}
			}
		}

		DigitDisplay[] digits = new DigitDisplay[4];
		for (int i = 0; i < digits.length; i++) {
			digits[i] = new DigitDisplay(this, digitsOnly[i]);
		}

		DigitsParser parser = new DigitsParser();
		int[] values = parser.parse("00:01");
		for (int i = 0; i < values.length; i++) {
			digits[i].setNumber(values[i]);
		}

		// new DigitIncrementTask(one).execute("");
		// new DigitIncrementTask(two).execute("");
		// new DigitIncrementTask(three).execute("");
		new DigitIncrementTask(digits[3]).execute("");

	}

	private void animateAllLightableDots(ViewGroup digits) {
		for (int digit = 0; digit < digits.getChildCount(); digit++) {

			ViewGroup matrix = (ViewGroup) digits.getChildAt(digit);

			// Experiment to show you can find the items in the grid predictably
			int count = matrix.getChildCount();
			for (int i = 0; i < count; i++) {
				View item = matrix.getChildAt(i);
				int itemCount = ((ViewGroup) item).getChildCount();
				for (int j = 0; j < itemCount; j++) {
					View subItem = ((ViewGroup) item).getChildAt(j);
					if (subItem instanceof FrameLayout) {
						// Only the items inside FrameLayouts need to animate
						ImageView dot = (ImageView) ((ViewGroup) subItem)
								.getChildAt(1);
						fadeOut(dot);
					}
				}
			}
		}
	}

	private void fadeOut(ImageView dot) {
		Animation vanish = AnimationUtils.loadAnimation(this, R.anim.vanish);
		Animation appear = AnimationUtils.loadAnimation(this, R.anim.appear);
		AnimationListener vanishChain = new ChainedListener(dot, appear);
		AnimationListener appearChain = new ChainedListener(dot, vanish);
		vanish.setAnimationListener(vanishChain);
		appear.setAnimationListener(appearChain);
		dot.startAnimation(vanish);
	}
}