package com.example.bookinghotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.util.Pair;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.bookinghotel.entity.Booked;
import com.example.bookinghotel.entity.Hotel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class BookRoom extends AppCompatActivity {
    FloatingActionButton ivBookBack;
    TextView tvBookPrice,tvSoPhong,tvNgayDat, tvNgayTra;
    Button btnBookRoom, btnNgayDat,btnNgayTra,btnBook;
    CoordinatorLayout main_content;
    ImageView ivBannerBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        getSupportActionBar().hide();
        ivBookBack = findViewById(R.id.ivBookBack);
        tvBookPrice = findViewById(R.id.tvBookPrice);
        btnBookRoom = findViewById(R.id.btnBookRoom);
        tvSoPhong =findViewById(R.id.tvSoPhong);
        btnNgayDat = findViewById(R.id.btnNgayDat);
        main_content = findViewById(R.id.main_content);
        tvNgayDat = findViewById(R.id.tvNgayDat);
        tvNgayTra = findViewById(R.id.tvNgayTra);
        btnNgayTra = findViewById(R.id.btnNgayTra);
        btnBook = findViewById(R.id.btnBook);
        ivBannerBook = findViewById(R.id.ivBannerBook);

        Intent intent = getIntent();
        Integer price = intent.getIntExtra("price",0);
        String boookeds = intent.getStringExtra("bookeds");
        String type = intent.getStringExtra("type");
        Integer numberRoom = intent.getIntExtra("numberRoom",0);
        Gson gson = new Gson();
        ArrayList<Booked> date1 = gson.fromJson(boookeds, new TypeToken<List<Booked>>() {
        }.getType());
        if(!intent.getStringExtra("image").trim().equals("")){
            byte[] decodedString = Base64.decode(intent.getStringExtra("image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(decodedByte, 1000, 400, true);
            ivBannerBook.setImageBitmap(bMapScaled);
        }


        //filter by type
        ArrayList<Booked> date = (ArrayList<Booked>) date1.stream().filter(p->p.getTypeRoom().equals(type)).collect(Collectors.toList());


        tvBookPrice.setText(String.valueOf(price));
        ivBookBack.setOnClickListener(v -> {
            finish();
        });
        btnBookRoom.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(this, v);
            for (int i = 1; i <= numberRoom; i++) {
                menu.getMenu().add("Phòng "+ i);
            }
            menu.show();
            menu.setOnMenuItemClickListener(item -> {
                tvSoPhong.setText(item.getTitle().toString());
                return true;
            });
        });
        btnNgayDat.setOnClickListener(v -> {


            if(tvSoPhong.getText().toString().equals("Chưa có phòng nào được chọn")){
                Snackbar snackbar = Snackbar
                        .make(main_content, "Bạn chưa chọn phòng", Snackbar.LENGTH_SHORT)
                        .setAction("Chọn phòng", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Code khi bấm vào nút thư lại ở đây
                                btnBookRoom.performClick();
                            }
                        });
                snackbar.show();
            }else{
                Integer idPhong = Integer.valueOf(tvSoPhong.getText().toString().split(" ")[1]) -1;
                while (date.size()<numberRoom){
                    date.add(null);
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd ");
                ArrayList<Calendar> calendarArrayList = new ArrayList<>();
                if(date.get(idPhong)!= null){
                    ArrayList<Long> bookedBegin = date.get(idPhong).getBegin();
                    ArrayList<Long> bookedEnd = date.get(idPhong).getEnd();
                    for (int i = 0; i < bookedBegin.size(); i++) {
                        String[] test,test1;
                        test = dateFormat.format(bookedBegin.get(i)).split("/");
                        test1 = dateFormat.format(bookedEnd.get(i)).split("/");
                        Calendar start =  getCalendar(Integer.parseInt(test[2]),Integer.parseInt(test[1]) ,Integer.parseInt(test[0]));
                        Calendar end =  getCalendar(Integer.parseInt(test1[2]),Integer.parseInt(test1[1]) ,Integer.parseInt(test1[0]));
                        Integer startDate = start.get(Calendar.DAY_OF_MONTH);
                        Integer startMonth = start.get(Calendar.MONTH) ;
                        Integer startYear = start.get(Calendar.YEAR);

                        Integer endDate = end.get(Calendar.DAY_OF_MONTH);
                        Integer endMonth = end.get(Calendar.MONTH) ;
                        Integer endYear = end.get(Calendar.YEAR);
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
                        Calendar calendarStart = new GregorianCalendar(startYear,startMonth,startDate);
                        Calendar calendarEnd = new GregorianCalendar(endYear,endMonth,endDate);
                        Calendar temp = calendarStart;
                        calendarArrayList.add((Calendar) temp.clone());
                        while (temp.before(calendarEnd)){
                            temp.add(Calendar.DATE,1);
                            calendarArrayList.add((Calendar) temp.clone());
                        }
                        calendarArrayList.add(calendarEnd);

                    }
                }

                Calendar now = Calendar.getInstance();
//                Calendar now1 = getCalendar(2020, 11,28);
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        (view, year, monthOfYear, dayOfMonth) -> {
                            tvNgayDat.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            tvNgayTra.setText("Chưa có");
                        },
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.setMinDate(now);
                Calendar[] calendarArrayListNew = new Calendar[calendarArrayList.size()];
                calendarArrayListNew= calendarArrayList.toArray(calendarArrayListNew);
                dpd.setDisabledDays(calendarArrayListNew);
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }

        });
        btnNgayTra.setOnClickListener(v -> {


            if(tvSoPhong.getText().toString().equals("Chưa có phòng nào được chọn")){
                Snackbar snackbar = Snackbar
                        .make(main_content, "Bạn chưa chọn phòng", Snackbar.LENGTH_SHORT)
                        .setAction("Chọn phòng", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Code khi bấm vào nút thư lại ở đây
                                btnBookRoom.performClick();
                            }
                        });
                snackbar.show();
                return;
            }

            if(tvNgayDat.getText().toString().equals("Chưa có")){
                Snackbar snackbar = Snackbar
                        .make(main_content, "Bạn chưa chọn ngày đặt phòng", Snackbar.LENGTH_SHORT)
                        .setAction("Chọn ngày", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Code khi bấm vào nút thư lại ở đây
                                btnNgayDat.performClick();
                            }
                        });
                snackbar.show();
                return;
            }else{
                Integer idPhong = Integer.valueOf(tvSoPhong.getText().toString().split(" ")[1]) -1;
                while (date.size()<numberRoom){
                    Log.e("Ad",date.toString());
                    date.add(null);
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd ");
                ArrayList<Calendar> calendarArrayList = new ArrayList<>();
                if(date.get(idPhong)!= null){
                    ArrayList<Long> bookedBegin = date.get(idPhong).getBegin();
                    ArrayList<Long> bookedEnd = date.get(idPhong).getEnd();
                    for (int i = 0; i < bookedBegin.size(); i++) {
                        String[] test,test1;
                        test = dateFormat.format(bookedBegin.get(i)).split("/");
                        test1 = dateFormat.format(bookedEnd.get(i)).split("/");
                        Calendar start =  getCalendar(Integer.parseInt(test[2]),Integer.parseInt(test[1]) ,Integer.parseInt(test[0]));
                        Calendar end =  getCalendar(Integer.parseInt(test1[2]),Integer.parseInt(test1[1]) ,Integer.parseInt(test1[0]));
                        Integer startDate = start.get(Calendar.DAY_OF_MONTH);
                        Integer startMonth = start.get(Calendar.MONTH) ;
                        Integer startYear = start.get(Calendar.YEAR);

                        Integer endDate = end.get(Calendar.DAY_OF_MONTH);
                        Integer endMonth = end.get(Calendar.MONTH) ;
                        Integer endYear = end.get(Calendar.YEAR);
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
                        Calendar calendarStart = new GregorianCalendar(startYear,startMonth,startDate);
                        Calendar calendarEnd = new GregorianCalendar(endYear,endMonth,endDate);
                        Calendar temp = calendarStart;
                        calendarArrayList.add((Calendar) temp.clone());
                        while (temp.before(calendarEnd)){
                            temp.add(Calendar.DATE,1);
                            calendarArrayList.add((Calendar) temp.clone());
                        }
                        calendarArrayList.add(calendarEnd);

                    }
                }

                Calendar now = Calendar.getInstance();
//                Calendar now1 = getCalendar(2020, 11,28);
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        (view, year, monthOfYear, dayOfMonth) -> {
                            tvNgayTra.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                            Calendar calendarEnd = new GregorianCalendar(year,monthOfYear,dayOfMonth);

                        },
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                String[] ngayDat = tvNgayDat.getText().toString().split("/");
                Calendar ngayDatCalender =  getCalendar(Integer.parseInt(ngayDat[2]),Integer.parseInt(ngayDat[1]) ,Integer.parseInt(ngayDat[0]));
                dpd.setMinDate(ngayDatCalender);
                Calendar[] calendarArrayListNew = new Calendar[calendarArrayList.size()];
                calendarArrayListNew= calendarArrayList.toArray(calendarArrayListNew);
                for(Calendar i : calendarArrayList){
                    if(ngayDatCalender.before(i)){
                        dpd.setMaxDate(i);
                        break;
                    }
                }
                dpd.setDisabledDays(calendarArrayListNew);
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
        btnBook.setOnClickListener(v -> {
            if(!tvNgayTra.getText().toString().equals("Chưa có") && !tvNgayTra.getText().toString().equals("Chưa có") ){
                String[] ngayDat = tvNgayDat.getText().toString().split("/");
                Calendar ngayDatCalender =  getCalendar(Integer.parseInt(ngayDat[2]),Integer.parseInt(ngayDat[1]) ,Integer.parseInt(ngayDat[0]));

                String[] ngayTra = tvNgayTra.getText().toString().split("/");
                Calendar ngayTraCalender =  getCalendar(Integer.parseInt(ngayTra[2]),Integer.parseInt(ngayTra[1]) ,Integer.parseInt(ngayTra[0]));
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                Log.e("Asd", dateFormat.format(ngayDatCalender.getTimeInMillis()) + " "+ dateFormat.format(ngayTraCalender.getTimeInMillis()));
            }
            else if(tvNgayDat.getText().toString().equals("Chưa có")){
                Snackbar snackbar = Snackbar
                        .make(main_content, "Bạn chưa chọn ngày đặt phòng", Snackbar.LENGTH_SHORT)
                        .setAction("Chọn ngày", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Code khi bấm vào nút thư lại ở đây
                                btnNgayDat.performClick();
                            }
                        });
                snackbar.show();
            }else if(tvNgayTra.getText().toString().equals("Chưa có")){
                Snackbar snackbar = Snackbar
                        .make(main_content, "Bạn chưa chọn ngày trả phòng", Snackbar.LENGTH_SHORT)
                        .setAction("Chọn ngày", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Code khi bấm vào nút thư lại ở đây
                                btnNgayTra.performClick();
                            }
                        });
                snackbar.show();
            }



        });

    }
    public Calendar getCalendar(int year, int month, int date) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }
}
