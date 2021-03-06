package io.xdag.xdagwallet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.scottyab.rootbeer.RootBeer;

import butterknife.BindView;
import butterknife.OnClick;
import io.xdag.common.Common;
import io.xdag.common.base.ToolbarActivity;
import io.xdag.common.tool.ToolbarMode;
import io.xdag.common.util.TextStyleUtil;
import io.xdag.xdagwallet.R;
import io.xdag.xdagwallet.config.Config;
import io.xdag.xdagwallet.util.AlertUtil;

/**
 * created by ssyijiu  on 2018/7/22
 */

public class UseExplainActivity extends ToolbarActivity
    implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.explain_tv_explain_text)
    TextView mTvExplain;
    @BindView(R.id.explain_cb_backup)
    CheckBox mCbBackup;
    @BindView(R.id.explain_cb_not_show)
    CheckBox mCbNoShow;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_use_explain;
    }


    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mTvExplain.setText(
            new TextStyleUtil()
                .append(getString(R.string.use_explain_1))
                .append(getString(R.string.use_explain_2))
                .append(getString(R.string.use_explain_3))
                .setForegroundColor(Common.getColor(R.color.RED))
                .append(getString(R.string.use_explain_4))
                .append(getString(R.string.use_explain_5))
                .setForegroundColor(Common.getColor(R.color.RED))
                .appendLine()
                .append(getString(R.string.use_explain_6))
                .append(getString(R.string.use_explain_7))
                .create()
        );

        mCbBackup.setOnCheckedChangeListener(this);
        mCbNoShow.setOnCheckedChangeListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mCbBackup.setChecked(Config.isUserBackup());
        mCbNoShow.setChecked(Config.isNotShowExplain());
        RootBeer rootBeer = new RootBeer(mContext);

        // root
        if (rootBeer.isRootedWithoutBusyBoxCheck()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle(R.string.warning)
                .setMessage(R.string.check_root_explain)
                .setPositiveButton(R.string.continue_use, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isNotShow()) {
                            WalletActivity.start(mContext);
                            finish();
                        }
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.finish();
                    }
                });
            builder.create().show();
        } else if (isNotShow()) {
            WalletActivity.start(mContext);
            finish();
        }

    }


    @OnClick(R.id.explain_btn_start)
    void explain_btn_start() {
        if (!Config.isUserBackup()) {
            AlertUtil.show(mContext, getString(R.string.please_backup_your_xdag_wallet_first));
            return;
        }
        WalletActivity.start(mContext);
        if (isNotShow()) {
            finish();
        }
    }


    @OnClick(R.id.explain_btn_pool)
    void explain_btn_pool() {
        PoolListActivity.start(mContext, true);
    }


    public static boolean isNotShow() {
        return Config.isUserBackup() && Config.isNotShowExplain();
    }


    @Override
    protected int getToolbarTitle() {
        return R.string.use_explain;
    }


    @Override
    protected int getToolbarMode() {
        return ToolbarMode.MODE_NONE;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.explain_cb_backup:
                Config.setUserBackup(isChecked);
                break;
            case R.id.explain_cb_not_show:
                Config.setNotShowExplain(isChecked);
                break;
            default:

        }
    }
}
