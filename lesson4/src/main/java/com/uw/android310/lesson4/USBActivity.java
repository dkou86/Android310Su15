package com.uw.android310.lesson4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;


public class USBActivity extends AppCompatActivity {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private byte[] mBytes;
    private static int TIMEOUT = 0;
    private boolean forceClaim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the device from the intent
        UsbDevice usbDeviceFromIntent = (UsbDevice) getIntent()
                .getParcelableExtra(UsbManager.EXTRA_DEVICE);

        // Enumerating devices
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Log.d("USBActivity", deviceList.toString());
        
        // Iterator
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            // Do something with the device
        }

//        // Request permission (explicit)
//        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
//        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//        registerReceiver(mUsbReceiver, filter);
//        usbManager.requestPermission(device, permissionIntent);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device != null) {
                        // Setup device communication
                        UsbInterface usbInterface = device.getInterface(0);
                        final UsbEndpoint usbEndpoint = usbInterface.getEndpoint(0);

                        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                        final UsbDeviceConnection usbDeviceConnection = usbManager.openDevice(device);
                        usbDeviceConnection.claimInterface(usbInterface, forceClaim);

                        // Synchronous
                        usbDeviceConnection.bulkTransfer(usbEndpoint, mBytes, mBytes.length, TIMEOUT);

                        // Asynchronous
                        UsbRequest usbRequest = new UsbRequest();
                        usbRequest.initialize(usbDeviceConnection, usbEndpoint);

                        ByteBuffer byteBuffer = ByteBuffer.wrap(mBytes);
                        usbRequest.queue(byteBuffer, mBytes.length);

                        // Java API - use runnable
                        new Runnable() {
                            @Override
                            public void run() {
                                usbDeviceConnection.bulkTransfer(usbEndpoint, mBytes, mBytes.length, TIMEOUT);
                            }
                        };

                        // Handler is great way to (not manually spawn a thread) and use the
                        // Android looper.
                        new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);

                                // Send data
                            }
                        };

                    } else {
                        // Permission denied
                        Toast.makeText(
                                USBActivity.this,
                                "User did not grant permission to access device: "
                                        + device.getDeviceName(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };
}
