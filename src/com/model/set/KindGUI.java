package com.model.set;

import com.activity.AddKind_Activity;
import com.activity.JZ_Activity;
import com.dao.Kind_Dao;
import com.yyyy.yyyy.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class KindGUI {
	private int deleteid;
	private EditText addNew;
	private LinearLayout linearlayOut;

	@SuppressLint("InflateParams")
	public void createChlid(int firstid, LinearLayout rootLinearLayout) {
		this.linearlayOut = rootLinearLayout;
		rootLinearLayout.removeAllViews();// �����
		Cursor cursor = Kind_Dao.getchildKinds(firstid);
		LayoutInflater inflater = LayoutInflater
				.from(AddKind_Activity.addKind_Activity);
		while (cursor.moveToNext()) {
			View view = inflater.inflate(R.layout.childkind_templet, null);
			((TextView) view.findViewById(R.id._childkind)).setText(cursor
					.getString(cursor.getColumnIndex("kindname")));
			TextView delete = (TextView) view.findViewById(R.id.delete);
			delete.setId(cursor.getInt(cursor.getColumnIndex("secondid")));
			delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// ������ʾ,����JZ_Activity.consumeKind �� v.getId()��λ
					deleteid = v.getId();
					Dialog alert = new AlertDialog.Builder(
							AddKind_Activity.addKind_Activity)
							.setTitle("ɾ�����")
							.setMessage("ȷ��Ҫɾ��������")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Kind_Dao.deleteItem(JZ_Activity.consumekind,deleteid);
											createChlid(JZ_Activity.consumekind, linearlayOut);
										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									}).create();
					alert.show();
				}
			});
			rootLinearLayout.addView(view);
		}
	}

	@SuppressLint("InflateParams")
	public void addNew(int firstid, LinearLayout rootLinearLayout) {
		this.linearlayOut = rootLinearLayout;
		LayoutInflater inflater = LayoutInflater
				.from(AddKind_Activity.addKind_Activity);
		View view = inflater.inflate(R.layout.addnew_templet, null);
		rootLinearLayout.addView(view);
		this.addNew = (EditText) view.findViewById(R.id._childkindName);
		((TextView) view.findViewById(R.id._ok))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String newKindName = addNew.getText().toString();
						if (newKindName.length() > 0) {
							int kind = JZ_Activity.consumekind;
							if (Kind_Dao.insertNewKind(kind, newKindName)) {
								createChlid(kind, linearlayOut);// ˢ�½���
							}
						} else {
							Toast.makeText(AddKind_Activity.addKind_Activity,
									"���ֻ�ûдŶ��", Toast.LENGTH_SHORT).show();
							;
						}
					}
				});
	}
}
