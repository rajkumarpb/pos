package com.refresh.pos.ui.sale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;

@SuppressLint("ValidFragment")
public class EditFragmentDialog extends DialogFragment {
	private Register register;
	private UpdatableFragment saleFragment;
	private UpdatableFragment reportFragment;
	private EditText quantityBox;
	private EditText priceBox;
	private Button comfirmButton;
	private String saleId;
	private String position;
	private LineItem lineItem;
	
	public EditFragmentDialog(UpdatableFragment saleFragment, UpdatableFragment reportFragment) {
		super();
		this.saleFragment = saleFragment;
		this.reportFragment = reportFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_saleedit, container, false);
		try {
			register = Register.getInstance();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		quantityBox = (EditText) v.findViewById(R.id.quantityBox);
		priceBox = (EditText) v.findViewById(R.id.priceBox);
		comfirmButton = (Button) v.findViewById(R.id.confirmButton);
		
		saleId = getArguments().getString("sale_id");
		position = getArguments().getString("position");

		lineItem = register.getCurrentSale().getLineItemAt(Integer.parseInt(position));
		quantityBox.setText(lineItem.getQuantity()+"");
		priceBox.setText(lineItem.getProduct().getUnitPrice()+"");

		comfirmButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				register.updateItem(
						Integer.parseInt(saleId),
						lineItem,
						Integer.parseInt(quantityBox.getText().toString()),
						Double.parseDouble(priceBox.getText().toString())
				);
				
				end();
			}
			
		});
		return v;
	}
	private void end(){
		saleFragment.update();
		reportFragment.update();
		this.dismiss();
	}
	
	
}