package com.wd.tech.face;

import android.util.Log;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.java.ExtInputStream;
import com.guo.android_extend.java.ExtOutputStream;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqj3375 on 2017/7/11.
 */

public class FaceDB {
	private final String TAG = this.getClass().toString();

	public static String appid = "rp1Z4Ch5euyRikj4n4W5D2iFL4vyiBvq2h67MDbZ4Jd";
	public static String ft_key = "Ghx166nyZQpatMvzQt4DXcRuXr4BCsCqeXFnDJ1hWHxr";
	public static String fd_key = "Ghx166nyZQpatMvzQt4DXcS2hFKMEwC7vbJ4RWUYwfQ8";
	public static String fr_key = "Ghx166nyZQpatMvzQt4DXcSXLrN26wPM2MEyPHwF4qrx";
	public static String age_key = "Ghx166nyZQpatMvzQt4DXcSmfetLkaaBWye7PwZdLr8Y";
	public static String gender_key = "Ghx166nyZQpatMvzQt4DXcStq49UDM1ZBWoyowZJrFuf";

	String mDBPath;
	public List<FaceRegist> mRegister;
	AFR_FSDKEngine mFREngine;
	AFR_FSDKVersion mFRVersion;
	boolean mUpgrade;

	public class FaceRegist {
		public String mName;
		public List<AFR_FSDKFace> mFaceList;

		public FaceRegist(String name) {
			mName = name;
			mFaceList = new ArrayList<>();
		}
	}

	public FaceDB(String path) {
		mDBPath = path;
		mRegister = new ArrayList<>();
		mFRVersion = new AFR_FSDKVersion();
		mUpgrade = false;
		mFREngine = new AFR_FSDKEngine();
		AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
		if (error.getCode() != AFR_FSDKError.MOK) {
			Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
		} else {
			mFREngine.AFR_FSDK_GetVersion(mFRVersion);
			Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
		}
	}

	public void destroy() {
		if (mFREngine != null) {
			mFREngine.AFR_FSDK_UninitialEngine();
		}
	}

	private boolean saveInfo() {
		try {
			FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt");
			ExtOutputStream bos = new ExtOutputStream(fs);
			bos.writeString(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel());
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean loadInfo() {
		if (!mRegister.isEmpty()) {
			return false;
		}
		try {
			FileInputStream fs = new FileInputStream(mDBPath + "/face.txt");
			ExtInputStream bos = new ExtInputStream(fs);
			//load version
			String version_saved = bos.readString();
			if (version_saved.equals(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel())) {
				mUpgrade = true;
			}
			//load all regist name.
			if (version_saved != null) {
				for (String name = bos.readString(); name != null; name = bos.readString()){
					if (new File(mDBPath + "/" + name + ".data").exists()) {
						mRegister.add(new FaceRegist(new String(name)));
					}
				}
			}
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadFaces(){
		if (loadInfo()) {
			try {
				for (FaceRegist face : mRegister) {
					Log.d(TAG, "load name:" + face.mName + "'s face feature data.");
					FileInputStream fs = new FileInputStream(mDBPath + "/" + face.mName + ".data");
					ExtInputStream bos = new ExtInputStream(fs);
					AFR_FSDKFace afr = null;
					do {
						if (afr != null) {
							if (mUpgrade) {
								//upgrade data.
							}
							face.mFaceList.add(afr);
						}
						afr = new AFR_FSDKFace();
					} while (bos.readBytes(afr.getFeatureData()));
					bos.close();
					fs.close();
					Log.d(TAG, "load name: size = " + face.mFaceList.size());
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public	void addFace(String name, AFR_FSDKFace face) {
		try {
			//check if already registered.
			boolean add = true;
			for (FaceRegist frface : mRegister) {
				if (frface.mName.equals(name)) {
					frface.mFaceList.add(face);
					add = false;
					break;
				}
			}
			if (add) { // not registered.
				FaceRegist frface = new FaceRegist(name);
				frface.mFaceList.add(face);
				mRegister.add(frface);
			}

			if (saveInfo()) {
				//update all names
				FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
				ExtOutputStream bos = new ExtOutputStream(fs);
				for (FaceRegist frface : mRegister) {
					bos.writeString(frface.mName);
				}
				bos.close();
				fs.close();

				//save new feature
				fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
				bos = new ExtOutputStream(fs);
				bos.writeBytes(face.getFeatureData());
				bos.close();
				fs.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean delete(String name) {
		try {
			//check if already registered.
			boolean find = false;
			for (FaceRegist frface : mRegister) {
				if (frface.mName.equals(name)) {
					File delfile = new File(mDBPath + "/" + name + ".data");
					if (delfile.exists()) {
						delfile.delete();
					}
					mRegister.remove(frface);
					find = true;
					break;
				}
			}

			if (find) {
				if (saveInfo()) {
					//update all names
					FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
					ExtOutputStream bos = new ExtOutputStream(fs);
					for (FaceRegist frface : mRegister) {
						bos.writeString(frface.mName);
					}
					bos.close();
					fs.close();
				}
			}
			return find;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upgrade() {
		return false;
	}
}
