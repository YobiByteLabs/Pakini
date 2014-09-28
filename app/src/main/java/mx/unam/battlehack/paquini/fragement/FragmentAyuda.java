package mx.unam.battlehack.paquini.fragement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.adaptador.MyPagerAdapter;
import mx.unam.battlehack.paquini.controlador.ActivityMain;
import mx.unam.battlehack.paquini.objetos.Constants;
import mx.unam.battlehack.paquini.util.ServerUtilities;

public class FragmentAyuda extends Fragment implements View.OnClickListener {

    private Button bp;
    NumberPicker np;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "Aef0ThAkyiB6Q3vusXRL-OBZF4sAWMybTcok2XqNDc22Dx1eXwYbWU3emX35";
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Context context;
    private MyPagerAdapter pager;
    private int actual_cause;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    public static FragmentAyuda newInstance(int sectionNumber) {
        FragmentAyuda fragment = new FragmentAyuda();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ActivityMain) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ayuda, container, false);
        ViewPager view = (ViewPager)v.findViewById(R.id.pager);
        pager = new MyPagerAdapter(getFragmentManager());
        view.setAdapter(pager);
        view.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}
            @Override
            public void onPageSelected(int i) { actual_cause=i;}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });


        String[] nums = new String[201];
        for (int i = 1; i <= nums.length; i++)
            nums[i - 1] = Integer.toString(i * 50);

        bp = (Button) v.findViewById(R.id.btn_pagar_D);
        np = (NumberPicker) v.findViewById(R.id.nump);
        np.setMinValue(1);
        np.setMaxValue(200);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(nums);
        bp.setOnClickListener(this);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        context.stopService(new Intent(context, PayPalService.class));
        super.onDestroy();
    }

    public void onBuyPressed() {

        // PAYMENT_INTENT_SALE will cause the payment to complete immediately.

        PayPalPayment payment = new PayPalPayment(new BigDecimal("" + (np.getValue() * 50)), "MXN", "Tu Ayuda!",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i(Constants.TAG, "Confirm " + confirm.toJSONObject().toString(4));
                    validar(confirm.toJSONObject());
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    private void validar(JSONObject jsonObject) {
        try {
            if(jsonObject.getJSONObject("response").getString("state").equals("approved")){
                Toast.makeText(context,"Felicidades, haz donado a la causa numero "+actual_cause,Toast.LENGTH_SHORT).show();
                ayudar(Constants.getId(context),actual_cause,String.valueOf(np.getValue() * 50));

            }else{
                Toast.makeText(context,"Tu pago no fue aceptado!",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void ayudar(String id, int actual_cause, String s) {
        ServerUtilities.Ayudar ayudar = new ServerUtilities.Ayudar(context);
        ayudar.execute(id,String.valueOf(actual_cause),s);

    }


    @Override
    public void onClick(View view) {
       if(view.getId()==R.id.btn_pagar_D){
           onBuyPressed();
       }
    }
}
