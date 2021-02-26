package com.healthplus.healthchamp.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atom.mobilepaymentsdk.PayActivity;
import com.healthplus.healthchamp.Activity.AddProfile;
import com.healthplus.healthchamp.Activity.AppConstants;
import com.healthplus.healthchamp.Activity.DrawerActivity;
import com.healthplus.healthchamp.Activity.Util;
import com.healthplus.healthchamp.Activity.VerificationActivity;
import com.healthplus.healthchamp.Adapter.GetCounsellorAdapter;
import com.healthplus.healthchamp.Adapter.GetPromocodeOfferAdapter;
import com.healthplus.healthchamp.Model.HealthChampPlusData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class BookAppointmentFragment extends Fragment {
TextView txtname,txtmail,txtappointmentdate,txtslot,txtconsultcharge,txtmobile,txtcouncildate,txtcode;
ImageView imgdefaultchat,imgchat,imgdefaultphone,imgphone,imgdefaultvideo,imgvideo,back;
Button btncouncilbook;
LinearLayout layout_code,layout_breakup;
    List<HealthChampPlusData> docdata = new ArrayList<>();
    String response_data,user_id,parentgenderid;
    String password="";
    String merchantId="";
    String txnscamt="";
    String loginid="";
    String prodid="";
    String counsellingamount,counsellingamountid;
    String txncurr="";
    String clientcode="";
    String custacc="";
    String channelid="";
    String amt1 = "";
    String txnid = "";
    String date = "";
    String cardtype = "";
    String cardAssociate = "";
    String bankid = "";
    String discriminator = "";
    String ru="";
    String signature_request="";
    String signature_response="";
    String mprod="";
    String surcharge="";
    String completeurl;
    String cardmode;
    String customerName="", customerEmailID="", customerMobileNo="", billingAddress="", optionalUdf9="";
    String url1="https://payment.atomtech.in/";
    String mainURL,sendmainURL;
    Button btnatompay,btnairpay;
    Button btntestatompay,btntestairpay;
    String id;
    String bank_txn;
    TextView txtamount,txtdate,txtresponse;
    String message,pin,bookingcounsellingtransactionid;
    String amt = "0";
    String name,mobile,mail,city;
    String transactionid;
    String ResponseValue, counsellingmodeid;
    String  apitransactionid;
    String formattedDate;
    String  atomresponseurl,pname,pemail,pmobile,pslot,pdate,plocation;
    String userloginid;
    TextView txtlogout,txtbreakup,txtcharge,txttotalcharge,txtpromocode,txtoffervalue;
    ProgressDialog progressBar;
    RecyclerView recyclerviewoffer;
    String famount;
    EditText edtpromocode;
    Double bookamount;
    ImageView imgcancel;
    Button btnapply;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookappointment, container, false);
        txtname=view.findViewById(R.id.txtname);
        edtpromocode=view.findViewById(R.id.edtpromocode);
        btnapply=view.findViewById(R.id.btnapply);
        txtcharge=view.findViewById(R.id.txtcharge);
        txtbreakup=view.findViewById(R.id.txtbreakup);
        imgcancel=view.findViewById(R.id.imgcancel);
        txtoffervalue=view.findViewById(R.id.txtoffervalue);
        txtpromocode=view.findViewById(R.id.txtpromocode);
        layout_breakup=view.findViewById(R.id.layout_breakup);
        txtmail=view.findViewById(R.id.txtmail);
        layout_code=view.findViewById(R.id.layout_code);
        txtcode=view.findViewById(R.id.txtcode);
        recyclerviewoffer=view.findViewById(R.id.recyclerviewoffer);
        txtappointmentdate=view.findViewById(R.id.txtappointmentdate);
        txtslot=view.findViewById(R.id.txtslot);
        txtcouncildate=view.findViewById(R.id.txtcouncildate);
        back=view.findViewById(R.id.back);
        txtconsultcharge=view.findViewById(R.id.txtconsultcharge);
        txtmobile=view.findViewById(R.id.txtmobile);
        imgdefaultchat=view.findViewById(R.id.imgdefaultchat);
        imgchat=view.findViewById(R.id.imgchat);
        imgdefaultphone=view.findViewById(R.id.imgdefaultphone);
        imgphone=view.findViewById(R.id.imgphone);
        imgdefaultvideo=view.findViewById(R.id.imgdefaultvideo);
        imgvideo=view.findViewById(R.id.imgvideo);
        txttotalcharge=view.findViewById(R.id.txttotalcharge);
        btncouncilbook=view.findViewById(R.id.btncouncilbook);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounselorAvailability f1 = new CounselorAvailability();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, f1);
                ft.addToBackStack(null);
                ft.commit();
                SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=spreferences.edit();
                editor.remove("timeslotstarttime");
                editor.remove("timeslotendtime");
                editor.apply();
                editor.commit();
            }
        });

        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String bookingdate = mypref1.getString("bookingdate","");
        String Name = mypref1.getString("Name","");
        String EmailID = mypref1.getString("EmailID","");
        String timeslot = mypref1.getString("timeslot","");
        String MobileNo = mypref1.getString("parentMobileNo","");
        txtslot.setText("Slot : " + timeslot);
        txtmobile.setText("Mobile : " + MobileNo);
        txtmail.setText("Email : " + EmailID);
        txtname.setText("Name : " + Name);
        txtappointmentdate.setText("Appointment date : " +bookingdate);
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        txtcouncildate.setText(todaydate);

//        imgdefaultchat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imgchat.setVisibility(View.VISIBLE);
//                imgdefaultchat.setVisibility(View.GONE);
//                imgphone.setVisibility(View.GONE);
//                imgdefaultphone.setVisibility(View.VISIBLE);
//                imgvideo.setVisibility(View.GONE);
//                imgdefaultvideo.setVisibility(View.VISIBLE);
//                counsellingmodeid = "2";
//                GetParentChildMaster();
//                btncouncilbook.setVisibility(View.VISIBLE);
//                txtcode.setVisibility(View.VISIBLE);
//                txtbreakup.setVisibility(View.VISIBLE);
//
//               txtconsultcharge.setText("Consultation Charges : Rs. 120");
//            }
//        });

        imgdefaultphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               imgphone.setVisibility(View.VISIBLE);
                imgdefaultphone.setVisibility(View.GONE);
             //   imgchat.setVisibility(View.GONE);
             //   imgdefaultchat.setVisibility(View.VISIBLE);
                imgvideo.setVisibility(View.GONE);
                imgdefaultvideo.setVisibility(View.VISIBLE);
                counsellingmodeid = "3";
                GetParentChildMaster();
                btncouncilbook.setVisibility(View.VISIBLE);
                txtcode.setVisibility(View.VISIBLE);
                txtbreakup.setVisibility(View.VISIBLE);

            }
        });
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtpromocode.getText().clear();
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String promocodeoffervalue = mypref1.getString("OfferValue","");
                String promocodeoffercode = mypref1.getString("OfferCode", "");
                if(promocodeoffervalue.equals("")||promocodeoffercode.equals("") || edtpromocode.getText().toString().isEmpty())
                {
                    txtcharge.setText(counsellingamount);
                    txtoffervalue.setText("0");
                    txtpromocode.setText("Appply Promoce : No Promocode Apply");

                    txttotalcharge.setText(counsellingamount);
                }
                else {
                    txtcharge.setText(counsellingamount);
                    txtoffervalue.setText(promocodeoffervalue);
                    txtpromocode.setText("Appply Promoce : " + promocodeoffercode);
                    String actualamount = counsellingamount;
                    String offeramount = promocodeoffervalue;
                    Double actamount = Double.valueOf(actualamount);
                    Double offamount = Double.valueOf(offeramount);
                    if (actamount > offamount) {
                        double finalamount = actamount - offamount;
                        famount = String.valueOf(finalamount);
                        txttotalcharge.setText(famount);
                    } else {
                        txtcharge.setText(counsellingamount);
                        txtoffervalue.setText("0");
                        txtpromocode.setText("Appply Promoce : No Promocode Apply");
                        txttotalcharge.setText(counsellingamount);
                        Toast.makeText(getActivity(), "Can not apply this offer", Toast.LENGTH_SHORT).show();
                    }
                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor prefsEditr = mypref.edit();
                    prefsEditr.putString("famount", famount);
                    prefsEditr.commit();
                }
            }
        });

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetOfferCode();
                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String promocodeoffervalue = mypref1.getString("OfferValue","");
                String promocodeoffercode = mypref1.getString("OfferCode", "");
                if(promocodeoffervalue.equals("")||promocodeoffercode.equals("") || edtpromocode.getText().toString().isEmpty())
                {
                    txtcharge.setText(counsellingamount);
                    txtoffervalue.setText("0");
                    txtpromocode.setText("Appply Promoce : No Promocode Apply");

                    txttotalcharge.setText(counsellingamount);
                }
                else {
                    txtcharge.setText(counsellingamount);
                    txtoffervalue.setText(promocodeoffervalue);
                    txtpromocode.setText("Appply Promoce : " + promocodeoffercode);
                    String actualamount = counsellingamount;
                    String offeramount = promocodeoffervalue;
                    Double actamount = Double.valueOf(actualamount);
                    Double offamount = Double.valueOf(offeramount);
                    if (actamount > offamount) {
                        double finalamount = actamount - offamount;
                        famount = String.valueOf(finalamount);
                        txttotalcharge.setText(famount);
                    } else {
                        txtcharge.setText(counsellingamount);
                        txtoffervalue.setText("0");
                        txtpromocode.setText("Appply Promoce : No Promocode Apply");
                        txttotalcharge.setText(counsellingamount);
                        Toast.makeText(getActivity(), "Can not apply this offer", Toast.LENGTH_SHORT).show();
                    }
                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor prefsEditr = mypref.edit();
                    prefsEditr.putString("famount", famount);
                    prefsEditr.commit();
                }
            }
        });
        imgdefaultvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgvideo.setVisibility(View.VISIBLE);
                imgdefaultvideo.setVisibility(View.GONE);
                imgphone.setVisibility(View.GONE);
                imgdefaultphone.setVisibility(View.VISIBLE);
              //  imgchat.setVisibility(View.GONE);
              //  imgdefaultchat.setVisibility(View.VISIBLE);
                counsellingmodeid = "1";
                GetParentChildMaster();
                btncouncilbook.setVisibility(View.VISIBLE);
                txtcode.setVisibility(View.VISIBLE);
                txtbreakup.setVisibility(View.VISIBLE);
            }
        });
        txtbreakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_breakup.setVisibility(View.VISIBLE);
//                SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
//               String promocodeoffervalue = mypref1.getString("OfferValue","");
//                String promocodeoffercode = mypref1.getString("OfferCode", "");
//                if(promocodeoffervalue.equals("")||promocodeoffercode.equals("") || edtpromocode.getText().toString().isEmpty())
//                {
//                    txtcharge.setText(counsellingamount);
//                    txtoffervalue.setText("0");
//                    txtpromocode.setText("Appply Promoce : No Promocode Apply");
//
//                        txttotalcharge.setText(counsellingamount);
//                    }
//                    else {
//                        txtcharge.setText(counsellingamount);
//                    txtoffervalue.setText(promocodeoffervalue);
//                    txtpromocode.setText("Appply Promoce : " + promocodeoffercode);
//                    String actualamount = counsellingamount;
//                    String offeramount = promocodeoffervalue;
//                    Double actamount = Double.valueOf(actualamount);
//                    Double offamount = Double.valueOf(offeramount);
//                    if (actamount > offamount) {
//                        double finalamount = actamount - offamount;
//                        famount = String.valueOf(finalamount);
//                        txttotalcharge.setText(famount);
//                    } else {
//                        txtcharge.setText(counsellingamount);
//                        txtoffervalue.setText("0");
//                        txtpromocode.setText("Appply Promoce : No Promocode Apply");
//                        txttotalcharge.setText(counsellingamount);
//                        Toast.makeText(getActivity(), "Can not apply this offer", Toast.LENGTH_SHORT).show();
//                    }
//                    SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                    SharedPreferences.Editor prefsEditr = mypref.edit();
//                    prefsEditr.putString("famount", famount);
//                    prefsEditr.commit();
//                }

            }
        });
        txtcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_code.setVisibility(View.VISIBLE);
                @SuppressLint("WrongConstant")
                LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerviewoffer.setLayoutManager(horizontalLayoutManager1);
                docdata = new ArrayList<>();
                GetOffers();
            }
        });

      btncouncilbook.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //  PostCounsellingRecords();
              SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                  String Name = mypref1.getString("Name", "");
                  String EmailID = mypref1.getString("EmailID", "");
                  String MobileNo = mypref1.getString("parentMobileNo", "");
                  String famount = mypref1.getString("offeramountcharge","");
                  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                  String formattedDate = sdf.format(new Date());

                  if (famount.equals(null)||famount.equals("")||famount.equals("null") || edtpromocode.getText().toString().isEmpty())
                  {
                      String donateamount = counsellingamount;
                      Double doubleAmt = Double.valueOf(donateamount);//Double.valueOf(dororprojectfundneeded);
                   //   bookamount = Double.valueOf(doubleAmt.toString());
                      amt = doubleAmt.toString();

                  }
                  else{
                      String donateamount = famount;
                      Double doubleAmt = Double.valueOf(donateamount);//Double.valueOf(dororprojectfundneeded);
                     amt = doubleAmt.toString();
                   //  bookamount = Double.valueOf(doubleAmt.toString());
             }

                  String posturl = "https://payment.atomtech.in/mobilesdk/param";//https://paynetzuat.atomtech.in/mobilesdk/param
                  Intent newPayIntent = new Intent(getActivity(), PayActivity.class);
                  newPayIntent.putExtra("merchantId", "111292");
                  newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be �0�
                  newPayIntent.putExtra("loginid", "111292");
                  newPayIntent.putExtra("password", "19f1c5cb");
                  newPayIntent.putExtra("prodid", "RUPANTAR");
                  newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be �INR�
                  newPayIntent.putExtra("clientcode", encodeBase64("007"));
                  newPayIntent.putExtra("custacc", "100000036600");
                  newPayIntent.putExtra("amt", amt);//Should be 3 decimal number i.e 1.000
                  newPayIntent.putExtra("txnid", "013");
                  newPayIntent.putExtra("date", formattedDate);//Should be in same format
                  newPayIntent.putExtra("discriminator", "All"); //NB/ IMPS/ All
                  newPayIntent.putExtra("signature_request", "a206557143e5035ee2");
                  newPayIntent.putExtra("signature_response", "81e859e7440130bec3");
                  newPayIntent.putExtra("ru", "https://payment.atomtech.in/mobilesdk/param");//https://paynetzuat.atomtech.in/paynetzclient/ResponseParam.jsp"); //https://payment.atomtech.in/mobilesdk/param"); // FOR PRODUCTION
                  newPayIntent.putExtra("customerName", Name); //Only for Name
                  newPayIntent.putExtra("customerEmailID", EmailID);//Only for Email ID
                  newPayIntent.putExtra("donorprofileaddress", "ghaziabad");//Only for Mobile Number
                  newPayIntent.putExtra("billingAddress", "ghaziabad");//Only for Address
                  newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 1");// Can pass any data
                  SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                  SharedPreferences.Editor prefsEditr = mypref.edit();
                  prefsEditr.putString("url", posturl);
                  prefsEditr.commit();
                  merchantId = trimmingText(newPayIntent.getStringExtra("merchantId"));
                  txnscamt = trimmingText(newPayIntent.getStringExtra("txnscamt"));
                  loginid = trimmingText(newPayIntent.getStringExtra("loginid"));
                  password = trimmingText(newPayIntent.getStringExtra("password"));
                  prodid = trimmingText(newPayIntent.getStringExtra("prodid"));
                  txncurr = trimmingText(newPayIntent.getStringExtra("txncurr"));
                  clientcode = encodeBase64("007");
                  channelid = trimmingText(newPayIntent.getStringExtra("channelid"));
                  amt1 = trimmingText(newPayIntent.getStringExtra("amt"));
                  txnid = trimmingText(newPayIntent.getStringExtra("txnid"));
                  date = trimmingText(newPayIntent.getStringExtra("date"));
                  cardtype = trimmingText(newPayIntent.getStringExtra("cardtype"));
                  cardAssociate = trimmingText(newPayIntent.getStringExtra("cardAssociate"));
                  System.out.println("cardAssociate ::" + cardAssociate);
                  bankid = trimmingText(newPayIntent.getStringExtra("bankid"));
                  discriminator = trimmingText(newPayIntent.getStringExtra("discriminator"));
                  surcharge = trimmingText(newPayIntent.getStringExtra("surcharge"));
                  signature_request = trimmingText(newPayIntent.getStringExtra("signature_request"));
                  signature_response = trimmingText(newPayIntent.getStringExtra("signature_response"));
                  customerName = trimmingTextWithEmpty(newPayIntent.getStringExtra("customerName"));
                  custacc = trimmingTextWithEmpty(newPayIntent.getStringExtra("custacc"));
                  customerEmailID = trimmingTextWithEmpty(newPayIntent.getStringExtra("customerEmailID"));
                  customerMobileNo = trimmingTextWithEmpty(newPayIntent.getStringExtra("customerMobileNo"));
                  billingAddress = trimmingTextWithEmpty(newPayIntent.getStringExtra("billingAddress"));
                  optionalUdf9 = trimmingTextWithEmpty(newPayIntent.getStringExtra("optionalUdf9"));
                  mprod = trimmingTextWithEmpty(newPayIntent.getStringExtra("mprod"));
                  ru = trimmingText(newPayIntent.getStringExtra("ru"));
                  System.out.println("With out Bank id");
                  String signature_req = signature_request;
                  System.out.println("signature from app:" + signature_req);
                  signature_request = getEncodedValueWithSha2(signature_req, loginid, password, "NBFundTransfer", prodid, txnid, amt, txncurr);
                  completeurl = "login=" + loginid + "&pass=" + password + "&ttype=NBFundTransfer&prodid=" + prodid + "&amt=" + amt + "&txncurr=" + txncurr + "&txnscamt=" + txnscamt + "&clientcode=" + clientcode + "&txnid=" + txnid + "&date=" + date + "&custacc=" + custacc + "&ru=" + ru + "&signature=" + signature_request + "&udf1=" + customerName + "&udf2=" + customerEmailID + "&udf3=" + customerMobileNo + "&udf9=" + optionalUdf9;
                  mainURL = url1 + "paynetz/epi/fts?" + completeurl;
                  SharedPreferences mypref4 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                  SharedPreferences.Editor prefsEditr1 = mypref4.edit();
                  prefsEditr1.putString("paymentmainURL", mainURL);
                  prefsEditr1.commit();
                  Log.i("mainurl", mainURL);
                  PostInitiatePayment();
                  startActivityForResult(newPayIntent, 1);
                  String apicompleteurl = "login=" + loginid + "|pass=" + password + "|ttype=NBFundTransfer|prodid=" + prodid + "|amt=" + amt + "|txncurr=" + txncurr + "|txnscamt=" + txnscamt + "|clientcode=" + clientcode + "|txnid=" + txnid + "|date=" + date + "|custacc=" + custacc + "|ru=" + ru + "|signature=" + signature_request + "|udf1=" + customerName + "|udf2=" + customerEmailID + "|udf3=" + customerMobileNo + "|udf9=" + optionalUdf9;
                  sendmainURL = url1 + "paynetz/epi/fts?" + apicompleteurl;

          }
        });
        GetTransactionID();
        return view;
    }
    private void GetParentChildMaster() {
        String url = AppConstants.Base_URL+"GetPaymentConfig?CounsellingModeID="+counsellingmodeid+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        counsellingamount =jsonobject.getString("Amount");
                        counsellingamountid=jsonobject.getString("ID");
                        txtconsultcharge.setText("Consultation Charges : Rs." + counsellingamount);
                        SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor prefsEditr = mypref.edit();
                        prefsEditr.putString("counsellingamount", counsellingamount);
                        prefsEditr.commit();
                        txtcharge.setText(counsellingamount);
                        txtoffervalue.setText("0");
                        txtpromocode.setText("Appply Promoce : No Promocode Apply");
                        txttotalcharge.setText(counsellingamount);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void GetOfferCode() {
        String promocode = edtpromocode.getText().toString();
        String url = AppConstants.Base_URL+"GetOffers?OfferCode="+promocode+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("[]")||response.equals("")||response.equals("null")||response.equals(null))
                    {
                        Toast.makeText(getActivity(), "No Offer is availale", Toast.LENGTH_SHORT).show();
                        txtcharge.setText(counsellingamount);
                        txtoffervalue.setText("0");
                        txtpromocode.setText("Appply Promoce : No Promocode Apply");
                        txttotalcharge.setText(counsellingamount);
                        famount = String.valueOf(counsellingamount);
                        SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor prefsEditr = mypref.edit();
                        prefsEditr.putString("offeramountcharge", famount);
                        prefsEditr.commit();
                    }
                    else {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            String OfferCode = jsonobject.getString("OfferCode");
                            String OfferValue = jsonobject.getString("OfferValue");
                            String offerid = jsonobject.getString("ID");
                            Double counselorcharge = Double.valueOf(counsellingamount);
                            Double offamount = Double.valueOf(OfferValue);

                            if ((counselorcharge < offamount) || (counselorcharge.equals(offamount))) {
                                Toast.makeText(getActivity(), "Can not apply this offer", Toast.LENGTH_SHORT).show();
                                txtcharge.setText(counsellingamount);
                                txtoffervalue.setText("0");
                                txtpromocode.setText("Appply Promoce : No Promocode Apply");
                                txttotalcharge.setText(counsellingamount);
                                famount = String.valueOf(counsellingamount);
                                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor prefsEditr = mypref.edit();
                                prefsEditr.putString("offeramountcharge", famount);
                                prefsEditr.putString("offerpromocodeid", offerid);
                                prefsEditr.commit();
                            } else {
                                double finalamount = counselorcharge - offamount;
                                famount = String.valueOf(finalamount);
                                Toast.makeText(getActivity(), "Offer Applied", Toast.LENGTH_SHORT).show();
                                txtcharge.setText(counsellingamount);
                                txtoffervalue.setText(OfferValue);
                                txtpromocode.setText("Appply Promoce : " + OfferCode);
                                txttotalcharge.setText(famount);
                                SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor prefsEditr = mypref.edit();
                                prefsEditr.putString("OfferValue", OfferValue);
                                prefsEditr.putString("OfferCode", OfferCode);
                                prefsEditr.putString("offeramountcharge", famount);
                                prefsEditr.commit();
                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void GetOffers() {
        String url = AppConstants.Base_URL+"GetOffers";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                  if (response.equals("")||response.equals(null)||response.equals("null"))
                  {
                      Toast.makeText(getActivity(), "No offer is available", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      docdata.clear();
                      JSONArray jsonArray = new JSONArray(response);
                      for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject jsonobject = jsonArray.getJSONObject(i);
                          HealthChampPlusData statedata = new HealthChampPlusData();
                          statedata.setId(jsonobject.getString("ID"));
                          statedata.setName(jsonobject.getString("OfferName"));
                          statedata.setQualify(jsonobject.getString("OfferCode"));
                          statedata.setAddress(jsonobject.getString("OfferValue"));
                          docdata.add(statedata);
                      }
                  }
                    GetPromocodeOfferAdapter adapter = new GetPromocodeOfferAdapter((ArrayList<HealthChampPlusData>) docdata, getActivity());
                    recyclerviewoffer.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    private void GetTransactionID() {
        String url = AppConstants.Base_URL+"GetTransactionID";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    bookingcounsellingtransactionid = response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void PostCounsellingRecords() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String registername  = mypref1.getString("Name", "");
        String offerpromocodeid = mypref1.getString("offerpromocodeid","");
        String famount = mypref1.getString("offeramountcharge","");

        String registermobileno = mypref1.getString("parentMobileNo","");
        String registeremailid = mypref1.getString("EmailID","");
        String counsellorprofileid = mypref1.getString("counsellorprofileid","");
        String parentGender = mypref1.getString("parentGender","");
        String timeslotstarttime = mypref1.getString("timeslotstarttime","");
        String timeslotendtime = mypref1.getString("timeslotendtime","");
        if (parentGender.equals("Male"))
        {
            parentgenderid = "1";
        }
        if (parentGender.equals("Female"))
        {
            parentgenderid = "2";
        }
        if (parentGender.equals("Other"))
        {
            parentgenderid = "3";
        }
        String counsellingtypeiduser = mypref1.getString("counsellingtypeiduser","");
        String timeslotid = mypref1.getString("timeslotid","");
        String userid = mypref1.getString("userid","");
        String bookingdate= mypref1.getString("bookingdate","");
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject familydoctordetails = new JSONObject();
        try {
            familydoctordetails.put("ID", userid);
            familydoctordetails.put("Name", registername);
            familydoctordetails.put("EmailID", registeremailid);
            familydoctordetails.put("MobileNo", registermobileno);
            familydoctordetails.put("Age", "");
            familydoctordetails.put("CreateDate", todaydate);
            familydoctordetails.put("CreateBy", userid);
            familydoctordetails.put("Status", 1);
            familydoctordetails.put("CounsellingDate", bookingdate);
            familydoctordetails.put("LoginID", userid);
            familydoctordetails.put("GenderID", parentgenderid);
            familydoctordetails.put("CountryID", "");
            familydoctordetails.put("StateID", "");
            familydoctordetails.put("DistrictID", "");
            familydoctordetails.put("SchoolID", "");
            familydoctordetails.put("SlotID", "");
            familydoctordetails.put(" ClassID", "");
            familydoctordetails.put("SectionID", "");
            familydoctordetails.put("CounsellingTypeID", counsellingtypeiduser);
            familydoctordetails.put("CounsellingModeID", counsellingmodeid);
            familydoctordetails.put("CounsellorID", counsellorprofileid);
            familydoctordetails.put("StartTime", timeslotstarttime);
            familydoctordetails.put("EndTime", timeslotendtime);
            familydoctordetails.put("PaymentStatus", "Transaction Successful!");
            familydoctordetails.put("Paid", famount);
            familydoctordetails.put("OfferID", offerpromocodeid);
            familydoctordetails.put("PaymentConfigID", counsellingamountid);
            familydoctordetails.put("PaymentDate", todaydate);
            familydoctordetails.put("TransactionID", bookingcounsellingtransactionid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstants.Base_URL+"PostCounsellingRecords";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, url, familydoctordetails,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.dismiss();
                            if (!response.equals(""))
                            {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                    Toast.makeText(getActivity(), "Counselling Booked Success", Toast.LENGTH_SHORT).show();
                                    ConfirmBooking f1 = new ConfirmBooking();
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.nav_host_fragment, f1);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }

                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Counselling Booked Success", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                error.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    @SuppressLint("NewApi")
    public String encodeBase64(String encode)
    {
        System.out.println("[encodeBase64] Base64 encode : "+encode);
        String decode=null;
        String data;
        try {
            decode=  Base64.encodeToString(encode.getBytes(), Base64.NO_WRAP);
        } catch (Exception e) {
            System.out.println("Unable to decode : "+ e);
        }
        return  decode;
    }
    public String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for(int i = 0; i < hash.length; ++i) {
                String hex = Integer.toHexString(255 & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }
    public String md5(String in, int y) {
        String md5text = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for(int i = 0; i < len; ++i) {
                sb.append(Character.forDigit((a[i] & 240) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 15, 16));
            }
            md5text = sb.toString();
            return sb.toString();
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
            return md5text;
        }
    }
    public String getProtoDomain(String sDomain) {
        int slashslash = sDomain.indexOf("//") + 2;
        return sDomain.substring(0, sDomain.indexOf(47, slashslash));
    }
    public static String getEncodedValueWithSha2(String hashKey,String ...param)
    {
        String resp = null;
        StringBuilder sb = new StringBuilder();
        for (String s : param) {
            sb.append(s);
        }
        try{
            System.out.println("[getEncodedValueWithSha2]String to Encode =" + sb.toString());
            resp = byteToHexString(encodeWithHMACSHA2(sb.toString(), hashKey));
        }catch(Exception e)
        {
            System.out.println("[getEncodedValueWithSha2]Unable to encocd value with key :" + hashKey + " and input :" + sb.toString());
            e.printStackTrace();
        }
        return resp;
    }
    public static String byteToHexString(byte byData[])
    {
        StringBuilder sb = new StringBuilder(byData.length * 2);
        for(int i = 0; i < byData.length; i++)
        {
            int v = byData[i] & 0xff;
            if(v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    public static byte[] encodeWithHMACSHA2(String text,String keyString) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.io.UnsupportedEncodingException
    {
        java.security.Key sk = new javax.crypto.spec.SecretKeySpec(keyString.getBytes("UTF-8"),"HMACSHA512");
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance(sk.getAlgorithm());
        mac.init(sk);
        byte[] hmac = mac.doFinal(text.getBytes("UTF-8"));
        return hmac;
    }
    public String trimmingText(String str)
    {
        String txt = null;
        if(str!=null)
        {
            txt = str.replaceAll(" ", "%20");
        }
        return txt;
    }
    public String trimmingTextWithEmpty(String str)
    {
        String txt = "";
        if(str!=null)
        {
            txt = str.replaceAll(" ", "%20");
        }
        return txt;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RESULTCODE--->" + resultCode);
        System.out.println("REQUESTCODE--->" + requestCode);
        System.out.println("RESULT_OK--->" + RESULT_OK);
        if (requestCode == 1) {
            System.out.println("---------INSIDE ATOM-------");
            if (data != null) {
                message = data.getStringExtra("status");
                String[] resKey = data.getStringArrayExtra("responseKeyArray");
                String[] resValue = data.getStringArrayExtra("responseValueArray");
                final Map<String , String > map = new HashMap<String, String>();
                String mmp_txn= map.get("mmp_txn");
                String mer_txn = map.get("mer_txn");
                String f_code = map.get("f_code");
                String prod = map.get("prod");
                String amt = map.get("amt");
                if(resKey!=null && resValue!=null)
                {
                    for(int i=0; i<resKey.length; i++)
                        System.out.println("  "+i+" resKey : "+resKey[i]+" resValue : "+resValue[i]);
                }
                if (message.equals("Transaction Cancelled!"))
                {

                    ResponseValue = resKey[0] + "=" + resValue[0] +resKey[1] + "=" + resValue[1] +"=" +  resKey[2] + "=" + resValue[2]+ resKey[3] + "=" + resValue[3]+ resKey[4] + "=" + resValue[4]+ resKey[5] +"=" +  resValue[5]+ resKey[6] +"=" + resValue[6]+ resKey[7] +"=" + resValue[7]+ resKey[8] +"=" + resValue[8]+ resKey[9] +"=" + resValue[9] + resKey[10] +"=" + resValue[10] + resKey[11] +"=" + resValue[11]+  resKey[12] +"=" + resValue[12]+ resKey[13] +"=" + resValue[13];  //+ resKey[14] +"=" + resValue[14] + resKey[15] +"=" + resValue[15]+ resKey[16] +"=" + resValue[16]+ resKey[17] +"=" + resValue[17]+ resKey[18] +"=" + resValue[18]+ resKey[19] +"=" + resValue[19]+ resKey[20] + "=" +resValue[20]+ resKey[21] +"=" + resValue[21] + resKey[22] +"=" +resValue[22] + resKey[23] +"=" +  resValue[23] ;
                   // UpdateInitiatePayment();

                }
                if (message.equals("Transaction Successful!"))
                {
                    ResponseValue = resKey[0] + "=" + resValue[0] +resKey[1] + "=" + resValue[1] +"=" +  resKey[2] + "=" + resValue[2]+ resKey[3] + "=" + resValue[3]+ resKey[4] + "=" + resValue[4]+ resKey[5] +"=" +  resValue[5]+ resKey[6] +"=" + resValue[6]+ resKey[7] +"=" + resValue[7]+ resKey[8] +"=" + resValue[8]+ resKey[9] +"=" + resValue[9] + resKey[10] +"=" + resValue[10] + resKey[11] +"=" + resValue[11]+  resKey[12] +"=" + resValue[12]+ resKey[13] +"=" + resValue[13];  //+ resKey[14] +"=" + resValue[14]+ resKey[15] +"=" + resValue[15]+ resKey[16] +"=" + resValue[16]+ resKey[17] +"=" + resValue[17]+ resKey[18] +"=" + resValue[18]+ resKey[19] +"=" + resValue[19]+ resKey[20] + "=" +resValue[20]+ resKey[21] +"=" + resValue[21] + resKey[22] +"=" +resValue[22] + resKey[23] +"=" +  resValue[23] ;
                  //  UpdateInitiatePayment();
                    PostCounsellingRecords();

                }
                if (message.equals("Transaction Failed!"))
                {

                    ResponseValue = resKey[0] + "=" + resValue[0] +resKey[1] + "=" + resValue[1] +"=" +  resKey[2] + "=" + resValue[2]+ resKey[3] + "=" + resValue[3]+ resKey[4] + "=" + resValue[4]+ resKey[5] +"=" +  resValue[5]+ resKey[6] +"=" + resValue[6]+ resKey[7] +"=" + resValue[7]+ resKey[8] +"=" + resValue[8]+ resKey[9] +"=" + resValue[9] + resKey[10] +"=" + resValue[10] + resKey[11] +"=" + resValue[11]+  resKey[12] +"=" + resValue[12]+ resKey[13] +"=" + resValue[13];  //+ resKey[14] +"=" + resValue[14]+ resKey[15] +"=" + resValue[15]+ resKey[16] +"=" + resValue[16]+ resKey[17] +"=" + resValue[17]+ resKey[18] +"=" + resValue[18]+ resKey[19] +"=" + resValue[19]+ resKey[20] + "=" +resValue[20]+ resKey[21] +"=" + resValue[21] + resKey[22] +"=" +resValue[22] + resKey[23] +"=" +  resValue[23] ;
                   // UpdateInitiatePayment();
                }
            }
        }
    }

    private void PostInitiatePayment() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(true);
        progressBar.setMessage("loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String registername  = mypref1.getString("Name", "");
        String registermobileno = mypref1.getString("parentMobileNo","");
        String registeremailid = mypref1.getString("EmailID","");
        String counsellorprofileid = mypref1.getString("counsellorprofileid","");
        String parentGender = mypref1.getString("parentGender","");
        String timeslotstarttime = mypref1.getString("timeslotstarttime","");
        String timeslotendtime = mypref1.getString("timeslotendtime","");
        if (parentGender.equals("Male"))
        {
            parentgenderid = "1";
        }
        if (parentGender.equals("Female"))
        {
            parentgenderid = "2";
        }
        if (parentGender.equals("Other"))
        {
            parentgenderid = "3";
        }
        String offerpromocodeid = mypref1.getString("offerpromocodeid","");
        String famount = mypref1.getString("offeramountcharge","");
        String counsellingtypeiduser = mypref1.getString("counsellingtypeiduser","");
        String timeslotid = mypref1.getString("timeslotid","");
        String expertservicecategoryid = mypref1.getString("expertservicecategoryid","");
        String counsellingtypeiduser1 = mypref1.getString("counsellingtypeiduser","");
        String userid = mypref1.getString("userid","");
        String bookingdate= mypref1.getString("bookingdate","");
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject familydoctordetails = new JSONObject();
        try {
            familydoctordetails.put("ID", userid);
            familydoctordetails.put("LoginID", userid);
            familydoctordetails.put("TransactionID", bookingcounsellingtransactionid);
            familydoctordetails.put("BankTransactionID", "");
            familydoctordetails.put("InitiateDate", todaydate);
            familydoctordetails.put("ServiceID", expertservicecategoryid);
            familydoctordetails.put("PackageID", counsellingtypeiduser1);
            familydoctordetails.put("PaidAmount", famount);
            familydoctordetails.put("TotalAmount", counsellingamount);
            familydoctordetails.put("GSTAmount", "");
            familydoctordetails.put("PaymentMode", "Online");
            familydoctordetails.put("BankResponseDate", "");
            familydoctordetails.put("GateWayRequest", sendmainURL);
            familydoctordetails.put("GateWayResponse", ResponseValue);
            familydoctordetails.put("TransactionStatus", message);
            familydoctordetails.put("Status", "1");
            familydoctordetails.put("UpdatedDate", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = AppConstants.Base_URL+"PostInitiatePayment";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, url, familydoctordetails,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressBar.dismiss();
                            if (!response.equals(""))
                            {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonobject = new JSONObject(String.valueOf(response));
                                 //   Toast.makeText(getActivity(), "Counselling Booked Success", Toast.LENGTH_SHORT).show();
//                                    ConfirmBooking f1 = new ConfirmBooking();
//                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                    ft.replace(R.id.nav_host_fragment, f1);
//                                    ft.addToBackStack(null);
//                                    ft.commit();
                                }

                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Counselling Booked Success", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                error.printStackTrace();
              //  Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}