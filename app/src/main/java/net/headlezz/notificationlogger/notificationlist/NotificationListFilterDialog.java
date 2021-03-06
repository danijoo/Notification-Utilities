package net.headlezz.notificationlogger.notificationlist;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import net.headlezz.notificationlogger.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationListFilterDialog extends AlertDialog implements DialogInterface.OnClickListener {

    NotificationListFilterDialogCallbacks mCallbacks;

    @Bind(R.id.notification_filter_etTitle)
    EditText etTitle;

    @Bind(R.id.notification_filter_etMessage)
    EditText etMessage;

    @Bind(R.id.notification_filter_etAppName)
    EditText etAppName;

    @Bind(R.id.notification_filter_etPackage)
    EditText etPackageName;

    boolean hasPreselection = false;
    String message, title, appName, packageName;

    interface NotificationListFilterDialogCallbacks {
        void onFilterNotificationList(
            String title,
            String message,
            String appName,
            String packageName
        );
    }

    public NotificationListFilterDialog(Context context, NotificationListFilterDialogCallbacks cb) {
        super(context);
        mCallbacks = cb;

        setButton(BUTTON_POSITIVE, context.getString(R.string.filter_dialog_ok), this);
        setButton(BUTTON_NEGATIVE, context.getString(R.string.filter_dialog_cancel), this);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.notification_list_filter_dialog, null);
        setView(view);

        setTitle(context.getString(R.string.filter_dialog_title));
    }

    public NotificationListFilterDialog(Context context, NotificationListFilterDialogCallbacks cb, String title, String message, String appName, String packageName) {
        this(context, cb);
        this.title = title;
        this.message = message;
        this.appName = appName;
        this.packageName = packageName;
        hasPreselection = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(hasPreselection) {
            if(title != null)
                etTitle.setText(title);
            if(message != null)
                etMessage.setText(message);
            if(packageName != null)
                etPackageName.setText(packageName);
            if(appName != null)
                etAppName.setText(appName);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == BUTTON_POSITIVE)
            mCallbacks.onFilterNotificationList(
                    etTitle.getText().toString(),
                    etMessage.getText().toString(),
                    etAppName.getText().toString(),
                    etPackageName.getText().toString()
            );
    }

}
