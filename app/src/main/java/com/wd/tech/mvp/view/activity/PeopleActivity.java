package com.wd.tech.mvp.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.*;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.image.ImageConverter;
import com.guo.android_extend.widget.ExtImageView;
import com.guo.android_extend.widget.HListView;
import com.wd.tech.R;

import com.wd.tech.base.RsaCoder;
import com.wd.tech.face.FaceDB;
import com.wd.tech.mvp.model.app.MyApp;
import com.wd.tech.mvp.model.bean.BindFaceBean;
import com.wd.tech.mvp.presenter.presenterimpl.FaceRegPresenter;

import com.wd.tech.mvp.view.base.BaseActivity;
import com.wd.tech.mvp.view.contract.Contract;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by gqj3375 on 2017/4/27.
 */

public class PeopleActivity extends BaseActivity<Contract.IMyFaceRegView, FaceRegPresenter> implements Contract.IMyFaceRegView, SurfaceHolder.Callback {
    private final String TAG = this.getClass().toString();
    private final static int MSG_CODE = 0x1000;
    private final static int MSG_EVENT_REG = 0x1001;
    private final static int MSG_EVENT_NO_FACE = 0x1002;
    private final static int MSG_EVENT_NO_FEATURE = 0x1003;
    private final static int MSG_EVENT_FD_ERROR = 0x1004;
    private final static int MSG_EVENT_FR_ERROR = 0x1005;
    private UIHandler mUIHandler;
    // Intent data.
    private String mFilePath;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Bitmap mBitmap;
    private Rect src = new Rect();
    private Rect dst = new Rect();
    private Thread view;
    private EditText mEditText;
    private ExtImageView mExtImageView;
    private HListView mHListView;
    private RegisterViewAdapter mRegisterViewAdapter;
    private AFR_FSDKFace mAFR_FSDKFace;
    private FaceRegPresenter faceRegPresenter;
    private HashMap hashMap;


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
        try {
            view.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Nullable
    @Override
    public FaceRegPresenter createPresenter() {
        faceRegPresenter = new FaceRegPresenter(this);
        return faceRegPresenter;
    }

    @Override
    public void initActivityView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.pople_register);
        //initial data.
        if (!getIntentData(getIntent().getExtras())) {
            Log.e(TAG, "getIntentData fail!");
            this.finish();
        }
        hashMap = new HashMap<String,String>();
        SharedPreferences sharedPreferences  = getSharedPreferences("User", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "0");
        String sessionId = sharedPreferences.getString("sessionId", "0");;
        hashMap.put("userId", userId);
        hashMap.put("sessionId", sessionId);
        mRegisterViewAdapter = new RegisterViewAdapter(this);
        mHListView = (HListView) findViewById(R.id.hlistView);
        mHListView.setAdapter(mRegisterViewAdapter);
        mHListView.setOnItemClickListener(mRegisterViewAdapter);


        mUIHandler = new UIHandler();

        mBitmap = MyApp.Companion.decodeImage(mFilePath);
        src.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        mSurfaceView.getHolder().addCallback(this);
        view = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mSurfaceHolder == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];
                ImageConverter convert = new ImageConverter();
                convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);
                if (convert.convert(mBitmap, data)) {
                    Log.d(TAG, "convert ok!");
                }
                convert.destroy();

                AFD_FSDKEngine engine = new AFD_FSDKEngine();
                AFD_FSDKVersion version = new AFD_FSDKVersion();
                List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
                AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
                Log.d(TAG, "AFD_FSDK_InitialFaceEngine = " + err.getCode());
                if (err.getCode() != AFD_FSDKError.MOK) {
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_FD_ERROR;
                    reg.arg2 = err.getCode();
                    mUIHandler.sendMessage(reg);
                }
                err = engine.AFD_FSDK_GetVersion(version);
                Log.d(TAG, "AFD_FSDK_GetVersion =" + version.toString() + ", " + err.getCode());
                err = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);
                Log.d(TAG, "AFD_FSDK_StillImageFaceDetection =" + err.getCode() + "<" + result.size());
                while (mSurfaceHolder != null) {
                    Canvas canvas = mSurfaceHolder.lockCanvas();
                    if (canvas != null) {
                        Paint mPaint = new Paint();
                        boolean fit_horizontal = canvas.getWidth() / (float) src.width() < canvas.getHeight() / (float) src.height();
                        float scale = 1.0f;
                        if (fit_horizontal) {
                            scale = canvas.getWidth() / (float) src.width();
                            dst.left = 0;
                            dst.top = (canvas.getHeight() - (int) (src.height() * scale)) / 2;
                            dst.right = dst.left + canvas.getWidth();
                            dst.bottom = dst.top + (int) (src.height() * scale);
                        } else {
                            scale = canvas.getHeight() / (float) src.height();
                            dst.left = (canvas.getWidth() - (int) (src.width() * scale)) / 2;
                            dst.top = 0;
                            dst.right = dst.left + (int) (src.width() * scale);
                            dst.bottom = dst.top + canvas.getHeight();
                        }
                        canvas.drawBitmap(mBitmap, src, dst, mPaint);
                        canvas.save();
                        canvas.scale((float) dst.width() / (float) src.width(), (float) dst.height() / (float) src.height());
                        canvas.translate(dst.left / scale, dst.top / scale);
                        for (AFD_FSDKFace face : result) {
                            mPaint.setColor(Color.RED);
                            mPaint.setStrokeWidth(10.0f);
                            mPaint.setStyle(Paint.Style.STROKE);
                            canvas.drawRect(face.getRect(), mPaint);
                        }
                        canvas.restore();
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                        break;
                    }
                }

                if (!result.isEmpty()) {
                    AFR_FSDKVersion version1 = new AFR_FSDKVersion();
                    AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
                    AFR_FSDKFace result1 = new AFR_FSDKFace();
                    AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
                    Log.d("com.arcsoft", "AFR_FSDK_InitialEngine = " + error1.getCode());
                    if (error1.getCode() != AFD_FSDKError.MOK) {
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_FR_ERROR;
                        reg.arg2 = error1.getCode();
                        mUIHandler.sendMessage(reg);
                    }
                    error1 = engine1.AFR_FSDK_GetVersion(version1);
                    Log.d("com.arcsoft", "FR=" + version.toString() + "," + error1.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
                    error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
                    Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode());
                    if (error1.getCode() == AFR_FSDKError.MOK) {
                        mAFR_FSDKFace = result1.clone();
                        int width = result.get(0).getRect().width();
                        int height = result.get(0).getRect().height();
                        Bitmap face_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        Canvas face_canvas = new Canvas(face_bitmap);
                        face_canvas.drawBitmap(mBitmap, result.get(0).getRect(), new Rect(0, 0, width, height), null);
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_REG;
                        reg.obj = face_bitmap;
                        mUIHandler.sendMessage(reg);
                    } else {
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_NO_FEATURE;
                        mUIHandler.sendMessage(reg);
                    }
                    error1 = engine1.AFR_FSDK_UninitialEngine();
                    Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
                } else {
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_NO_FACE;
                    mUIHandler.sendMessage(reg);
                }
                err = engine.AFD_FSDK_UninitialFaceEngine();
                Log.d(TAG, "AFD_FSDK_UninitialFaceEngine =" + err.getCode());
            }
        });
        view.start();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onSuccess(@NotNull Object any) {
        if(any instanceof BindFaceBean)
        {
            BindFaceBean bindFaceBean = (BindFaceBean) any;
            if (bindFaceBean.getStatus().equals("0000") ) {
                Toast.makeText(PeopleActivity.this, "" + bindFaceBean.getMessage(), Toast.LENGTH_SHORT).show();
                SharedPreferences faceId = getSharedPreferences("face", MODE_PRIVATE);
                try {
                    String s = RsaCoder.decryptByPublicKey(bindFaceBean.getFaceId());
                    faceId.edit().putString("faceId", s).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //view.stop();
            } else {
                Toast.makeText(PeopleActivity.this, "" + bindFaceBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    class Holder {
        ExtImageView siv;
        TextView tv;
    }
        /**
     * @param bundle
     * @note bundle data :
     * String imagePath
     */
    private boolean getIntentData(Bundle bundle) {
        try {
            mFilePath = bundle.getString("imagePath");
            if (mFilePath == null || mFilePath.isEmpty()) {
                return false;
            }
            Log.i(TAG, "getIntentData:" + mFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    class UIHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE) {
                if (msg.arg1 == MSG_EVENT_REG) {
                    LayoutInflater inflater = LayoutInflater.from(PeopleActivity.this);
                    View layout = inflater.inflate(R.layout.dialog_register, null);
                    mEditText = layout.findViewById(R.id.editview);
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                    mExtImageView = layout.findViewById(R.id.extimageview);
                    mExtImageView.setImageBitmap((Bitmap) msg.obj);
                    final Bitmap face = (Bitmap) msg.obj;
                    new AlertDialog.Builder(PeopleActivity.this)
                            .setTitle("请输入注册名字")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(layout)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MyApp) PeopleActivity.this.getApplicationContext()).getMFaceDB().addFace(mEditText.getText().toString(), mAFR_FSDKFace);
                                    mRegisterViewAdapter.notifyDataSetChanged();
                                    Log.v("faceIDdddddddddd",""+mAFR_FSDKFace.getFeatureData());
                                    Log.v("faceIDdddddddddd",""+mAFR_FSDKFace.getFeatureData().toString());
                                    // 调用接口
                                 faceRegPresenter.onIMyFacePre(hashMap,mAFR_FSDKFace.toString());
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (msg.arg1 == MSG_EVENT_NO_FEATURE) {
                    Toast.makeText(PeopleActivity.this, "人脸特征无法检测，请换一张图片", Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_NO_FACE) {
                    Toast.makeText(PeopleActivity.this, "没有检测到人脸，请换一张图片", Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_FD_ERROR) {
                    Toast.makeText(PeopleActivity.this, "FD初始化失败，错误码：" + msg.arg2, Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_FR_ERROR) {
                    Toast.makeText(PeopleActivity.this, "FR初始化失败，错误码：" + msg.arg2, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    class RegisterViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        Context mContext;
        LayoutInflater mLInflater;

        public RegisterViewAdapter(Context c) {
            // TODO Auto-generated constructor stub
            mContext = c;
            mLInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ((MyApp) mContext.getApplicationContext()).getMFaceDB().mRegister.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder = null;
            if (convertView != null) {
                holder = (Holder) convertView.getTag();
            } else {
                convertView = mLInflater.inflate(R.layout.item_sample, null);
                holder = new Holder();
                holder.siv = convertView.findViewById(R.id.imageView1);
                holder.tv = convertView.findViewById(R.id.textView1);
                convertView.setTag(holder);
            }

            if (!((MyApp) mContext.getApplicationContext()).getMFaceDB().mRegister.isEmpty()) {
                FaceDB.FaceRegist face = ((MyApp) mContext.getApplicationContext()).getMFaceDB().mRegister.get(position);
                holder.tv.setText(face.mName);

                convertView.setWillNotDraw(false);
            }

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("onItemClick", "onItemClick = " + position + "pos=" + mHListView.getScroll());
            final String name = ((MyApp) mContext.getApplicationContext()).getMFaceDB().mRegister.get(position).mName;
            final int count = ((MyApp) mContext.getApplicationContext()).getMFaceDB().mRegister.get(position).mFaceList.size();
            new AlertDialog.Builder(PeopleActivity.this)
                    .setTitle("删除注册名:" + name)
                    .setMessage("包含:" + count + "个注册人脸特征信息")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MyApp) mContext.getApplicationContext()).getMFaceDB().delete(name);
                            mRegisterViewAdapter.notifyDataSetChanged();
                            faceRegPresenter.onIMyFacePre(hashMap,mAFR_FSDKFace.toString());
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

}
