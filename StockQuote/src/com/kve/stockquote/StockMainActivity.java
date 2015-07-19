package com.kve.stockquote;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StockMainActivity extends Activity {
	// ocho 16.48   mins 
	
	public final static String STOCK_SYMBOL = "com.kve.stockquote.STOCK";
	
	private SharedPreferences stockSymbolsEntered;
	
	private TableLayout	tlStocks;
	private EditText 	etStockSymbol;
	
	private Button		btnEnterStockSymbol;
	private Button		btnDeleteSymbols;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_main);
		
		tlStocks			= (TableLayout) findViewById(R.id.tlStocks);
		etStockSymbol		= (EditText) 	findViewById(R.id.etStockSymbol);
		btnEnterStockSymbol	= (Button) 		findViewById(R.id.btnEnterStockSymbol);
		btnDeleteSymbols	= (Button) 		findViewById(R.id.btnDeleteSymbols);
		
		stockSymbolsEntered = getSharedPreferences("stocklist", MODE_PRIVATE);
		
		btnEnterStockSymbol.setOnClickListener(enterStockSymbolListener);
		btnDeleteSymbols.setOnClickListener(deleteStocksListener);
		
		updateSavedStockList(null);
}

	private void updateSavedStockList(String newStockSymbol) {
		String[] stocks = stockSymbolsEntered.getAll().keySet().toArray(new String[0]);
		Arrays.sort(stocks, String.CASE_INSENSITIVE_ORDER);
		
		if (null != newStockSymbol)
		{
			insertStockInScrollView(newStockSymbol, Arrays.binarySearch(stocks, newStockSymbol));
		}
		else
		{
			for (int i= 0 ; i < stocks.length ; i++)
			{
				insertStockInScrollView(stocks[i], i);			
			}
		}
	}

	private void saveStockSymbol(String newStock)
	{
		String isTheStockNew = stockSymbolsEntered.getString(newStock, null);
		
		SharedPreferences.Editor preferencesEditor = stockSymbolsEntered.edit();
		preferencesEditor.putString(newStock, newStock);
		preferencesEditor.apply();
		
		if (null == isTheStockNew)
		{
			// it is new
			updateSavedStockList(newStock);
		}
		
	}
	
	private void insertStockInScrollView(String stock, int arrayIndex) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newStockRow = inflater.inflate(R.layout.stock_quote_row, null);
		TextView tvNewStock = (TextView) newStockRow.findViewById(R.id.tvStockSymbolQuoteRow);
		tvNewStock.setText(stock);
		
		Button btnStockQuote 	= (Button) newStockRow.findViewById(R.id.btnStockQuote);
		Button btnGoToWeb 		= (Button) newStockRow.findViewById(R.id.btnGoToWeb);
		
		btnStockQuote.setOnClickListener(stockQuoteListener);
		btnGoToWeb.setOnClickListener(goToWebListener);
		
		tlStocks.addView(newStockRow, arrayIndex);
}

	public OnClickListener enterStockSymbolListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(etStockSymbol.getText().length() > 0)
			{
				saveStockSymbol(etStockSymbol.getText().toString());
				etStockSymbol.setText("");
				
				//	Force close the keyboard
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etStockSymbol.getWindowToken(), 0);
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(StockMainActivity.this);
				builder.setTitle(R.string.invalid_stock_symbol);
				builder.setPositiveButton(R.string.ok, null);
				builder.setMessage(R.string.enter_stock_symbol);
				
				AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		}
	};

	public	OnClickListener deleteStocksListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			tlStocks.removeAllViews();
			SharedPreferences.Editor preferencesEditor = stockSymbolsEntered.edit();
			preferencesEditor.clear();
			preferencesEditor.apply();

		}
	};

	public OnClickListener stockQuoteListener= new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TableRow tableRow = (TableRow) v.getParent();
			TextView tvRow = (TextView) tableRow.findViewById(R.id.tvStockSymbolQuoteRow) ;
			String stockSymbol = tvRow.getText().toString();
			
			Intent intent = new Intent(StockMainActivity.this, StockInfoActivity.class);
			intent.putExtra(STOCK_SYMBOL, stockSymbol);
			startActivity(intent);
		
		}
	};


	public		OnClickListener goToWebListener= new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TableRow tableRow = (TableRow) v.getParent();
			TextView tvRow = (TextView) tableRow.findViewById(R.id.tvStockSymbolQuoteRow) ;
			String stockSymbol = tvRow.getText().toString();

			String stockUrl = getString(R.string.yahoo_stock_url) + stockSymbol;
			Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(stockUrl));
			startActivity(websiteIntent);
		}
	};


}
