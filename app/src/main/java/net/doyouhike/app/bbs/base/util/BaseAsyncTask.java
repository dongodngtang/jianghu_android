package net.doyouhike.app.bbs.base.util;

/**
 * Created by Hawaken on 2016/3/18.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, BaseAsyncTaskResult<Result>> {
    private static final String TAG = BaseAsyncTask.class.getSimpleName();

    protected boolean isShowDialogFlag;
    private AsyncAlertDialog alertDialog;
    private Context context;

    public BaseAsyncTask(Context context) {
        this(context, false);
        this.context = context;

    }

    public BaseAsyncTask(Context context, boolean isShowDialogFlag) {
        this.isShowDialogFlag = isShowDialogFlag;
        this.context = context;
        if(isShowDialogFlag)
            alertDialog = new AsyncAlertDialog(context);
    }

    protected BaseAsyncTaskResult<Result> doInBackground(Params... params) {
        try {
            return this.doInBackgroundInner(params);
        } catch (Throwable var3) {
            Log.e(TAG, "doInBackgroundInner error ", var3);
            return new BaseAsyncTaskResult(var3);
        }
    }

    protected abstract BaseAsyncTaskResult<Result> doInBackgroundInner(Params... var1) throws Throwable;

    protected void onPostExecute(BaseAsyncTaskResult<Result> result) {
        try {
            if(result != null && !result.hasError()) {
                this.onPostExecuteSuccess(result);
            } else {
                if(result == null) {
                    Log.w(TAG, "BaseAsyncTaskResult is null");
                } else {
                    Log.w(TAG, "BaseAsyncTaskResult has error", result.getError());
                }

                this.onPostExecuteFailure();
            }
        } finally {
            this.onPostExecuteFinish();
        }

    }

    private void onPostExecuteFinish() {
        if(null!=alertDialog)
        alertDialog.dismiss();
    }

    protected abstract void onPostExecuteSuccess(BaseAsyncTaskResult<Result> var1);

    protected void onPostExecuteFailure() {
        Log.w(TAG, "onPostExecuteFailure..");
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.openDialog("提示", "正在处理,请稍等...");
    }

    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
    }

    protected void onCancelled() {
        super.onCancelled();
    }

    public void openDialog(String title, String message) {
        if(this.isShowDialogFlag) {
            try {
                alertDialog.show();
            } catch (Exception var4) {
                Log.w(TAG, "openDialog error", var4);
            }
        }

    }



    public void cancelRequest() {
        Log.v(TAG, "cancel...");
    }


}
