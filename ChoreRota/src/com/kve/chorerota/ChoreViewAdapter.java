package com.kve.chorerota;


	import java.util.ArrayList;

import com.kve.chorerota.data.*;

	import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

	public class ChoreViewAdapter extends BaseAdapter {

		private ArrayList<ChoreRecord> list = new ArrayList<ChoreRecord>();
		private static LayoutInflater inflater = null;
		private Context mContext;
		
		
		public ChoreViewAdapter(Context context) {
			mContext = context;
			inflater = LayoutInflater.from(mContext);
		}

		public int getCount() {
			return list.size();
		}

		public ChoreRecord getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View newView = convertView;
			ViewHolder holder;

			ChoreRecord curr = list.get(position);

			if (null == convertView) {
				holder = new ViewHolder();
				newView = inflater
						.inflate(R.layout.chore_item, parent, false);
				holder.choreName = (TextView) newView.findViewById(R.id.tvTableChoreName);
				holder.baseDate = (TextView) newView.findViewById(R.id.tvTableDate);
				holder.baseTime = (TextView) newView.findViewById(R.id.tvTableTime);
				holder.freqNo = (TextView) newView.findViewById(R.id.tvTableFreqNo);
				holder.freqUnits = (TextView) newView.findViewById(R.id.tvTableFreqUnit);
			newView.setTag(holder);

			} else {
				holder = (ViewHolder) newView.getTag();
			}

			holder.choreName.setText(curr.getChoreName());
			holder.baseDate.setText(curr.getBaseDateString());
			holder.baseTime.setText(curr.getBaseTimeString());
			holder.freqNo.setText(curr.getFreqNo().toString());
			holder.freqUnits.setText(curr.getFreqUnits().getUnitName());

	
			return newView;
		}

		static class ViewHolder {

			TextView choreName;
			TextView baseDate;
			TextView baseTime;
			TextView freqNo;
			TextView freqUnits;

		}

	
		public void add(ChoreRecord listItem) {
			list.add(listItem);
			notifyDataSetChanged();
		}

		public ArrayList<ChoreRecord> getList() {
			return list;
		}

		public void removeAllViews() {
			list.clear();
			this.notifyDataSetChanged();
		}
	}


