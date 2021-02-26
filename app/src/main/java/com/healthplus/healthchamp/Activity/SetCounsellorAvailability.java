package com.healthplus.healthchamp.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthplus.healthchamp.Adapter.SpinnerStateAdapter;
import com.healthplus.healthchamp.Model.SpinnerData;
import com.healthplus.healthchamp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class SetCounsellorAvailability extends AppCompatActivity {
    CheckBox checkboxmonday,checkboxtuesday,checkboxwednesday,checkboxthursday,checkboxfriday,checkboxsaturday,checkall;
    boolean isFromView;
    TextView txtsectiontime,txtmonday,txttuesday,txtwednesday,txtthursday,txtfriday,txtsaturday;
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    EditText edtfrom, edtto;
    Button btnschedule;
    EditText edtmondayfrom,edtmondayto,edttuesdayfrom,edttuesdayto,edtwednesdayfrom,edtwednesdayto,edtthursdayfrom,
            edtthursdayto,edtfridayfrom,edtfridayto,edtsaturdayfrom,edtsaturdayto;
    LinearLayout layout_bookslot;
    List<SpinnerData> slotlist = new ArrayList<>();
    List<SpinnerData> counciltypelist = new ArrayList<>();
    EditText edtdate;
    Spinner spinner_type,spinner_slot;
    final Calendar myCalendar = Calendar.getInstance();
    String mondayfromtime,tuesdayfromtime,wedfromtime,thursdayfromtime,frifromtime,satfromtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setcounselloravailability);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_800));
        }
        edtdate=findViewById(R.id.edtdate);
        txtmonday=findViewById(R.id.txtmonday);
        txttuesday=findViewById(R.id.txttuesday);
        txtwednesday=findViewById(R.id.txtwednesday);
        txtthursday=findViewById(R.id.txtthursday);
        txtfriday=findViewById(R.id.txtfriday);
        txtsaturday=findViewById(R.id.txtsaturday);
       // btnschedule = findViewById(R.id.btnschedule);
        spinner_slot=findViewById(R.id.spinner_slot);
        spinner_type=findViewById(R.id.spinner_type);
        layout_bookslot=findViewById(R.id.layout_bookslot);
        txtsectiontime=findViewById(R.id.txtsectiontime);
        edtsaturdayto=findViewById(R.id.edtsaturdayto);
        edtsaturdayfrom=findViewById(R.id.edtsaturdayfrom);
        edtfridayto=findViewById(R.id.edtfridayto);
        checkall=findViewById(R.id.checkall);
        edtfridayfrom=findViewById(R.id.edtfridayfrom);
        edtthursdayto=findViewById(R.id.edtthursdayto);
        edtthursdayfrom=findViewById(R.id.edtthursdayfrom);
        edtwednesdayto=findViewById(R.id.edtwednesdayto);
        edtwednesdayfrom=findViewById(R.id.edtwednesdayfrom);
        edttuesdayto=findViewById(R.id.edttuesdayto);
        edttuesdayfrom=findViewById(R.id.edttuesdayfrom);
        edtmondayfrom=findViewById(R.id.edtmondayfrom);
        edtmondayto=findViewById(R.id.edtmondayto);
        edtfrom=findViewById(R.id.edtfrom);
        edtto=findViewById(R.id.edtto);
        checkboxmonday=findViewById(R.id.checkboxmonday);
        checkboxtuesday=findViewById(R.id.checkboxtuesday);
        checkboxwednesday=findViewById(R.id.checkboxwednesday);
        checkboxthursday=findViewById(R.id.checkboxthursday);
        checkboxfriday=findViewById(R.id.checkboxfriday);
        checkboxsaturday=findViewById(R.id.checkboxsaturday);
        slotlist = new ArrayList<>();
        GetSlot();
        counciltypelist = new ArrayList<>();
        GetCounsellingType();
//        btnschedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                postCounsellingSchedule();
//            }
//        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SetCounsellorAvailability.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edtmondayfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }

                        edtmondayfrom.setText(selectedHour + ":" + selectedMinute + AM_PM);
                        mondayfromtime = selectedHour + ":" + selectedMinute;

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtmondayto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String myTime = mondayfromtime;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        Date d = null;
                        try {
                            d = df.parse(myTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        cal.add(Calendar.MINUTE, 30);
                        String newTime = df.format(cal.getTime());
                        edtmondayto.setText(newTime + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edttuesdayfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this,  new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                      //  selectedMinute = Integer.parseInt("30");
                        edttuesdayfrom.setText(selectedHour + ":" + selectedMinute + AM_PM);
                        tuesdayfromtime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edttuesdayto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String myTime = tuesdayfromtime;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        Date d = null;
                        try {
                            d = df.parse(myTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        cal.add(Calendar.MINUTE, 30);
                        String newTime = df.format(cal.getTime());
                        edttuesdayto.setText(newTime + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });

        edtwednesdayfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        edtwednesdayfrom.setText(selectedHour + ":" + selectedMinute + AM_PM);
                        wedfromtime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtwednesdayto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String myTime = wedfromtime;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        Date d = null;
                        try {
                            d = df.parse(myTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        cal.add(Calendar.MINUTE, 30);
                        String newTime = df.format(cal.getTime());
                        edtwednesdayto.setText(newTime + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });

        edtthursdayfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        edtthursdayfrom.setText(selectedHour + ":" + selectedMinute + AM_PM);
                        thursdayfromtime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtthursdayto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String myTime = thursdayfromtime;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        Date d = null;
                        try {
                            d = df.parse(myTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        cal.add(Calendar.MINUTE, 30);
                        String newTime = df.format(cal.getTime());
                        edtthursdayto.setText(newTime + AM_PM);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtfridayfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        edtfridayfrom.setText(selectedHour + ":" + selectedMinute + AM_PM);
                        frifromtime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtfridayto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String myTime = frifromtime;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        Date d = null;
                        try {
                            d = df.parse(myTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        cal.add(Calendar.MINUTE, 30);
                        String newTime = df.format(cal.getTime());
                        edtfridayto.setText(newTime + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtsaturdayfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        edtsaturdayfrom.setText(selectedHour + ":" + selectedMinute + AM_PM);
                        satfromtime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        edtsaturdayto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetCounsellorAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        String myTime = satfromtime;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        Date d = null;
                        try {
                            d = df.parse(myTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(d);
                        cal.add(Calendar.MINUTE, 30);
                        String newTime = df.format(cal.getTime());
                        edtsaturdayto.setText(newTime + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Slot");
                mTimePicker.show();
            }
        });
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel2();
            }

        };
        edtfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog mDate = new DatePickerDialog(SetCounsellorAvailability.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),  myCalendar1.get(Calendar.DAY_OF_MONTH));
                mDate.show();

            }
        });
        edtto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog mDate = new DatePickerDialog(SetCounsellorAvailability.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),  myCalendar2.get(Calendar.DAY_OF_MONTH));
                mDate.show();
            }
        });
        isFromView = true;
        checkall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //boolean isChecked = checkall.isChecked();
                isFromView = false;
                if (!isFromView) {
                    if (isChecked) {
                        checkboxmonday.setChecked(true);
                        checkboxtuesday.setChecked(true);
                        checkboxwednesday.setChecked(true);
                        checkboxthursday.setChecked(true);
                        checkboxfriday.setChecked(true);
                        checkboxsaturday.setChecked(true);
                        isFromView = false;
                        txtmonday.setEnabled(false);
                        txttuesday.setEnabled(false);
                        txtwednesday.setEnabled(false);
                        txtthursday.setEnabled(false);
                        txtfriday.setEnabled(false);
                        txtsaturday.setEnabled(false);
                        layout_bookslot.setVisibility(View.VISIBLE);


                    } else {
                        checkboxmonday.setChecked(false);
                        checkboxtuesday.setChecked(false);
                        checkboxwednesday.setChecked(false);
                        checkboxthursday.setChecked(false);
                        checkboxfriday.setChecked(false);
                        checkboxsaturday.setChecked(false);
                        isFromView = false;
                        txtmonday.setEnabled(true);
                        txttuesday.setEnabled(true);
                        txtwednesday.setEnabled(true);
                        txtthursday.setEnabled(true);
                        txtfriday.setEnabled(true);
                        txtsaturday.setEnabled(true);
                        layout_bookslot.setVisibility(View.GONE);

                    }

                }
            }
        });
       // isFromView = false;
        checkboxmonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFromView = false;
                if (!isFromView) {
                        if (isChecked) {
                            checkboxmonday.setChecked(true);
                            isFromView = false;
                        } else {
                            checkboxmonday.setChecked(false);
                            isFromView = false;
                        }
                    }
                }
        });
      //  isFromView = false;
        checkboxtuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFromView = false;
                if (!isFromView) {
                    if (isChecked) {
                        checkboxtuesday.setChecked(true);
                        isFromView = false;
                    } else {
                        checkboxtuesday.setChecked(false);
                        isFromView = false;
                    }
                }
            }
        });
  //     isFromView = false;
        checkboxwednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFromView = false;
                if (!isFromView) {
                    if (isChecked) {
                        checkboxwednesday.setChecked(true);
                        isFromView = false;
                    } else {
                        checkboxwednesday.setChecked(false);
                        isFromView = false;
                    }
                }

            }
        });
     //   isFromView = false;
        checkboxthursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFromView = false;
                if (!isFromView) {
                    if (isChecked) {
                        checkboxthursday.setChecked(true);
                        isFromView = false;
                    } else {
                        checkboxthursday.setChecked(false);
                        isFromView = false;
                    }
                }


            }
        });
      //  isFromView = false;
        checkboxfriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFromView = false;
                if (!isFromView) {
                    if (isChecked) {
                        checkboxfriday.setChecked(true);
                        isFromView = false;
                    } else {
                        checkboxfriday.setChecked(false);
                        isFromView = false;
                    }
                }


            }
        });
      //  isFromView = false;
        checkboxsaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFromView = false;
                if (!isFromView) {
                    if (isChecked) {
                        checkboxsaturday.setChecked(true);
                        isFromView = false;
                    } else {
                        checkboxsaturday.setChecked(false);
                        isFromView = false;
                    }
                }


            }
        });
        layout_bookslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkboxmonday.isChecked() && checkboxtuesday.isChecked() && checkboxwednesday.isChecked() && checkboxthursday.isChecked() && checkboxfriday.isChecked() && checkboxsaturday.isChecked()){
                        if (edtmondayfrom.getText().toString().isEmpty() || edtmondayto.getText().toString().isEmpty() || edttuesdayfrom.getText().toString().isEmpty() || edttuesdayto.getText().toString().isEmpty() || edtwednesdayfrom.getText().toString().isEmpty() || edtwednesdayto.getText().toString().isEmpty() ||
                                edtthursdayfrom.getText().toString().isEmpty() || edtthursdayto.getText().toString().isEmpty() || edtfridayfrom.getText().toString().isEmpty() || edtfridayto.getText().toString().isEmpty() ||
                                edtsaturdayfrom.getText().toString().isEmpty() || edtsaturdayto.getText().toString().isEmpty()) {
                            Toast.makeText(SetCounsellorAvailability.this, "Please set slot before submitting", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            PostCounsellingScheduleMonday();
                            PostCounsellingScheduleTuesday();
                            PostCounsellingScheduleWednesday();
                            PostCounsellingScheduleThursday();
                            PostCounsellingScheduleFriday();
                            PostCounsellingScheduleSaturday();
                        }

                  }

//                    else {
//                        for (int i = 0; i < 6; i++) {
//
//                            if (checkboxmonday.isChecked()) {
//                                if (edtmondayfrom.getText().toString().isEmpty() || edtmondayto.getText().toString().isEmpty()) {
//                                    Toast.makeText(SetCounsellorAvailability.this, "Fill date and time", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    PostCounsellingScheduleMonday();
//                                }
//                            } else if (checkboxtuesday.isChecked()) {
//                                if (edttuesdayfrom.getText().toString().isEmpty() || edttuesdayto.getText().toString().isEmpty()) {
//                                    Toast.makeText(SetCounsellorAvailability.this, "Fill date and time", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    PostCounsellingScheduleTuesday();
//                                }
//                            } else if (checkboxwednesday.isChecked()) {
//                                if (edtwednesdayfrom.getText().toString().isEmpty() || edtwednesdayto.getText().toString().isEmpty()) {
//                                    Toast.makeText(SetCounsellorAvailability.this, "Fill date and time", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    PostCounsellingScheduleWednesday();
//                                }
//                            } else if (checkboxthursday.isChecked()) {
//                                if (edtthursdayfrom.getText().toString().isEmpty() || edtthursdayto.getText().toString().isEmpty()) {
//                                    Toast.makeText(SetCounsellorAvailability.this, "Fill date and time", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    PostCounsellingScheduleThursday();
//                                }
//                            } else if (checkboxfriday.isChecked()) {
//                                if (edtfridayfrom.getText().toString().isEmpty() || edtfridayto.getText().toString().isEmpty()) {
//                                    Toast.makeText(SetCounsellorAvailability.this, "Fill date and time", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    PostCounsellingScheduleFriday();
//                                }
//                            } else if (checkboxsaturday.isChecked()) {
//                                if (edtsaturdayfrom.getText().toString().isEmpty() || edtsaturdayto.getText().toString().isEmpty()) {
//                                    Toast.makeText(SetCounsellorAvailability.this, "Fill date and time", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    PostCounsellingScheduleSaturday();
//                                }
//                            } else if (!checkboxmonday.isChecked() && !checkboxtuesday.isChecked() && !checkboxwednesday.isChecked() && !checkboxthursday.isChecked() && !checkboxfriday.isChecked() && !checkboxsaturday.isChecked()) {
//                                Toast.makeText(SetCounsellorAvailability.this, "Please set atleast one slot before moving next", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }

            }
        });
        ArrayList<String> results = getTimeSet(true);
        txtsectiontime.setText(String.valueOf(results));

        txtmonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtmondayfrom.getText().toString().isEmpty() || edtmondayto.getText().toString().isEmpty()) {
                                    Toast.makeText(SetCounsellorAvailability.this, "Fill slot", Toast.LENGTH_SHORT).show();
                                } else {
                                    PostCounsellingScheduleMonday();
                                }
            }
        });
        txttuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edttuesdayfrom.getText().toString().isEmpty() || edttuesdayto.getText().toString().isEmpty()) {
                    Toast.makeText(SetCounsellorAvailability.this, "Fill slot", Toast.LENGTH_SHORT).show();
                } else {
                    PostCounsellingScheduleTuesday();
                }
            }
        });
        txtwednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtwednesdayfrom.getText().toString().isEmpty() || edtwednesdayto.getText().toString().isEmpty()) {
                    Toast.makeText(SetCounsellorAvailability.this, "Fill slot", Toast.LENGTH_SHORT).show();
                } else {
                    PostCounsellingScheduleWednesday();
                }
            }
        });
        txtthursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtthursdayfrom.getText().toString().isEmpty() || edtthursdayto.getText().toString().isEmpty()) {
                    Toast.makeText(SetCounsellorAvailability.this, "Fill slot", Toast.LENGTH_SHORT).show();
                } else {
                    PostCounsellingScheduleThursday();
                }
            }
        });
        txtfriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtfridayfrom.getText().toString().isEmpty() || edtfridayto.getText().toString().isEmpty()) {
                    Toast.makeText(SetCounsellorAvailability.this, "Fill slot", Toast.LENGTH_SHORT).show();
                } else {
                    PostCounsellingScheduleFriday();
                }
            }
        });
        txtsaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtsaturdayfrom.getText().toString().isEmpty() || edtsaturdayto.getText().toString().isEmpty()) {
                    Toast.makeText(SetCounsellorAvailability.this, "Fill slot", Toast.LENGTH_SHORT).show();
                } else {
                    PostCounsellingScheduleSaturday();
                }
            }
        });


    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (myCalendar.before(Calendar.DAY_OF_MONTH))
        {
            Toast.makeText(SetCounsellorAvailability.this, "You can't apply date before today", Toast.LENGTH_SHORT).show();
        }
        else{
            edtdate.setText(sdf.format(myCalendar.getTime()));
        }
    }
    private ArrayList<String> getTimeSet(boolean isCurrentDay) {
        ArrayList results = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);// what should be the default?
        if(!isCurrentDay)
            calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (int i = 0; i < 9; i++) {
            String  day1 = sdf.format(calendar.getTime());

            // add 15 minutes to the current time; the hour adjusts automatically!
            calendar.add(Calendar.MINUTE, 15);

            String day2 = sdf.format(calendar.getTime());

            String day = day1 + " - " + day2;
            results.add(i, day);


        }

        return results;
    }
    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (myCalendar1.before(Calendar.DAY_OF_MONTH))
        {
            Toast.makeText(SetCounsellorAvailability.this, "You can't apply date before today", Toast.LENGTH_SHORT).show();
        }
        else{
            int weekday = Integer.parseInt(String.valueOf(myCalendar1.get(Calendar.DAY_OF_WEEK)));
            int finalweekday = weekday - 1;
            edtfrom.setText(sdf.format(myCalendar1.getTime()));

            Calendar calendar = Calendar.getInstance();
            String[] days = new String[] {  "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
            int day = finalweekday - 1;
            if (day==0)
            {
                checkboxmonday.setChecked(true);
            }
            if (day==1)
            {
                checkboxtuesday.setChecked(true);
            }
            if (day==2)
            {
                checkboxwednesday.setChecked(true);
            }
            if (day==3)
            {
                checkboxthursday.setChecked(true);
            }
            if (day==4)
            {
                checkboxfriday.setChecked(true);
            }
            if (day==5)
            {
                checkboxsaturday.setChecked(true);
            }
        }
    }
    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (myCalendar2.before(myCalendar1))
        {
            Toast.makeText(SetCounsellorAvailability.this, "Enter valid to date", Toast.LENGTH_SHORT).show();
        }
        else{
            int weekday = Integer.parseInt(String.valueOf(myCalendar1.get(Calendar.DAY_OF_WEEK)));
            int finalweekday = weekday - 1;
            edtto.setText(sdf.format(myCalendar2.getTime()));
            Calendar calendar = Calendar.getInstance();
            String[] days = new String[] {  "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
            int day = finalweekday - 1;
            if (day==0)
            {
                checkboxmonday.setChecked(true);
            }
            if (day==1)
            {
                checkboxtuesday.setChecked(true);
            }
            if (day==2)
            {
                checkboxwednesday.setChecked(true);
            }
            if (day==3)
            {
                checkboxthursday.setChecked(true);
            }
            if (day==4)
            {
                checkboxfriday.setChecked(true);
            }
            if (day==5)
            {
                checkboxsaturday.setChecked(true);
            }
        }
    }
    private void PostCounsellingScheduleMonday() {
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String userid = mypref1.getString("userid", "");
        String counsellortypeid = mypref1.getString("counsellortypeid", "");
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ID", userid);
            jsonObject.put("CounsellingDate", todaydate);
            jsonObject.put("CounsellingTypeID", counsellortypeid);
            jsonObject.put("StartTime", edtmondayfrom.getText().toString());
            jsonObject.put("EndTime", edtmondayto.getText().toString());
            jsonObject.put("DayID", 1);
            jsonObject.put("CounsellingModeID", "");
            jsonObject.put("MonthID", "");
            jsonObject.put("CounsellorID", userid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", userid);
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");

        } catch(
                JSONException e)
        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"PostCounsellorAvailability";
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        edtmondayfrom.getText().clear();
                        edtmondayto.getText().clear();
                        Toast.makeText(SetCounsellorAvailability.this, "Slot Schedule Success", Toast.LENGTH_SHORT).show();
                        checkboxmonday.setChecked(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
    private void PostCounsellingScheduleTuesday() {
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String userid = mypref1.getString("userid", "");
        String counsellortypeid = mypref1.getString("counsellortypeid", "");
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ID", userid);
            jsonObject.put("CounsellingDate", todaydate);
            jsonObject.put("CounsellingTypeID", counsellortypeid);
            jsonObject.put("StartTime", edttuesdayfrom.getText().toString());
            jsonObject.put("EndTime", edttuesdayto.getText().toString());
            jsonObject.put("DayID", 2);
            jsonObject.put("CounsellingModeID", "");
            jsonObject.put("MonthID", "");
            jsonObject.put("CounsellorID", userid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", userid);
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");

        } catch(
                JSONException e)
        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"PostCounsellorAvailability";
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        edttuesdayfrom.getText().clear();
                        edttuesdayto.getText().clear();
                        Toast.makeText(SetCounsellorAvailability.this, "Slot Schedule Success", Toast.LENGTH_SHORT).show();
                        checkboxtuesday.setChecked(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
    private void PostCounsellingScheduleWednesday() {
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String userid = mypref1.getString("userid", "");
        String counsellortypeid = mypref1.getString("counsellortypeid", "");
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ID", userid);
            jsonObject.put("CounsellingDate", todaydate);
            jsonObject.put("CounsellingTypeID", counsellortypeid);
            jsonObject.put("StartTime", edtwednesdayfrom.getText().toString());
            jsonObject.put("EndTime", edtwednesdayto.getText().toString());
            jsonObject.put("DayID", 3);
            jsonObject.put("CounsellingModeID", "");
            jsonObject.put("MonthID", "");
            jsonObject.put("CounsellorID", userid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", userid);
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");

        } catch(
                JSONException e)
        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"PostCounsellorAvailability";
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        edtwednesdayfrom.getText().clear();
                        edtwednesdayto.getText().clear();
                        Toast.makeText(SetCounsellorAvailability.this, "Slot Schedule Success", Toast.LENGTH_SHORT).show();
                        checkboxwednesday.setChecked(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
    private void PostCounsellingScheduleThursday() {
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String userid = mypref1.getString("userid", "");
        String counsellortypeid = mypref1.getString("counsellortypeid", "");
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ID", userid);
            jsonObject.put("CounsellingDate", todaydate);
            jsonObject.put("CounsellingTypeID", counsellortypeid);
            jsonObject.put("StartTime", edtthursdayfrom.getText().toString());
            jsonObject.put("EndTime", edtthursdayto.getText().toString());
            jsonObject.put("DayID", 4);
            jsonObject.put("CounsellingModeID", "");
            jsonObject.put("MonthID", "");
            jsonObject.put("CounsellorID", userid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", userid);
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");

        } catch(
                JSONException e)
        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"PostCounsellorAvailability";
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        edtthursdayfrom.getText().clear();
                        edtthursdayto.getText().clear();
                        Toast.makeText(SetCounsellorAvailability.this, "Slot Schedule Success", Toast.LENGTH_SHORT).show();
                        checkboxthursday.setChecked(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
    private void PostCounsellingScheduleFriday() {
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String userid = mypref1.getString("userid", "");
        String counsellortypeid = mypref1.getString("counsellortypeid", "");
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ID", userid);
            jsonObject.put("CounsellingDate", todaydate);
            jsonObject.put("CounsellingTypeID", counsellortypeid);
            jsonObject.put("StartTime", edtfridayfrom.getText().toString());
            jsonObject.put("EndTime", edtfridayto.getText().toString());
            jsonObject.put("DayID", 5);
            jsonObject.put("CounsellingModeID", "");
            jsonObject.put("MonthID", "");
            jsonObject.put("CounsellorID", userid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", userid);
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");

        } catch(
                JSONException e)
        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"PostCounsellorAvailability";
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        edtfridayfrom.getText().clear();
                        edtfridayto.getText().clear();
                        Toast.makeText(SetCounsellorAvailability.this, "Slot Schedule Success", Toast.LENGTH_SHORT).show();
                        checkboxfriday.setChecked(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
    private void PostCounsellingScheduleSaturday() {
        String todaydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String userid = mypref1.getString("userid", "");
        String counsellortypeid = mypref1.getString("counsellortypeid", "");
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ID", userid);
            jsonObject.put("CounsellingDate", todaydate);
            jsonObject.put("CounsellingTypeID", counsellortypeid);
            jsonObject.put("StartTime", edtsaturdayfrom.getText().toString());
            jsonObject.put("EndTime", edtsaturdayto.getText().toString());
            jsonObject.put("DayID", 6);
            jsonObject.put("CounsellingModeID", "");
            jsonObject.put("MonthID", "");
            jsonObject.put("CounsellorID", userid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", userid);
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");

        } catch(
                JSONException e)
        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"PostCounsellorAvailability";
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        edtsaturdayfrom.getText().clear();
                        edtsaturdayto.getText().clear();
                        Toast.makeText(SetCounsellorAvailability.this, "Slot Schedule Success", Toast.LENGTH_SHORT).show();
                        checkboxsaturday.setChecked(false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
    private void GetSlot() {
        String url = AppConstants.Base_URL+"HealthAdmin/GetSlot";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("msg", response);
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        SpinnerData statedata = new SpinnerData();
                        statedata.setName(jsonobject.getString("Name"));
                        statedata.setId(jsonobject.getString("ID"));
                        slotlist.add(statedata);
                    }
                    SpinnerStateAdapter spinnerStateAdapter = new SpinnerStateAdapter(SetCounsellorAvailability.this, android.R.layout.simple_spinner_dropdown_item, (ArrayList<SpinnerData>) slotlist);
                    spinner_slot.setAdapter(spinnerStateAdapter);
                    spinner_slot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String id = slotlist.get(i).getId();
                            String name = slotlist.get(i).getName();
                            SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
                            SharedPreferences.Editor prefsEditr = mypref.edit();
                            prefsEditr.putString("managesessionslotid", id);
                            prefsEditr.putString("managesessionslotname", name);
                            prefsEditr.commit();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        requestQueue.add(stringRequest);
    }
    private void GetCounsellingType() {
        String url = AppConstants.Base_URL+"HealthAdmin/GetCounsellingType";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("msg", response);
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        SpinnerData statedata = new SpinnerData();
                        statedata.setName(jsonobject.getString("name"));
                        statedata.setId(jsonobject.getString("id"));
                        counciltypelist.add(statedata);
                    }
                    SpinnerStateAdapter spinnerStateAdapter = new SpinnerStateAdapter(SetCounsellorAvailability.this, android.R.layout.simple_spinner_dropdown_item, (ArrayList<SpinnerData>) counciltypelist);
                    spinner_type.setAdapter(spinnerStateAdapter);
                    spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String id = counciltypelist.get(i).getId();
                            String name = counciltypelist.get(i).getName();
                            SharedPreferences mypref = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
                            SharedPreferences.Editor prefsEditr = mypref.edit();
                            prefsEditr.putString("managesessioncounciltypeid", id);
                            prefsEditr.putString("managesessioncounciltypename", name);
                            prefsEditr.commit();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        requestQueue.add(stringRequest);
    }

    private void postCounsellingSchedule() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String todaydate= formatter.format(todayDate);
        SharedPreferences mypref1 = PreferenceManager.getDefaultSharedPreferences(SetCounsellorAvailability.this);
        String managesessioncounciltypeid = mypref1.getString("managesessioncounciltypeid", "");
        String managesessionslotid = mypref1.getString("managesessionslotid", "");
        String counsellingdate = edtdate.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("CounsellingDate", counsellingdate);
            jsonObject.put("CounsellingTypeID", managesessioncounciltypeid);
            jsonObject.put("SlotID", managesessionslotid);
            jsonObject.put("Priority", "1");
            jsonObject.put("Status", "1");
            jsonObject.put("CreatedDate", todaydate);
            jsonObject.put("CreatedBy", "1");
            jsonObject.put("UpdatedDate", "");
            jsonObject.put("UpdatedBy", "");
        } catch(JSONException e)

        {
            e.printStackTrace();
        }
        String urlString = AppConstants.Base_URL+"HealthAdmin/PostCounsellingSchedule";

        RequestQueue requestQueue = Volley.newRequestQueue(SetCounsellorAvailability.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Toast.makeText(SetCounsellorAvailability.this, "Set Slot Success", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SetCounsellorAvailability.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }
}