package kfc.mykfchotshot;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ContactSupplierAdd extends Activity implements OnClickListener{
	private EditText name,number;
	public static int[] addContactButtons=
	{R.id.createContactSave,R.id.createContactClear};;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_book_add);
		name = (EditText) findViewById(R.id.contactName);
		number = (EditText) findViewById(R.id.contactNumber);
		for(int i:addContactButtons){
			View v = findViewById(i);
			v.setOnClickListener(this);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(name.getText().toString().trim().length()>0||
				number.getText().toString().trim().length()>0)
		new AlertDialog.Builder(ContactSupplierAdd.this)
		.setTitle("Save contact")
		.setMessage("Do you want to save contact?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				save();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				clean();
			}
		})
		.show();
		super.onPause();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.createContactSave:
			save();
			break;
		case R.id.createContactClear:
			clean();
			break;
		}
	}
	
	private void save(){
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();

		 ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
		                        .withValue(RawContacts.ACCOUNT_TYPE, null)
		                        .withValue(RawContacts.ACCOUNT_NAME,null )
		                        .build());
		ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
		                        .withValue(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE)
		                        .withValue(Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
		                        .withValue(Phone.LABEL, "KFC_Supplier")
		                        .withValue(Phone.NUMBER, number.getText().toString().trim())
		                        .build());
		ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
		                        .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
		                        .withValue(Data.MIMETYPE,
		                         StructuredName.CONTENT_ITEM_TYPE)
		                        .withValue(StructuredName.DISPLAY_NAME, name.getText().toString().trim())
		                        .build());  
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			Toast.makeText(ContactSupplierAdd.this, "Contact added", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("VIC",e.getMessage());
		}
		clean();
	}
	
	private void clean(){
		name.setText("");
		number.setText("");
	}
}
