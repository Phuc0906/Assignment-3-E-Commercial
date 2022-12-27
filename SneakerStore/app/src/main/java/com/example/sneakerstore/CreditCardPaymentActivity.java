package com.example.sneakerstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreditCardPaymentActivity extends AppCompatActivity {

    EditText cardName, cardNo, cardExpiredDate, cardCSV;
    TextView nameOnCardView, expiredDateView, cardNoView;
    TextView invalidName, invalidNumber, invalidDate;
    ImageView cardType;
    Button payBtn;
    ImageButton backToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);


        cardName = findViewById(R.id.nameOnCardInput);
        cardNo = findViewById(R.id.cardNoInput);
        cardExpiredDate = findViewById(R.id.expiredDateInput);
        cardCSV = findViewById(R.id.csvInput);


        nameOnCardView = findViewById(R.id.nameOnCardPayment);
        expiredDateView = findViewById(R.id.validDate);
        cardNoView = findViewById(R.id.cardNumberPayment);
        cardType = findViewById(R.id.cardTypeView);

        invalidName = findViewById(R.id.invalidCardName);
        invalidNumber = findViewById(R.id.invalidCardNoAnnounce);
        invalidDate = findViewById(R.id.invalidDateAnnounce);

        payBtn = findViewById(R.id.payBtn);
        backToOrder = findViewById(R.id.backToOrder);


        invalidName.setVisibility(View.INVISIBLE);
        invalidNumber.setVisibility(View.INVISIBLE);
        invalidDate.setVisibility(View.INVISIBLE);

        cardType.setImageResource(0);

        cardNoView.setText("");

        int maxLength = 16;
        cardNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        cardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                invalidNumber.setVisibility(View.INVISIBLE);
                String displayString = charSequence.toString();
                if (displayString.length() > 12) {
                    displayString = displayString.substring(0, 4) + " " + displayString.substring(4, 8) + " " + displayString.substring(8, 12) + " " + displayString.substring(12, displayString.length());
                }else if (displayString.length() > 8) {
                    displayString = displayString.substring(0, 4) + " " + displayString.substring(4, 8) + " " + displayString.substring(8, displayString.length());
                }else if (displayString.length() > 3) {
                    displayString = displayString.substring(0, 4) + " " + displayString.substring(4, displayString.length());
                }

                cardNoView.setText(displayString);



                if (charSequence.length() > 0) {


                    if (charSequence.charAt(0) == '5') {
                        cardType.setImageResource(R.drawable.mc_symbol_opt_45_3x);
                    } else if (charSequence.charAt(0) == '4') {
                        cardType.setImageResource(R.drawable.visa_logo);
                    }
                }else {
                    cardType.setImageResource(0);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                invalidName.setVisibility(View.INVISIBLE);
                System.out.println(i + " - " + i1 + " - " + i2);
                nameOnCardView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        int expiredMaxLength = 4;
        cardExpiredDate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(expiredMaxLength)});
        cardExpiredDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                invalidDate.setVisibility(View.INVISIBLE);
                String displayDate = charSequence.toString();
                if (displayDate.length() > 2) {
                    displayDate = displayDate.substring(0, 2) + "/" + displayDate.substring(2, displayDate.length());
                }

                expiredDateView.setText("Valid Thru " + displayDate);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validNo = false;
                boolean validDate = true;
                boolean validName = false;

                if (cardName.getText().toString().length() != 0) {
                    validName = true;
                }else {
                    invalidName.setVisibility(View.VISIBLE);
                }

                // check valid card number
                if (cardNoView.getText().toString().length() != 19) {
                    invalidNumber.setVisibility(View.VISIBLE);
                }else {
                    validNo = true;
                }

                if (validDate && validNo && validName) {
                    // start success intent'
                    Intent intent = new Intent(CreditCardPaymentActivity.this, OrderSuccessActivity.class);
                    startActivityForResult(intent, 96);
                }


            }
        });

        backToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backResultIntent = new Intent();
                setResult(RESULT_CANCELED, backResultIntent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 96) {
            if (resultCode == RESULT_OK) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}