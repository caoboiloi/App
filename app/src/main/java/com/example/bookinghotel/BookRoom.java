package com.example.bookinghotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.bookinghotel.Fragment.CartFragment;
import com.example.bookinghotel.Screen.Home.Home;
import com.example.bookinghotel.entity.Booked;
import com.example.bookinghotel.entity.Ticket;
import com.example.bookinghotel.entity.TimeBooked;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
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
        String bookedRoomStr = intent.getStringExtra("bookedRoom");
        String type = intent.getStringExtra("type");
        String path = intent.getStringExtra("path");
        Integer numberRoom = intent.getIntExtra("numberRoom",0);
        AtomicReference<Integer> totalDate = new AtomicReference<>(0);

        Gson gson = new Gson();

        ArrayList<TimeBooked> bookedRoom = gson.fromJson(bookedRoomStr, new TypeToken<List<TimeBooked>>() {
        }.getType());

        Log.e("Ad1", String.valueOf(bookedRoom));
        if(!intent.getStringExtra("image").trim().equals("")){
            byte[] decodedString = Base64.decode(intent.getStringExtra("image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(decodedByte, 1000, 400, true);
            ivBannerBook.setImageBitmap(bMapScaled);
        }


        //filter by type
//        ArrayList<Booked> date = (ArrayList<Booked>) date1.stream().filter(p->p.getTypeRoom().equals(type)).collect(Collectors.toList());

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
                while (bookedRoom.size()<numberRoom){
                    bookedRoom.add(null);
                }
                Log.e("Ad1", String.valueOf(bookedRoom));
                Log.e("Ad1", String.valueOf(idPhong));
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd ");
                ArrayList<Calendar> calendarArrayList = new ArrayList<>();

                if(bookedRoom.get(idPhong)!= null){
                    Log.e("Ad1","begin "+ String.valueOf(bookedRoom.get(idPhong).getBegin()));
                    ArrayList<Long> bookedBegin = bookedRoom.get(idPhong).getBegin();
                    Log.e("Ad1","begin "+ String.valueOf(bookedBegin));
                    ArrayList<Long> bookedEnd = bookedRoom.get(idPhong).getEnd();
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
                totalDate.set(calendarArrayList.size());
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
                while (bookedRoom.size()<numberRoom){
                    bookedRoom.add(null);
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd ");
                ArrayList<Calendar> calendarArrayList = new ArrayList<>();
                if(bookedRoom.get(idPhong)!= null){
                    ArrayList<Long> bookedBegin = bookedRoom.get(idPhong).getBegin();
                    ArrayList<Long> bookedEnd = bookedRoom.get(idPhong).getEnd();
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
                Log.e("Ad1", "size "+String.valueOf(calendarArrayList.size()));
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

                //get UserId
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //get ticket
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users/"+userId+"/ticket");

                final Boolean[] inserted = {false};


                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<List<Ticket>> t = new GenericTypeIndicator<List<Ticket>>() {};
                        List<Ticket> tickets = snapshot.getValue(t);
                        snapshot.getChildrenCount();
                        int count = 0;
                        Date a = new Date(ngayDatCalender.getTimeInMillis());
                        Date b = new Date(ngayTraCalender.getTimeInMillis());
                        long diffInMillies = b.getTime() - a.getTime();
                        long day = TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS) + 1;
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference(path+"/bookedRoom");
                        Integer idRoom = Integer.valueOf(tvSoPhong.getText().toString().split(" ")[1]) -1;
                        TimeBooked temp = bookedRoom.get(idRoom);
                        if(temp == null){
                            temp = null;
                        }
                        Log.e("Ad1", String.valueOf(temp));
                        ArrayList<TimeBooked> a1 = new ArrayList<TimeBooked>();
                        for (int i = 0; i < bookedRoom.size(); i++) {
                            if(bookedRoom.get(i) ==null){
                                a1.add(new TimeBooked());
                            }
                            else{
                                a1.add(bookedRoom.get(i));
                            }
                        }
                        if(tickets != null){
                            count = tickets.size();

                            if(!inserted[0]){
                                inserted[0] = true;
                                Ticket ticket = new Ticket(ngayDatCalender.getTimeInMillis(),ngayTraCalender.getTimeInMillis(),price*day,path,true);
                                mDatabase.child(String.valueOf(count)).setValue(ticket);
                                a1.get(idRoom).appendBegin(a.getTime());
                                a1.get(idRoom).appendEnd(b.getTime());
                                mDatabase1.child(type).setValue(a1);
                                Snackbar snackbar = Snackbar
                                        .make(main_content, "Thành công", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                Intent intent = new Intent(BookRoom.this, HotelDetail.class);
                                intent.putExtra("status","success");
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            if(!inserted[0]){
                                inserted[0] = true;
                                Ticket ticket = new Ticket(ngayDatCalender.getTimeInMillis(),ngayTraCalender.getTimeInMillis(),price*day,path,true);
                                mDatabase.child(String.valueOf(count)).setValue(ticket);
                                a1.get(idRoom).appendBegin(a.getTime());
                                a1.get(idRoom).appendEnd(b.getTime());
                                mDatabase1.child(type).setValue(a1);
                                Snackbar snackbar = Snackbar
                                        .make(main_content, "Thành công", Snackbar.LENGTH_SHORT);
                                snackbar.show();

                                Intent intent = new Intent(BookRoom.this, HotelDetail.class);
                                intent.putExtra("status","success");
                                startActivity(intent);
                                finish();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
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
