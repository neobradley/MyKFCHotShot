package kfc.mykfchotshot;

public class Misc {
	
/*
 * TO CHANGE THE MENU BACKGROUND
 * 
 * 	protected void setMenuBackground() {

		getLayoutInflater().setFactory(new Factory() {

			public View onCreateView(String name, Context context,
					AttributeSet attrs) {

				if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")) {

					try { // Ask our inflater to create the view
						LayoutInflater f = getLayoutInflater();
						final View view = f.createView(name, null, attrs);
						
						 //The background gets refreshed each time a new item is
						 //added the options menu. So each time Android applies
						 //the default background we need to set our own
						 //background. This is done using a thread giving the
						 //background change as runnable object
						 
						new Handler().post(new Runnable() {
							public void run() {
								// view.setBackgroundResource(
								// R.drawable.background);
								view.setBackgroundColor(Color.BLACK);
							}
						});
						return view;
					} catch (InflateException e) {
					} catch (ClassNotFoundException e) {
					}
				}
				return null;
			}
		});
	}
 * */
}
