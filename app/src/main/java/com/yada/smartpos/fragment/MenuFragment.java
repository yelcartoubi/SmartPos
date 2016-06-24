package com.yada.smartpos.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.yada.sdk.packages.PackagingException;
import com.yada.smartpos.R;
import com.yada.smartpos.activity.App;
import com.yada.smartpos.activity.MainActivity;
import com.yada.smartpos.handler.ConsumeHandler;
import com.yada.smartpos.handler.InstallmentHandler;
import com.yada.smartpos.handler.PreAuthHandler;
import com.yada.smartpos.handler.SignInHandler;

import java.io.IOException;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private MainActivity mainActivity;
    private ConsumeHandler consumeHandler;
    private InstallmentHandler installmentHandler;
    private PreAuthHandler preAuthHandler;
    private SignInHandler signInHandler;

    private String[] arrText = new String[]{
            "消费", "消费撤销", "消费退货",
            "分期消费", "分期撤销", "分期退货",
            "预授权", "预授权撤销", "预授权完成",
            "预授权完成撤销", "签到", "打印"
    };

    private int[] arrImages = new int[]{
            R.drawable.main_icon_1, R.drawable.main_icon_2, R.drawable.main_icon_3,
            R.drawable.main_icon_4, R.drawable.main_icon_5, R.drawable.main_icon_6,
            R.drawable.main_icon_7, R.drawable.main_icon_8, R.drawable.main_icon_9,
            R.drawable.main_icon_10, R.drawable.main_icon_11, R.drawable.main_icon_12
    };

    public MenuFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        consumeHandler = new ConsumeHandler(mainActivity);
        installmentHandler = new InstallmentHandler(mainActivity);
        preAuthHandler = new PreAuthHandler(mainActivity);
        signInHandler = new SignInHandler(mainActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getFragmentManager();
        // 清空堆栈
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ((App) mainActivity.getApplication()).setTransData(null);
        ((App) mainActivity.getApplication()).setTransResult(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        GridLayout layout = (GridLayout) view.findViewById(R.id.fl_item);
        for (int i = 0; i < arrText.length; i++) {
            View item = inflater.inflate(R.layout.item_menu, container, false);
            item.setTag(i);
            ImageView imageView = (ImageView) item.findViewById(R.id.iv_icon);
            imageView.setImageResource(arrImages[i]);
            TextView textView = (TextView) item.findViewById(R.id.txt_name);
            textView.setText(arrText[i]);
            layout.addView(item);
            item.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        // 根据元素位置获取对应的值
        int i = (int) view.getTag();
        switch (i) {
            case 0:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            consumeHandler.sale();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            consumeHandler.revoke();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            consumeHandler.refund();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            installmentHandler.sale();
                        } catch (PackagingException | IOException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            installmentHandler.revoke();
                        } catch (PackagingException | IOException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 5:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            installmentHandler.refund();
                        } catch (PackagingException | IOException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 6:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            preAuthHandler.preAuth();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 7:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            preAuthHandler.preAuthRevoke();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 8:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            preAuthHandler.complete();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 9:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            preAuthHandler.completeRevoke();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;
            case 10:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            signInHandler.paramDownload();
                        } catch (IOException | PackagingException e) {
                            e.printStackTrace();
                            exceptionHandler(e);
                        }
                    }
                }).start();
                break;

        }
    }

    private void exceptionHandler(Exception e) {
        ((App) mainActivity.getApplication()).getTransResult().setTransCode("0");
        ((App) mainActivity.getApplication()).getTransResult().setTransMsg(e.getMessage());
        Message message = mainActivity.getFragmentHandler().obtainMessage(100);
        message.obj = "fail";
        message.sendToTarget();
    }

    /**
     private void clientTest() {
     new Thread(new Runnable() {
    @Override public void run() {
    TcpClient client = null;
    try {
    String pack = "6000120000130602007020058020c09009190456351010089268584731000000000000000000168309010012143704563510100892685847d4912520100008690038333938313030303130343131303038333938313030300156771ecbb5b635a94d0015303930353131303031303030303832483edbc6335f2119";
    ByteBuffer reqBuffer = ByteBuffer.wrap(BytesUtils.hexStringToBytes(pack));
    client = new TcpClient(new InetSocketAddress("10.2.56.70", 1000),
    new FixLenPackageSplitterFactory(2, false), 20000);
    client.open();
    ByteBuffer respBuffer = client.send(reqBuffer);
    System.out.println(BytesUtils.bytesToHex(respBuffer.array()));
    } catch (IOException e) {
    e.printStackTrace();
    } finally {
    client.close();
    }
    }
    }).start();
     }
     **/
}
