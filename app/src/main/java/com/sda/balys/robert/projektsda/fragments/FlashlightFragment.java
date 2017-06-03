package com.sda.balys.robert.projektsda.fragments;


import android.Manifest;
import android.content.Context;
import android.graphics.Camera;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sda.balys.robert.projektsda.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashlightFragment extends Fragment {
    private TextView buttonFlashlight;
    private Camera camera;
    private android.hardware.Camera cam;
    private android.hardware.Camera.Parameters params;
    private boolean isOnOf;

        boolean onOf=false;

    private

    Context context;

    public FlashlightFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flashlight, container, false);
        ButterKnife.bind(this,view);
        buttonFlashlight = (TextView) view.findViewById(R.id.flashlight);
        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{Manifest.permission.CAMERA},
                1);

        return view;
    }

    private void turnOnFlash(){
        if(!isOnOf){
//            if(cam==null||params==null){
//                return;
//            }
            cam= android.hardware.Camera.open();
            params = cam.getParameters();
            params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(params);
            cam.startPreview();
            isOnOf=true;
        }

    }

    private void turnOfFlsh(){
        if(isOnOf){
            if(cam==null||params==null){
                return;
            }
            cam= android.hardware.Camera.open();
            params=cam.getParameters();
            params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
            cam.setParameters(params);
            cam.stopPreview();
            isOnOf=false;
        }
    }


//    public boolean flashLight(Context context, boolean onOf) throws CameraAccessException {
//
//
//        //context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//        String[] listaCamer =   manager.getCameraIdList();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            manager.setTorchMode(listaCamer[0],onOf);
////        }
//
//        return true;
//    }
    @OnClick(R.id.flashlight)
    public void clicFlash(){
        String text;
        onOf = !onOf;
        if(onOf){
            turnOnFlash();
            text = "ON";
        }
        else{
            turnOfFlsh();
            text="OFF";
        }
        buttonFlashlight.setText(text);
//        try {
//            buttonFlashlight.setText(text);
//            flashLight(context,onOf);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }


    }

}
