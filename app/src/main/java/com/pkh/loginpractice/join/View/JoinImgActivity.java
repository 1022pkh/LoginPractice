package com.pkh.loginpractice.join.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pkh.loginpractice.R;
import com.pkh.loginpractice.join.presenter.JoinImgPresenter;
import com.pkh.loginpractice.join.presenter.JoinImgPresenterImpl;
import com.pkh.loginpractice.main.view.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinImgActivity extends AppCompatActivity implements JoinImgView{

    @BindView(R.id.imageView1)
    ImageView getImg;
    @BindView(R.id.completeBtn)
    Button completeBtn;

    final int REQ_CODE_SELECT_IMAGE=100;

    String getImgURL="";
    String getImgName="";

    private JoinImgPresenter presenter;
    ProgressDialog asyncDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_img);


        Log.i("myTag","joinImgActivity In");

        ButterKnife.bind(this);

        presenter = new JoinImgPresenterImpl(this);

    }

    @OnClick(R.id.imageView1)
    public void getImg(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @OnClick(R.id.completeBtn)
    public void completeJoinBtn(){
        Intent intent = getIntent();
        String id = intent.getExtras().get("id").toString();
        String name = intent.getExtras().get("name").toString();
        String pw = intent.getExtras().get("pw").toString();
        String email = intent.getExtras().get("email").toString();

        /**
         * getImgBtn 버튼 클릭을 통해, 업로드할 사진의 절대경로를 가져옴
         * 서버로 보내는 시간을 고려하여 진행바를 넣어줌
         */

        asyncDialog = new ProgressDialog(JoinImgActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("가입 중 입니다..");

        // show dialog
        asyncDialog.show();

        //String id, String nickname, String pwd, String email, String ImgURL
        presenter.registerToServer(id,name,pw,email,getImgURL);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    Log.i("myTag",name_Str);

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView)findViewById(R.id.imageView1);

                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(image_bitmap);

                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();

                }
                catch (FileNotFoundException e) { 		e.printStackTrace(); 			}
                catch (IOException e)                 {		e.printStackTrace(); 			}
                catch (Exception e)		         {             e.printStackTrace();			}
            }
        }
    }


    // 선택된 이미지 파일명 가져오기
    @Override
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        getImgURL = imgPath;
        getImgName = imgName;

        return "success";
    }

    @Override
    public void completeRegister(String result) {

        Log.i("myTag","completeRegister In");
        Log.i("myTag","completeRegister value = " +result);

        // dismiss dialog
        asyncDialog.dismiss();

        if (result.equals("success")) {

            Toast.makeText(getApplicationContext(), "가입 성공!" , Toast.LENGTH_SHORT).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);



        } else if (result.equals("fail")) {

            Toast.makeText(getApplicationContext(), "가입 실패!" , Toast.LENGTH_SHORT).show();
        }


    }


}
