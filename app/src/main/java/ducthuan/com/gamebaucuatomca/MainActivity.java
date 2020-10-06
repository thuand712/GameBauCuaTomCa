package ducthuan.com.gamebaucuatomca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    BanCoAdapter adapter;
    ArrayList<Integer> dsHinhConVat;
    ArrayList<Integer> dsHinhConVatXX;
    RecyclerView rvBanCo;
    TextView txtDiem,txtThoiGian;

    boolean checknhacnen = false;

    public static Integer[] giatienDatCuoc = new Integer[6];

    ImageView imgXiNgau1, imgXiNgau2, imgXiNgau3, imgBatDau, imgNhacNen;

    AnimationDrawable chuyendong1, chuyendong2, chuyendong3;
    Random rdXiNgau;

    int giaTriXiNgau1, giaTriXiNgau2, giaTriXiNgau3;

    CountDownTimer countDownTimer,countDownTimerDemThoiGian;

    int tienthuong, kiemtra;

    SharedPreferences sharedPreferences;
    int tongtiencu;

    MediaPlayer amthanhxingau, nhacnen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();


    }


    private void addEvents() {

        imgNhacNen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("kiemtra",imgNhacNen.getWidth()+"");


                if (checknhacnen == false) {
                    imgNhacNen.setImageResource(R.drawable.amthanhtat);
                    nhacnen.stop();
                    checknhacnen = true;

                } else {

                    imgNhacNen.setImageResource(R.drawable.amthanhmo);
                    nhacnen.start();
                    checknhacnen = false;


                }
            }
        });

        imgBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemtra = 0;
                for (int i = 0; i < giatienDatCuoc.length; i++) {
                    kiemtra += giatienDatCuoc[i];
                }
                if (kiemtra == 0) {
                    Toast.makeText(MainActivity.this, "Bạn chưa đặt cược !!", Toast.LENGTH_SHORT).show();
                } else {
                    if (kiemtra > tongtiencu) {
                        Toast.makeText(MainActivity.this, "Bạn không đủ tiền đặt cược", Toast.LENGTH_SHORT).show();
                    } else {
                        imgBatDau.setVisibility(View.GONE);
                        amthanhxingau.start();
                        xuLyLacXiNgau();
                    }
                }

            }
        });

        //imgBatDau*/

    }



    private void xuLyLacXiNgau() {

        tienthuong = 0;

        imgXiNgau1.setImageResource(R.drawable.xi_ngau_lac);
        imgXiNgau2.setImageResource(R.drawable.xi_ngau_lac);
        imgXiNgau3.setImageResource(R.drawable.xi_ngau_lac);

        chuyendong1 = (AnimationDrawable) imgXiNgau1.getDrawable();
        chuyendong2 = (AnimationDrawable) imgXiNgau2.getDrawable();
        chuyendong3 = (AnimationDrawable) imgXiNgau3.getDrawable();
        chuyendong1.start();
        chuyendong2.start();
        chuyendong3.start();

        //countDowntimer
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {


            }

            @Override
            public void onFinish() {
                randomXiNgau1();
                randomXiNgau2();
                randomXiNgau3();
                xuLyGiaiThuong();

            }
        };
        countDownTimer.start();
        //countDowntimer
    }

    private void xuLyGiaiThuong() {

        for (int i = 0; i < giatienDatCuoc.length; i++) {
            if (giatienDatCuoc[i] != 0) {
                if (i == giaTriXiNgau1) {
                    tienthuong += giatienDatCuoc[i];
                }
                if (i == giaTriXiNgau2) {
                    tienthuong += giatienDatCuoc[i];
                }
                if (i == giaTriXiNgau3) {
                    tienthuong += giatienDatCuoc[i];
                }
                if (i != giaTriXiNgau1 && i != giaTriXiNgau2 && i != giaTriXiNgau3) {
                    tienthuong -= giatienDatCuoc[i];
                }
            }

        }

        if (tienthuong == 0) {
            Toast.makeText(this, "Hên quá xém chết !", Toast.LENGTH_SHORT).show();
        } else if (tienthuong > 0) {
            Toast.makeText(this, "Quá dữ bạn trúng được " + tienthuong, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ôi xui quá mất " + tienthuong + " rồi !", Toast.LENGTH_SHORT).show();
        }


        final SharedPreferences.Editor editor = sharedPreferences.edit();
        tongtiencu = tongtiencu + tienthuong;

        if (tongtiencu <= 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(R.layout.custom_dialog_gameover);
            final AlertDialog dialog = builder.create();
            dialog.show();

            ImageView btnChoiLai = dialog.findViewById(R.id.btnChoiLai);
            ImageView btnThoatGame = dialog.findViewById(R.id.btnThoatGame);

            btnThoatGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putInt("tongtien", 1000);
                    editor.commit();
                    MainActivity.this.finish();
                }
            });

            btnChoiLai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tongtiencu = 1000;
                    editor.putInt("tongtien", tongtiencu);
                    editor.commit();
                    txtDiem.setText(tongtiencu + "");
                    dialog.dismiss();
                    imgBatDau.setVisibility(View.VISIBLE);
                }
            });

        } else {
            editor.putInt("tongtien", tongtiencu);
            editor.commit();
            txtDiem.setText(tongtiencu + "");
            imgBatDau.setVisibility(View.VISIBLE);
        }
    }


    private void randomXiNgau1() {
        rdXiNgau = new Random();
        giaTriXiNgau1 = rdXiNgau.nextInt(6);
        switch (giaTriXiNgau1) {
            case 0:
                imgXiNgau1.setImageResource(dsHinhConVat.get(giaTriXiNgau1));
                break;
            case 1:
                imgXiNgau1.setImageResource(dsHinhConVat.get(giaTriXiNgau1));
                break;
            case 2:
                imgXiNgau1.setImageResource(dsHinhConVat.get(giaTriXiNgau1));
                break;
            case 3:
                imgXiNgau1.setImageResource(dsHinhConVat.get(giaTriXiNgau1));
                break;
            case 4:
                imgXiNgau1.setImageResource(dsHinhConVat.get(giaTriXiNgau1));
                break;
            case 5:
                imgXiNgau1.setImageResource(dsHinhConVat.get(giaTriXiNgau1));
                break;
        }
    }

    private void randomXiNgau2() {
        rdXiNgau = new Random();
        giaTriXiNgau2 = rdXiNgau.nextInt(6);
        switch (giaTriXiNgau2) {
            case 0:
                imgXiNgau2.setImageResource(dsHinhConVat.get(giaTriXiNgau2));
                break;
            case 1:
                imgXiNgau2.setImageResource(dsHinhConVat.get(giaTriXiNgau2));
                break;
            case 2:
                imgXiNgau2.setImageResource(dsHinhConVat.get(giaTriXiNgau2));
                break;
            case 3:
                imgXiNgau2.setImageResource(dsHinhConVat.get(giaTriXiNgau2));
                break;
            case 4:
                imgXiNgau2.setImageResource(dsHinhConVat.get(giaTriXiNgau2));
                break;
            case 5:
                imgXiNgau2.setImageResource(dsHinhConVat.get(giaTriXiNgau2));
                break;
        }
    }

    private void randomXiNgau3() {
        rdXiNgau = new Random();
        giaTriXiNgau3 = rdXiNgau.nextInt(6);
        switch (giaTriXiNgau3) {
            case 0:
                imgXiNgau3.setImageResource(dsHinhConVat.get(giaTriXiNgau3));
                break;
            case 1:
                imgXiNgau3.setImageResource(dsHinhConVat.get(giaTriXiNgau3));
                break;
            case 2:
                imgXiNgau3.setImageResource(dsHinhConVat.get(giaTriXiNgau3));
                break;
            case 3:
                imgXiNgau3.setImageResource(dsHinhConVat.get(giaTriXiNgau3));
                break;
            case 4:
                imgXiNgau3.setImageResource(dsHinhConVat.get(giaTriXiNgau3));
                break;
            case 5:
                imgXiNgau3.setImageResource(dsHinhConVat.get(giaTriXiNgau3));
                break;
        }
    }


    private void addControls() {
        rvBanCo = findViewById(R.id.rvBanCo);
        imgXiNgau1 = findViewById(R.id.imgXiNgau1);
        imgXiNgau2 = findViewById(R.id.imgXiNgau2);
        imgXiNgau3 = findViewById(R.id.imgXiNgau3);
        imgBatDau = findViewById(R.id.imgBatDau);
        txtDiem = findViewById(R.id.txtDiem);
        imgNhacNen = findViewById(R.id.imgNhacNen);
        txtThoiGian = findViewById(R.id.txtThoiGian);

        countDownTimerDemThoiGian = new CountDownTimer(180000,1000) {
            @Override
            public void onTick(long milis) {
                long gio = TimeUnit.MILLISECONDS.toHours(milis);
                long phut = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
                long giay = TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milis));

                String giophutgiay = String.format("%02d:%02d:%02d",gio,phut,giay);
                txtThoiGian.setText(giophutgiay);
            }

            @Override
            public void onFinish() {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                tongtiencu = tongtiencu + 1000;
                editor.putInt("tongtien",tongtiencu);
                txtDiem.setText(tongtiencu+"");

                this.start();

            }
        };

        countDownTimerDemThoiGian.start();


        sharedPreferences = getSharedPreferences("tongtien", MODE_PRIVATE);
        tongtiencu = sharedPreferences.getInt("tongtien", 1000);
        txtDiem.setText(tongtiencu + "");

        amthanhxingau = MediaPlayer.create(MainActivity.this, R.raw.dice);
        nhacnen = MediaPlayer.create(MainActivity.this, R.raw.nhacnenbaucua);
        nhacnen.start();

        dsHinhConVat = new ArrayList<>();
        dsHinhConVat.add(R.drawable.baux);
        dsHinhConVat.add(R.drawable.cuax);
        dsHinhConVat.add(R.drawable.tomx);
        dsHinhConVat.add(R.drawable.cax);
        dsHinhConVat.add(R.drawable.gax);
        dsHinhConVat.add(R.drawable.naix);

        dsHinhConVatXX = new ArrayList<>();
        dsHinhConVatXX.add(R.drawable.bau);
        dsHinhConVatXX.add(R.drawable.cua);
        dsHinhConVatXX.add(R.drawable.tom);
        dsHinhConVatXX.add(R.drawable.ca);
        dsHinhConVatXX.add(R.drawable.ga);
        dsHinhConVatXX.add(R.drawable.nai);

        adapter = new BanCoAdapter(MainActivity.this, dsHinhConVatXX);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3, RecyclerView.VERTICAL, false);
        rvBanCo.setHasFixedSize(true);
        rvBanCo.setLayoutManager(layoutManager);
        rvBanCo.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        nhacnen.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        nhacnen.stop();
        super.onPause();
    }
}
