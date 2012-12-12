package kfc.mykfchotshot;

import java.util.ArrayList;
import java.util.Hashtable;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class ContactSupplierList extends ListActivity implements
		OnClickListener {
	@SuppressWarnings("rawtypes")
	private ArrayAdapter adapter;
	private ArrayList<String> listItems;
	private ArrayList<ContactItem> listStore;
	private ContactItem curItem;
	private Dialog sms, edit;
	private EditText msg, edName, edNumber;
	private ProgressDialog progDialog;
//	private final String[] options = { "Call", "SMS", "Edit", "Delete", "Cancel" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listStore = new ArrayList<ContactItem>();
		listItems = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		this.setListAdapter(adapter);
		getListView().setFastScrollEnabled(true);
		initList();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		initList();
		super.onResume();
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case 0:                      
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setMessage("Login Server...");
            progDialog.setCancelable(false);
            return progDialog;
        default:
            return null;
        }
    }

	private void initList() {
		showDialog(0);
		listStore.clear();
		listItems.clear();
		adapter.clear();
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						String number = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						String label = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
						if (label != null && label.equals("KFC_Supplier")) {
							listItems.add(name + " : " + number);
							listStore.add(new ContactItem(name, number));
						}
					}
					pCur.close();
				}
			}
		}
		adapter.notifyDataSetChanged();
		progDialog.dismiss();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int pos, long id) {
		// TODO Auto-generated method stub
		String name = listItems.get(pos);
		curItem = listStore.get(pos);
		// Toast.makeText(ContactSupplierList.this, curItem.getName(),
		// Toast.LENGTH_SHORT).show();
		new AlertDialog.Builder(ContactSupplierList.this).setItems(R.array.contact_options,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int index) {
						// TODO Auto-generated method stub
						Operation(index);
					}
				}).show();
	}

	private void Operation(int index) {
		// Toast.makeText(ContactSupplierList.this, "Option :" + index,
		// Toast.LENGTH_SHORT).show();
		switch (index) {
		case 0:// call
			Intent dial = new Intent("android.intent.action.CALL",
					Uri.parse("tel:" + curItem.getNumber()));
			startActivity(dial);
			break;
		case 1:// sms
			SendSMS();
			break;
		case 2:// edit
			Edit(curItem);
			break;
		case 3:// delete
			Delete(curItem);
			break;
		default:// none
			break;
		}
	}

	private void SendSMS() {
		int ids[] = { R.id.sendSMS, R.id.cancelSMS };
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.editsms, null);
		msg = (EditText) v.findViewById(R.id.smsmsg);
		for (int i : ids) {
			View b = v.findViewById(i);
			b.setOnClickListener(ContactSupplierList.this);
		}
		AlertDialog.Builder build = new AlertDialog.Builder(
				ContactSupplierList.this).setView(v)
				.setTitle("Send SMS to " + curItem.getName())
				.setCancelable(false);
		sms = build.create();
		sms.show();
	}

	private void Delete(ContactItem item) {
		ContentResolver cr = getContentResolver();
		String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(where, new String[] { item.getName() }).build());
		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
			listItems.remove(item.getName()+ " : " +item.getNumber());
			listStore.remove(item);
			adapter.notifyDataSetChanged();
			Toast.makeText(ContactSupplierList.this, "Contact deleted",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}

	private void Edit(ContactItem item) {
		int ids[] = { R.id.editcontactsave, R.id.editcontactcancel };
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.editcontact, null);
		edName = (EditText) v.findViewById(R.id.editcontactname);
		edNumber = (EditText) v.findViewById(R.id.editcontactnumber);
		edName.setText(item.getName());
		edNumber.setText(item.getNumber());
		for (int i : ids) {
			View b = v.findViewById(i);
			b.setOnClickListener(ContactSupplierList.this);
		}
		AlertDialog.Builder build = new AlertDialog.Builder(
				ContactSupplierList.this).setView(v)
				.setTitle("Send SMS to " + curItem.getName())
				.setCancelable(false);
		edit = build.create();
		edit.show();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sendSMS:
			new SMS(ContactSupplierList.this).sendSMS(
					new String[] { curItem.getNumber() }, msg.getText()
							.toString().trim());
		case R.id.cancelSMS:
			sms.dismiss();
			break;
		case R.id.editcontactsave:
			SaveEdit(curItem);
		case R.id.editcontactcancel:
			edit.dismiss();
		}
	}

	private void SaveEdit(ContactItem item) {
		listItems.remove(item.getName()+ " : " +item.getNumber());
		listStore.remove(item);
		adapter.notifyDataSetChanged();
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ContentResolver cr = getContentResolver();
		String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
		ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(where, new String[] { item.getName() })
				.build());
		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
			int rawContactInsertIndex = ops.size();
			ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
					.withValue(RawContacts.ACCOUNT_TYPE, null)
					.withValue(RawContacts.ACCOUNT_NAME, null).build());
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
							rawContactInsertIndex)
					.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
					.withValue(Phone.TYPE,
							ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
					.withValue(Phone.LABEL, "KFC_Supplier")
					.withValue(Phone.NUMBER, edNumber.getText().toString().trim())
					.build());
			ops.add(ContentProviderOperation
					.newInsert(Data.CONTENT_URI)
					.withValueBackReference(Data.RAW_CONTACT_ID,
							rawContactInsertIndex)
					.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
					.withValue(StructuredName.DISPLAY_NAME,
							edName.getText().toString().trim()).build());
			try {
				getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
				listItems.add(edName.getText().toString().trim()+ " : "+edNumber.getText().toString().trim());
				listStore.add(new ContactItem(edName.getText().toString().trim(),edNumber.getText().toString().trim()));
				adapter.notifyDataSetChanged();
				Toast.makeText(ContactSupplierList.this, "Contact edited",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("VIC", e.getMessage());
			}
		} catch (Exception e) {
		}
	}
}
